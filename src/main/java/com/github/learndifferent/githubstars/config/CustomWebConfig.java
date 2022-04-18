package com.github.learndifferent.githubstars.config;

import com.github.learndifferent.githubstars.enums.GetMode;
import com.github.learndifferent.githubstars.exception.ServiceException;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC configuration
 *
 * @author zhou
 * @date 2022/4/18
 */
@Configuration
public class CustomWebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new GetModeConverter());
    }
}

class GetModeConverter implements Converter<String, GetMode> {

    private final String message = "0 stand for 'Get all languages' and 1 stand for 'Get only the main language'";

    @Override
    public GetMode convert(String source) {
        try {
            return getGetModeByNumber(source);
        } catch (NullPointerException e) {
            throw new ServiceException(message);
        } catch (NumberFormatException e) {
            return getGetModeByName(source);
        }
    }

    private GetMode getGetModeByNumber(String source) {
        int num = Integer.parseInt(source);
        final int getAll = GetMode.GET_ALL_LANGUAGES.getNumber();
        final int getMain = GetMode.GET_ONLY_MAIN_LANGUAGE.getNumber();

        if (num == getAll) {
            return GetMode.GET_ALL_LANGUAGES;
        }
        if (num == getMain) {
            return GetMode.GET_ONLY_MAIN_LANGUAGE;
        }
        throw new NumberFormatException();
    }

    private GetMode getGetModeByName(String source) {
        try {
            return GetMode.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ServiceException(message);
        }
    }
}