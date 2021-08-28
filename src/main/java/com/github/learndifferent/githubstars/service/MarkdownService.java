package com.github.learndifferent.githubstars.service;

import com.github.learndifferent.githubstars.entity.Repo;

import java.util.LinkedHashMap;
import java.util.List;

public interface MarkdownService {
    void generateMarkdown(String username, LinkedHashMap<String, List<Repo>> repoMap);

    void generateMarkdown(String username, boolean getAllLanguages);

    void generateMarkdown(String username);
}
