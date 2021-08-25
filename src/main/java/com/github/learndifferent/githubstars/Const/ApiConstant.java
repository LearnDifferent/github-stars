package com.github.learndifferent.githubstars.Const;

public class ApiConstant {
    private ApiConstant() {
    }

    public static final String INCOMPLETE_API_URL = "https://api.github.com/users/{username}/starred?per_page=100&&page={pageNum}";
}
