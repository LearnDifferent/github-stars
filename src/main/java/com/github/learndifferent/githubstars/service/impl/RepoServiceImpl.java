package com.github.learndifferent.githubstars.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.learndifferent.githubstars.constant.ApiConstant;
import com.github.learndifferent.githubstars.entity.Repo;
import com.github.learndifferent.githubstars.enums.GetMode;
import com.github.learndifferent.githubstars.exception.ServiceException;
import com.github.learndifferent.githubstars.service.RepoService;
import com.github.learndifferent.githubstars.util.JsonUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Repository Service Implementation
 *
 * @author zhou
 * @date 2022/4/18
 */
@Slf4j
@Service
public class RepoServiceImpl implements RepoService {

    private final RestTemplate restTemplate;

    @Autowired
    public RepoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Repo> getRecentStarredRepos(String username, GetMode mode) {

        List<Repo> repos = new ArrayList<>();
        int pageNum = 1;

        while (updateReposAndReturnIfUpdated(username, mode, repos, pageNum)) {
            pageNum++;
        }

        return repos;
    }

    private boolean updateReposAndReturnIfUpdated(String username, GetMode mode, List<Repo> repos, int pageNum) {

        log.info("Processing page " + pageNum);

        String json = restTemplate.getForObject(ApiConstant.INCOMPLETE_API_URL,
                String.class, username, pageNum);
        String endMessage = "Finish getting " + username + "'s starred repos.";

        if (StringUtils.isEmpty(json)) {
            log.info(endMessage);
            // return false if no data to update
            return false;
        }

        List<Repo> reposTemp = JsonUtil.toObject(json, new TypeReference<List<Repo>>() {});

        if (CollectionUtils.isEmpty(reposTemp)) {
            log.info(endMessage);
            return false;
        }

        if (GetMode.GET_ALL_LANGUAGES.equals(mode)) {
            reposTemp.forEach(o -> {
                if (StringUtils.isEmpty(o.getLanguage())) {
                    // if no language provided, just return
                    return;
                }
                o.setLanguages(getAllLanguages(o));
                log.info("Get Starred Repo's all languages: " + o.getName());
            });
        }

        // 添加当前页面的 repo list
        repos.addAll(reposTemp);
        log.info("Finished page " + pageNum);
        // return true if updated the data
        return true;
    }

    private List<String> getAllLanguages(Repo repo) {
        String url = repo.getLanguagesUrl();
        String json;
        try {
            json = restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            log.warn("Fail to access all languages of the Repo: " + repo.getName());
            e.printStackTrace();
            return Collections.emptyList();
        }

        // 获取到的 key 是编程语言，value 是有该编程语言的构成数据
        // 所以 value 可以忽略，不过要使用 LinkedHashMap 来保证是按照默认（从多到少）顺序排列
        Map<String, String> languagesMap = JsonUtil.toObject(json,
                new TypeReference<LinkedHashMap<String, String>>() {});

        return new ArrayList<>(languagesMap.keySet());
    }

    /**
     * 给 Repo List 进行排序，最后输出排序后的 Repo Map。
     * <p>1. 将列表按照先 Watchers，后 Forks 排序，然后替换所有为 null 的 language 属性为 "Others"</p>
     * <p>2. 生成一个按照 Language 分类，也就是 Language 作为 key 的 LinkedHashMap</p>
     * <p>3. 用刚刚生成的 Map 的 entrySet 的 Stream，来给这个 Map 的 key 再次进行排序</p>
     * <p>3.1 再次排序的时候，首先将 key 为 "Others" 的排序值，设置为 -1</p>
     * <p>3.2 然后，按照 key 所包含的 value 的多少，来设置排序值；key 包含的 value 越多，值越大</p>
     * <p>3.3 最后，按照排序值从大到小，进行排序</p>
     * <p>3.4 也就是说，含有 Repo 最多的 Language 会排在前面；
     * Language 为 null，也就是 "Others" 会排在最后</p>
     * <p>4. 最后生成完成的 LinkedHashMap 并返回</p>
     *
     * @param repos starred repositories
     * @return 排序后的 Repos
     */
    @Override
    public LinkedHashMap<String, List<Repo>> getSortedRepoMap(List<Repo> repos) {

        String others = "Others";

        log.info("Sort Starred Repo by Language, Stars and Forks.");
        return repos.stream()
                .sorted(Comparator
                        .comparing(Repo::getWatchers, Comparator.reverseOrder())
                        .thenComparing(Repo::getForks, Comparator.reverseOrder()))
                .peek(repo -> {
                    // 这里将 null 值转换为 Others 字符串
                    if (repo.getLanguage() == null) {
                        repo.setLanguage(others);
                    }
                })
                .collect(Collectors.groupingBy(
                        Repo::getLanguage,
                        LinkedHashMap::new,
                        Collectors.toList()))
                .entrySet().stream()
                .sorted(Comparator.comparing(entry -> {
                            if (others.equalsIgnoreCase(entry.getKey())) {
                                return -1;
                            }
                            return entry.getValue().size();
                        }, Comparator.reverseOrder())
                )
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new ServiceException("Key Conflicts");
                        },
                        LinkedHashMap::new));
    }

}