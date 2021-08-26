package com.github.learndifferent.githubstars.service;

import com.github.learndifferent.githubstars.entity.Repo;

import java.util.List;

public interface RepoService {

    List<Repo> getStarredRepoByUsername(String username);

    List<String> getLanguages(Repo repo);

    List<Repo> getSortedRepo(List<Repo> starredRepo);
}
