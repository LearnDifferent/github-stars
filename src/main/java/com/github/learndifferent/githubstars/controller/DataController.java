package com.github.learndifferent.githubstars.controller;

import com.github.learndifferent.githubstars.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DataController {

    private final MarkdownService markdownService;

    @Autowired
    public DataController(MarkdownService markdownService) {
        this.markdownService = markdownService;
    }

    @GetMapping
    public String hello() {
        return "Hello";
    }

    @GetMapping("/{username}")
    public void getStarsListMarkdown(@PathVariable("username") String username) {
        markdownService.generateMarkdown(username);
    }
}
