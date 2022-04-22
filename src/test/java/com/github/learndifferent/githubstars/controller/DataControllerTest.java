package com.github.learndifferent.githubstars.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.github.learndifferent.githubstars.enums.GetMode;
import com.github.learndifferent.githubstars.service.MarkdownService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DataController.class)
class DataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarkdownService markdownService;

    @Test
    @DisplayName("Should run once")
    void shouldRunOnce() throws Exception {

        String username = "user1";
        mockMvc.perform(get("/" + username + "/0"));

        verify(markdownService, times(1))
                .generateMarkdown(username, GetMode.GET_ALL_LANGUAGES);
    }

    @Test
    @DisplayName("Should get only the main language")
    void shouldGetOnlyTheMainLanguage() throws Exception {

        ArgumentCaptor<GetMode> captor = ArgumentCaptor.forClass(GetMode.class);

        doNothing().when(markdownService)
                .generateMarkdown(anyString(), captor.capture());

        mockMvc.perform(get("/user2/1"));

        assertEquals(GetMode.GET_ONLY_MAIN_LANGUAGE, captor.getValue());
    }

}