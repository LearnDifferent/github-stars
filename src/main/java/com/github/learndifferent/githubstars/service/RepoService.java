package com.github.learndifferent.githubstars.service;

import com.github.learndifferent.githubstars.entity.Repo;

import java.util.List;

public interface RepoService {

    /**
     * 获取某个用户所有的 starred repositories
     *
     * @param username        需要获取 starred repositories 的用户的 username
     * @param getAllLanguages 是否需要获取仓库的所有编程语言，如果是 false，就只获取主要的编程语言
     * @return starred repositories 的列表
     */
    List<Repo> getStarredRepos(String username, boolean getAllLanguages);

    List<Repo> getStarredRepos(String username);

    List<String> getLanguages(Repo repo);

    List<Repo> getSortedRepo(List<Repo> starredRepo);
}
