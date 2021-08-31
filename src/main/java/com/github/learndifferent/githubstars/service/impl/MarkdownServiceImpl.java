package com.github.learndifferent.githubstars.service.impl;

import com.github.learndifferent.githubstars.entity.Repo;
import com.github.learndifferent.githubstars.exception.ServiceException;
import com.github.learndifferent.githubstars.service.MarkdownService;
import com.github.learndifferent.githubstars.service.RepoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MarkdownServiceImpl implements MarkdownService {

    private final RepoService repoService;

    @Autowired
    public MarkdownServiceImpl(RepoService repoService) {
        this.repoService = repoService;
    }

    @Override
    public void generateMarkdown(String username, LinkedHashMap<String, List<Repo>> repoMap) {

        File markdownFile = getFile(username);

        try (FileOutputStream fos = new FileOutputStream(markdownFile);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             PrintWriter pw = new PrintWriter(osw, true)) {

            // 获取所有 key，也就是仓库的主要编程语言
            Set<String> reposMainLanguage = repoMap.keySet();
            // 创建一个 Map：key 为仓库的主要编程语言，value 为该编程语言的 id（用于 Markdown）
            LinkedHashMap<String, String> languageAndTheirMarkdownId = reposMainLanguage
                    .stream()
                    .collect(Collectors.toMap(
                            Function.identity(),
                            this::getMarkdownId,
                            (a, b) -> a,
                            LinkedHashMap::new));

            pw.println("# GitHub Stars");
            pw.println();
            pw.println(">A curated list of [" + username + "](https://github.com/" + username
                    + ") 's GitHub stars generated by [github-stars](https://github.com/LearnDifferent/github-stars)");
            pw.println();

            pw.println("## Table of Contents");
            pw.println();
            languageAndTheirMarkdownId.forEach((lang, langId) ->
                    pw.println("- [" + lang + " ("
                            + repoMap.get(lang).size() + ")](#" + langId + ")"));
            pw.println();

            repoMap.forEach((lang, starredRepo) -> {
                log.info("Adding Repos of " + lang);
                pw.println("## <span id=\"" + languageAndTheirMarkdownId.get(lang)
                        + "\">" + lang + "</span>");
                pw.println();

                starredRepo.forEach(s -> {
                    log.info("Adding Repo: " + s.getName());
                    pw.println("[" + s.getName() + "](" + s.getHtmlUrl() + ") :");
                    pw.println();
                    printIfExist(s.getHomepage(), "- [Website](" + s.getHomepage() + ")", pw);
                    pw.println("- Owner: [" + s.getOwner().getLogin() + "](" + s.getOwner().getHtmlUrl() + ")");
                    printIfExist(s.getDescription(), "- Description: " + s.getDescription(), pw);
                    pw.println("- Clone: `git clone " + s.getSshUrl() + "`");
                    pw.println("- Stars: " + s.getWatchers() + ", Forks: " + s.getForks());
                    printLanguagesIfExist(pw, s.getLanguages());
                    pw.println();
                    pw.println();
                });
            });
        } catch (FileNotFoundException e) {
            log.warn("Can't find the file.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            log.info("Finish Generating Markdown File.");
        }
    }

    private File getFile(String username) {
        String filename = username + "_" + System.currentTimeMillis() + ".md";
        File file = new File(filename);
        if (file.exists()) {
            throw new ServiceException(filename + " already exists");
        }
        try {
            boolean flag = file.createNewFile();
            if (flag) {
                log.info("Generating Markdown File: " + filename);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private String getMarkdownId(String word) {
        return UUID.randomUUID().toString();
    }

    private void printIfExist(String item, String printLine, PrintWriter pw) {
        if (StringUtils.isEmpty(item)) {
            return;
        }
        pw.println(printLine);
    }

    private void printLanguagesIfExist(PrintWriter pw, List<String> languages) {
        if (CollectionUtils.isEmpty(languages)) {
            return;
        }
        pw.print("- language: ");
        for (int i = 0; i < languages.size(); i++) {
            pw.print(languages.get(i));
            if (i != languages.size() - 1) {
                pw.print(", ");
            }
        }
    }

    @Override
    public void generateMarkdown(String username, boolean getAllLanguages) {
        List<Repo> recentRepos = repoService.getRecentStarredRepos(username, getAllLanguages);
        LinkedHashMap<String, List<Repo>> repoMap = repoService.getSortedRepoMap(recentRepos);
        generateMarkdown(username, repoMap);
    }

    @Override
    public void generateMarkdown(String username) {
        generateMarkdown(username, false);
    }
}
