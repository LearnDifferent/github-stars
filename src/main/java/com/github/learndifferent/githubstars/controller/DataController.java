package com.github.learndifferent.githubstars.controller;

import com.github.learndifferent.githubstars.entity.Starred;
import com.github.learndifferent.githubstars.service.MarkdownService;
import com.github.learndifferent.githubstars.service.StarredService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class DataController {

    private final StarredService starredService;
    private final MarkdownService markdownService;

    @Autowired
    public DataController(StarredService starredService,
                          MarkdownService markdownService) {
        this.starredService = starredService;
        this.markdownService = markdownService;
    }

    @GetMapping
    public String hello() {
        return "Hello";
    }

    @GetMapping("/{username}")
    public void getStarsListMarkdown(@PathVariable("username") String username) {
        List<Starred> starsSortByTime = starredService.getStarsByUsername(username);
        List<Starred> stars = starredService.getSortedStars(starsSortByTime);
        // 生成文件
        markdownService.generateMarkdown(stars);
    }
}
