package com.github.learndifferent.githubstars.constant;

public class ApiConstant {
    private ApiConstant() {
    }

    public static final String INCOMPLETE_API_URL = "https://api.github.com/users/{username}/starred?per_page=100&&page={pageNum}";
}