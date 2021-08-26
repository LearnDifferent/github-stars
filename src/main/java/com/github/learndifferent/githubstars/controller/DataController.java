package com.github.learndifferent.githubstars.controller;

import com.github.learndifferent.githubstars.entity.Repo;
import com.github.learndifferent.githubstars.service.MarkdownService;
import com.github.learndifferent.githubstars.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DataController {

    private final RepoService repoService;
    private final MarkdownService markdownService;

    @Autowired
    public DataController(RepoService repoService,
                          MarkdownService markdownService) {
        this.repoService = repoService;
        this.markdownService = markdownService;
    }

    @GetMapping
    public String hello() {
        return "Hello";
    }

    @GetMapping("/{username}")
    public void getStarsListMarkdown(@PathVariable("username") String username) {
        List<Repo> starredRepoSortByTime = repoService.getStarredRepos(username);
        List<Repo> stars = repoService.getSortedRepo(starredRepoSortByTime);
        // 生成文件
        markdownService.generateMarkdown(stars);
    }
}
