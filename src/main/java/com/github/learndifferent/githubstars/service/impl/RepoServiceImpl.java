package com.github.learndifferent.githubstars.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.learndifferent.githubstars.Const.ApiConstant;
import com.github.learndifferent.githubstars.entity.Repo;
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

@Slf4j
@Service
public class RepoServiceImpl implements RepoService {

    private final RestTemplate restTemplate;

    @Autowired
    public RepoServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Repo> getStarredRepos(String username, boolean getAllLanguages) {

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

            // todo 使用 AOP，完成获取 languages 的过程
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
    public List<Repo> getStarredRepos(String username) {
        return getStarredRepos(username, false);
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

    @Override
    public List<Repo> getSortedRepo(List<Repo> starredRepo) {

        log.info("Sorting Starred Repo by Language, Stars and Forks...");
        Comparator<Repo> comparator = Comparator
                .comparing(Repo::getLanguage, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Repo::getWatchers, Comparator.reverseOrder())
                .thenComparing(Repo::getForks, Comparator.reverseOrder());

        starredRepo.sort(comparator);
        log.info("Finish sorting.");
        return starredRepo;
    }
}
