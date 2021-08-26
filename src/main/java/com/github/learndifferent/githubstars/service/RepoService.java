package com.github.learndifferent.githubstars.service;

import com.github.learndifferent.githubstars.entity.Repo;

import java.util.LinkedHashMap;
import java.util.List;

public interface RepoService {

    /**
     * 获取某个用户所有的 starred repositories
     *
     * @param username        需要获取 starred repositories 的用户的 username
     * @param getAllLanguages 是否需要获取仓库的所有编程语言，如果是 false，就只获取主要的编程语言
     * @return starred repositories 的列表
     */
    List<Repo> getRecentStarredRepos(String username, boolean getAllLanguages);

    List<Repo> getRecentStarredRepos(String username);

    List<String> getLanguages(Repo repo);

    /**
     * 获取以 key 为 language，value 为每个 language 分类下的 Repo List 的 Map。
     * <p>其中，Repo List 会先根据 Watchers 排序，再根据 Forks 来排序</p>
     *
     * @param repos starred repositories
     * @return key 为 language，value 为每个 language 分类下的 Repo List；
     * 其中，Repo List 会先根据 Watchers 排序，再根据 Forks 来排序
     */
    LinkedHashMap<String, List<Repo>> getSortedRepoMap(List<Repo> repos);
}
