package com.github.learndifferent.githubstars.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Data
public class Starred {

    // name
    private String name;
    // url
    private String htmlUrl;

    // homepage
    private String homepage;

    // owner
    private Owner owner;

    // description
    private String description;

    // for git clone
    private String sshUrl;

    // forks
    private Integer forks;
    // starts
    private Integer watchers;

    // primary language
    private String language;

    // api of programming language(s)
    private String languagesUrl;
    // get the languages from the api
    private List<String> languages;
}
