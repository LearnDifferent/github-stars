package com.github.learndifferent.githubstars.service;

import com.github.learndifferent.githubstars.entity.Starred;

import java.util.List;

public interface StarredService {

    List<Starred> getStarsByUsername(String username);

    List<String> getLanguages(Starred starred);

    List<Starred> getSortedStars(List<Starred> stars);
}
