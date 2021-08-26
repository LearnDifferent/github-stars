package com.github.learndifferent.githubstars.service;

import com.github.learndifferent.githubstars.entity.Repo;

import java.util.List;

public interface MarkdownService {
    void generateMarkdown(List<Repo> starredRepo);
}
