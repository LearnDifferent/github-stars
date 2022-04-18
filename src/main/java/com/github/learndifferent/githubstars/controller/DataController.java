package com.github.learndifferent.githubstars.controller;

import com.github.learndifferent.githubstars.enums.GetMode;
import com.github.learndifferent.githubstars.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Data Controller
 *
 * @author zhou
 * @date 2022/4/18
 */
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
    public void generateMarkdown(@PathVariable("username") String username) {
        markdownService.generateMarkdown(username);
    }

    /**
     * Generate a list of GitHub user's stars and save it to a Markdown file
     *
     * @param username username of the user whose data is being requested
     * @param mode     0 stand for 'Get all languages' and 1 stand for 'Get only the main language'
     */
    @GetMapping("/{username}/{mode}")
    public void generateMarkdown(@PathVariable("username") String username,
                                 @PathVariable(value = "mode") GetMode mode) {
        markdownService.generateMarkdown(username, mode);
    }
}