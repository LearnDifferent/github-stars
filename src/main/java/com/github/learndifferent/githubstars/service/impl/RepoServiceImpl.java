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
    public List<Repo> getStarredRepoByUsername(String username) {

        List<Repo> starredRepo = new ArrayList<>();
        int pageNum = 1;

        while (true) {
            String json = restTemplate.getForObject(ApiConstant.INCOMPLETE_API_URL,
                    String.class, username, pageNum);
            log.info("Processing page " + pageNum);

            List<Repo> temp = JsonUtil.toObject(json, new TypeReference<List<Repo>>() {
            });

            if (CollectionUtils.isEmpty(temp)) {
                log.info("Finish getting " + username + "'s starred repos.");
                break;
            }

            // todo 使用 AOP，完成获取 languages 的过程
            temp.forEach(o -> {
                if (StringUtils.isEmpty(o.getLanguage())) {
                    // if no language provided, just return
                    log.info("Get Starred Repo: " + o.getName());
                    return;
                }
                o.setLanguages(getLanguages(o));
            });

            starredRepo.addAll(temp);
            log.info("Finished page " + pageNum);
            pageNum++;
        }

        return starredRepo;
    }

    @Override
    public List<String> getLanguages(Repo repo) {
        String url = repo.getLanguagesUrl();
        String json = "";
        try {
            json = restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            log.warn("Fail to access: " + repo.getName());
            e.printStackTrace();
            return Collections.emptyList();
        }

        Map<String, String> languagesMap = JsonUtil.toObject(json,
                new TypeReference<LinkedHashMap<String, String>>() {});
        log.info("Get Starred Repo: " + repo.getName());

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
