package com.github.learndifferent.githubstars.service;

import com.github.learndifferent.githubstars.entity.Repo;
import com.github.learndifferent.githubstars.enums.GetMode;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Repository Service
 *
 * @author zhou
 * @date 2022/4/18
 */
public interface RepoService {

    /**
     * 获取某个用户所有的 starred repositories
     *
     * @param username 需要获取 starred repositories 的用户的 username
     * @param mode     Get all languages if {@link GetMode#GET_ALL_LANGUAGES}.
     *                 Get only the main language if {@link GetMode#GET_ONLY_MAIN_LANGUAGE}.
     * @return starred repositories 的列表
     */
    List<Repo> getRecentStarredRepos(String username, GetMode mode);

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