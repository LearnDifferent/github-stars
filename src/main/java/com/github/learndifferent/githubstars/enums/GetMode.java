package com.github.learndifferent.githubstars.enums;

/**
 * Get all languages if {@link #GET_ALL_LANGUAGES}.
 * Get only the main language if {@link #GET_ONLY_MAIN_LANGUAGE}.
 *
 * @author zhou
 * @date 2022/4/18
 */
public enum GetMode {
    /**
     * Get all languages
     */
    GET_ALL_LANGUAGES(0),
    /**
     * Get only the main language
     */
    GET_ONLY_MAIN_LANGUAGE(1);

    private final int number;

    GetMode(final int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}