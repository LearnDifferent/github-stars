package com.github.learndifferent.githubstars.service;

import com.github.learndifferent.githubstars.entity.Starred;

import java.util.List;

public interface MarkdownService {
    void generateMarkdown(List<Starred> stars);
}
