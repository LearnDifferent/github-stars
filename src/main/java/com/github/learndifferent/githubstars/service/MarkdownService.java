package com.github.learndifferent.githubstars.service;

import com.github.learndifferent.githubstars.enums.GetMode;

/**
 * Markdown Service
 *
 * @author zhou
 * @date 2022/4/18
 */
public interface MarkdownService {

    /**
     * Generate a list of GitHub user's stars and save it to a Markdown file
     *
     * @param username username of the user whose data is being requested
     * @param mode     Get all languages if {@link GetMode#GET_ALL_LANGUAGES}.
     *                 Get only the main language if {@link GetMode#GET_ONLY_MAIN_LANGUAGE}.
     */
    void generateMarkdown(String username, GetMode mode);

    /**
     * Generate a list of GitHub user's stars and save it to a Markdown file
     *
     * @param username username of the user whose data is being requested
     */
    void generateMarkdown(String username);
}