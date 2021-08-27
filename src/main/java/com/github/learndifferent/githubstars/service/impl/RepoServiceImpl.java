package com.github.learndifferent.githubstars.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.learndifferent.githubstars.Const.ApiConstant;
import com.github.learndifferent.githubstars.entity.Repo;
import com.github.learndifferent.githubstars.exception.ServiceException;
import com.github.learndifferent.githubstars.service.RepoService;
import com.github.learndifferent.githubstars.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RepoServiceImpl implements RepoService {

    private final RestTemplate restTemplate;

    @Autowired
    public RepoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Repo> getRecentStarredRepos(String username, boolean getAllLanguages) {

        List<Repo> repos = new ArrayList<>();
        int pageNum = 1;

        while (true) {
            log.info("Processing page " + pageNum);
            String json = restTemplate.getForObject(ApiConstant.INCOMPLETE_API_URL,
                    String.class, username, pageNum);

            List<Repo> reposTemp = JsonUtil.toObject(json, new TypeReference<List<Repo>>() {
            });

            if (CollectionUtils.isEmpty(reposTemp)) {
                log.info("Finish getting " + username + "'s starred repos.");
                break;
            }

            if (getAllLanguages) {
                // 如果为 true，就遍历获取所有 languages
                reposTemp.forEach(o -> {
                    if (StringUtils.isEmpty(o.getLanguage())) {
                        // if no language provided, just return
                        return;
                    }
                    o.setLanguages(getLanguages(o));
                    log.info("Get Starred Repo's all languages: " + o.getName());
                });
            }

            // 添加当前页面的 repo list
            repos.addAll(reposTemp);
            log.info("Finished page " + pageNum);
            pageNum++;
        }

        return repos;
    }


    @Override
    public List<Repo> getRecentStarredRepos(String username) {
        return getRecentStarredRepos(username, false);
    }

    @Override
    public List<String> getLanguages(Repo repo) {
        String url = repo.getLanguagesUrl();
        String json = "";
        try {
            json = restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            log.warn("Fail to access Repo's all languages: " + repo.getName());
            e.printStackTrace();
            return Collections.emptyList();
        }

        // 获取到的 key 是编程语言，value 是有该编程语言的构成数据
        // 所以 value 可以忽略，不过要使用 LinkedHashMap 来保证是按照默认（从多到少）顺序排列
        Map<String, String> languagesMap = JsonUtil.toObject(json,
                new TypeReference<LinkedHashMap<String, String>>() {
                });

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

        log.info("Sort Starred Repo by Language, Stars and Forks.");
        return repos.stream()
                .sorted(Comparator
                        .comparing(Repo::getWatchers, Comparator.reverseOrder())
                        .thenComparing(Repo::getForks, Comparator.reverseOrder()))
                .peek(repo -> {
                    // 这里将 null 值转换为 Others 字符串
                    if (repo.getLanguage() == null) {
                        repo.setLanguage("Others");
                    }
                })
                .collect(Collectors.groupingBy(
                        Repo::getLanguage,
                        LinkedHashMap::new,
                        Collectors.toList()))
                .entrySet().stream()
                .sorted(Comparator.comparing(entry -> {
                            if (entry.getKey().equalsIgnoreCase("others")) {
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
