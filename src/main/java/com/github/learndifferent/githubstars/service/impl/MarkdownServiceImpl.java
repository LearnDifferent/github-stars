package com.github.learndifferent.githubstars.service.impl;

import com.github.learndifferent.githubstars.entity.Repo;
import com.github.learndifferent.githubstars.exception.ServiceException;
import com.github.learndifferent.githubstars.service.MarkdownService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MarkdownServiceImpl implements MarkdownService {

    @Override
    public void generateMarkdown(List<Repo> starredRepo) {

        try (FileOutputStream fos = new FileOutputStream(getFile());
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             PrintWriter pw = new PrintWriter(osw, true)) {
            // 总数
            final int total = starredRepo.size();
            // 计数器
            final int[] count = {1};
            // Repo 拥有的语言列表
            List<String> existingLanguage = new ArrayList<>();

            starredRepo.forEach(s -> {
                String primaryLanguage =
                        s.getLanguage() != null ? s.getLanguage() : "Others";

                if (languageNotExist(existingLanguage, primaryLanguage)) {
                    existingLanguage.add(primaryLanguage);
                    pw.println("# <span id=\"" + getMarkdownId(primaryLanguage) + "\">"
                            + primaryLanguage + "</span>");
                    pw.println();
                    log.info("Add H1 Tag: " + primaryLanguage);
                }

                log.info("Adding " + s.getName() + " to file.");
                pw.println("[" + s.getName() + "](" + s.getHtmlUrl() + ") :");
                pw.println();
                printIfExist(s.getHomepage(), "- [Homepage](" + s.getHomepage() + ")", pw);
                pw.println("- Owner: [" + s.getOwner().getLogin() + "](" + s.getOwner().getHtmlUrl() + ")");
                printIfExist(s.getDescription(), "- Description: " + s.getDescription(), pw);
                pw.println("- Clone: `git clone " + s.getSshUrl() + "`");
                pw.println("- Stars: " + s.getWatchers() + ", Forks: " + s.getForks());
                printLanguagesIfExist(pw, s.getLanguages());
                pw.println();
                pw.println();
                log.info(count[0] + "done, " + (total - count[0]) + " to go.");
                count[0]++;
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

    private File getFile() {
        File file = new File("StarredRepo.md");
        if (file.exists()) {
            // todo 暂时先使用固定的文件，以后再修改
            throw new ServiceException("StarredRepo.md already exists");
        }
        try {
            boolean flag = file.createNewFile();
            if (flag) {
                log.info("Generating Markdown File...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    private boolean languageNotExist(List<String> existingLanguage,
                                     String language) {
        return !existingLanguage.contains(language);
    }

    private String getMarkdownId(String word) {

        // 将所有非数字和非字母的字符，替换为 - 符号
        return word.trim().toLowerCase().replaceAll("\\W", "-");
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
}
