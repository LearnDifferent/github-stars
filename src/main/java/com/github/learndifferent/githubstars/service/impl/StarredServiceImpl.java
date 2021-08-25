package com.github.learndifferent.githubstars.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.learndifferent.githubstars.Const.ApiConstant;
import com.github.learndifferent.githubstars.entity.Starred;
import com.github.learndifferent.githubstars.service.StarredService;
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
public class StarredServiceImpl implements StarredService {

    private final RestTemplate restTemplate;

    @Autowired
    public StarredServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Starred> getStarsByUsername(String username) {

        List<Starred> stars = new ArrayList<>();
        int pageNum = 1;

        while (true) {
            String json = restTemplate.getForObject(ApiConstant.INCOMPLETE_API_URL,
                    String.class, username, pageNum);
            log.info("Processing page " + pageNum);

            List<Starred> temp = JsonUtil.toObject(json, new TypeReference<List<Starred>>() {
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

            stars.addAll(temp);
            log.info("Finished page " + pageNum);
            pageNum++;
        }

        return stars;
    }

    @Override
    public List<String> getLanguages(Starred starred) {
        String url = starred.getLanguagesUrl();
        String json = "";
        try {
            json = restTemplate.getForObject(url, String.class);
        } catch (RestClientException e) {
            log.warn("Fail to access: " + starred.getName());
            e.printStackTrace();
            return Collections.emptyList();
        }

        Map<String, String> languagesMap = JsonUtil.toObject(json, new TypeReference<LinkedHashMap<String, String>>() {
        });
        log.info("Get Starred Repo: " + starred.getName());

        return new ArrayList<>(languagesMap.keySet());
    }

    @Override
    public List<Starred> getSortedStars(List<Starred> stars) {

        log.info("Sorting Starred Repo by Language, Stars and Forks...");
        Comparator<Starred> comparator = Comparator
                .comparing(Starred::getLanguage, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Starred::getWatchers, Comparator.reverseOrder())
                .thenComparing(Starred::getForks, Comparator.reverseOrder());

        stars.sort(comparator);
        log.info("Finish sorting.");
        return stars;
    }
}
