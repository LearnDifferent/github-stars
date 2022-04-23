package com.github.learndifferent.githubstars.service.impl;

import com.github.learndifferent.githubstars.constant.ApiConstant;
import com.github.learndifferent.githubstars.entity.Repo;
import com.github.learndifferent.githubstars.enums.GetMode;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class RepoServiceImplTest {

    @InjectMocks
    private RepoServiceImpl repoService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    @DisplayName("Should return the list")
    void shouldReturnTheList() throws URISyntaxException, IOException {

        URL resource = RepoServiceImplTest.class.getClassLoader().getResource("data.json");
        assert resource != null;
        byte[] data = Files.readAllBytes(Paths.get(resource.toURI()));
        String json = new String(data);

        String username = "user1";

        Mockito.when(restTemplate.getForObject(ApiConstant.INCOMPLETE_API_URL, String.class, username, 1))
                .thenReturn(json);
        Mockito.when(restTemplate.getForObject(ApiConstant.INCOMPLETE_API_URL, String.class, username, 2))
                .thenReturn("");

        List<Repo> recentStarredRepos = repoService.getRecentStarredRepos(username, GetMode.GET_ONLY_MAIN_LANGUAGE);
        recentStarredRepos.forEach(System.out::println);
    }
}