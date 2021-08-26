package com.github.learndifferent.githubstars;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.learndifferent.githubstars.entity.Repo;
import com.github.learndifferent.githubstars.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
class GithubStarsApplicationTests {

    String json = "[\n" +
            "  {\n" +
            "    \"id\": 72907253,\n" +
            "    \"node_id\": \"MDEwOlJlcG9zaXRvcnk3MjkwNzI1Mw==\",\n" +
            "    \"name\": \"spring-boot-examples\",\n" +
            "    \"full_name\": \"ityouknow/spring-boot-examples\",\n" +
            "    \"private\": false,\n" +
            "    \"owner\": {\n" +
            "      \"login\": \"ityouknow\",\n" +
            "      \"id\": 4979648,\n" +
            "      \"node_id\": \"MDQ6VXNlcjQ5Nzk2NDg=\",\n" +
            "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/4979648?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/ityouknow\",\n" +
            "      \"html_url\": \"https://github.com/ityouknow\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/ityouknow/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/ityouknow/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/ityouknow/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/ityouknow/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/ityouknow/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/ityouknow/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/ityouknow/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/ityouknow/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/ityouknow/received_events\",\n" +
            "      \"type\": \"User\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"html_url\": \"https://github.com/ityouknow/spring-boot-examples\",\n" +
            "    \"description\": \"about learning Spring Boot via examples. Spring Boot 教程、技术栈示例代码，快速简单上手教程。 \",\n" +
            "    \"fork\": false,\n" +
            "    \"url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples\",\n" +
            "    \"forks_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/forks\",\n" +
            "    \"keys_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/keys{/key_id}\",\n" +
            "    \"collaborators_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/collaborators{/collaborator}\",\n" +
            "    \"teams_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/teams\",\n" +
            "    \"hooks_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/hooks\",\n" +
            "    \"issue_events_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/issues/events{/number}\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/events\",\n" +
            "    \"assignees_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/assignees{/user}\",\n" +
            "    \"branches_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/branches{/branch}\",\n" +
            "    \"tags_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/tags\",\n" +
            "    \"blobs_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/git/blobs{/sha}\",\n" +
            "    \"git_tags_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/git/tags{/sha}\",\n" +
            "    \"git_refs_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/git/refs{/sha}\",\n" +
            "    \"trees_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/git/trees{/sha}\",\n" +
            "    \"statuses_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/statuses/{sha}\",\n" +
            "    \"languages_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/languages\",\n" +
            "    \"stargazers_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/stargazers\",\n" +
            "    \"contributors_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/contributors\",\n" +
            "    \"subscribers_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/subscribers\",\n" +
            "    \"subscription_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/subscription\",\n" +
            "    \"commits_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/commits{/sha}\",\n" +
            "    \"git_commits_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/git/commits{/sha}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/comments{/number}\",\n" +
            "    \"issue_comment_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/issues/comments{/number}\",\n" +
            "    \"contents_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/contents/{+path}\",\n" +
            "    \"compare_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/compare/{base}...{head}\",\n" +
            "    \"merges_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/merges\",\n" +
            "    \"archive_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/{archive_format}{/ref}\",\n" +
            "    \"downloads_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/downloads\",\n" +
            "    \"issues_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/issues{/number}\",\n" +
            "    \"pulls_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/pulls{/number}\",\n" +
            "    \"milestones_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/milestones{/number}\",\n" +
            "    \"notifications_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/notifications{?since,all,participating}\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/labels{/name}\",\n" +
            "    \"releases_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/releases{/id}\",\n" +
            "    \"deployments_url\": \"https://api.github.com/repos/ityouknow/spring-boot-examples/deployments\",\n" +
            "    \"created_at\": \"2016-11-05T05:32:33Z\",\n" +
            "    \"updated_at\": \"2021-08-25T08:52:07Z\",\n" +
            "    \"pushed_at\": \"2021-06-14T03:20:40Z\",\n" +
            "    \"git_url\": \"git://github.com/ityouknow/spring-boot-examples.git\",\n" +
            "    \"ssh_url\": \"git@github.com:ityouknow/spring-boot-examples.git\",\n" +
            "    \"clone_url\": \"https://github.com/ityouknow/spring-boot-examples.git\",\n" +
            "    \"svn_url\": \"https://github.com/ityouknow/spring-boot-examples\",\n" +
            "    \"homepage\": \"http://www.ityouknow.com/spring-boot.html\",\n" +
            "    \"size\": 1010,\n" +
            "    \"stargazers_count\": 26110,\n" +
            "    \"watchers_count\": 26110,\n" +
            "    \"language\": \"Java\",\n" +
            "    \"has_issues\": true,\n" +
            "    \"has_projects\": true,\n" +
            "    \"has_downloads\": true,\n" +
            "    \"has_wiki\": true,\n" +
            "    \"has_pages\": false,\n" +
            "    \"forks_count\": 11429,\n" +
            "    \"mirror_url\": null,\n" +
            "    \"archived\": false,\n" +
            "    \"disabled\": false,\n" +
            "    \"open_issues_count\": 7,\n" +
            "    \"license\": null,\n" +
            "    \"forks\": 11429,\n" +
            "    \"open_issues\": 7,\n" +
            "    \"watchers\": 26110,\n" +
            "    \"default_branch\": \"master\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 148496687,\n" +
            "    \"node_id\": \"MDEwOlJlcG9zaXRvcnkxNDg0OTY2ODc=\",\n" +
            "    \"name\": \"SpringBoot-Labs\",\n" +
            "    \"full_name\": \"YunaiV/SpringBoot-Labs\",\n" +
            "    \"private\": false,\n" +
            "    \"owner\": {\n" +
            "      \"login\": \"YunaiV\",\n" +
            "      \"id\": 2015545,\n" +
            "      \"node_id\": \"MDQ6VXNlcjIwMTU1NDU=\",\n" +
            "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/2015545?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/YunaiV\",\n" +
            "      \"html_url\": \"https://github.com/YunaiV\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/YunaiV/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/YunaiV/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/YunaiV/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/YunaiV/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/YunaiV/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/YunaiV/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/YunaiV/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/YunaiV/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/YunaiV/received_events\",\n" +
            "      \"type\": \"User\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"html_url\": \"https://github.com/YunaiV/SpringBoot-Labs\",\n" +
            "    \"description\": \"一个涵盖六个专栏：Spring Boot 2.X、Spring Cloud、Spring Cloud Alibaba、Dubbo、分布式消息队列、分布式事务的仓库。希望胖友小手一抖，右上角来个 Star，感恩 1024\",\n" +
            "    \"fork\": false,\n" +
            "    \"url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs\",\n" +
            "    \"forks_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/forks\",\n" +
            "    \"keys_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/keys{/key_id}\",\n" +
            "    \"collaborators_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/collaborators{/collaborator}\",\n" +
            "    \"teams_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/teams\",\n" +
            "    \"hooks_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/hooks\",\n" +
            "    \"issue_events_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/issues/events{/number}\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/events\",\n" +
            "    \"assignees_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/assignees{/user}\",\n" +
            "    \"branches_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/branches{/branch}\",\n" +
            "    \"tags_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/tags\",\n" +
            "    \"blobs_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/git/blobs{/sha}\",\n" +
            "    \"git_tags_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/git/tags{/sha}\",\n" +
            "    \"git_refs_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/git/refs{/sha}\",\n" +
            "    \"trees_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/git/trees{/sha}\",\n" +
            "    \"statuses_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/statuses/{sha}\",\n" +
            "    \"languages_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/languages\",\n" +
            "    \"stargazers_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/stargazers\",\n" +
            "    \"contributors_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/contributors\",\n" +
            "    \"subscribers_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/subscribers\",\n" +
            "    \"subscription_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/subscription\",\n" +
            "    \"commits_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/commits{/sha}\",\n" +
            "    \"git_commits_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/git/commits{/sha}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/comments{/number}\",\n" +
            "    \"issue_comment_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/issues/comments{/number}\",\n" +
            "    \"contents_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/contents/{+path}\",\n" +
            "    \"compare_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/compare/{base}...{head}\",\n" +
            "    \"merges_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/merges\",\n" +
            "    \"archive_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/{archive_format}{/ref}\",\n" +
            "    \"downloads_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/downloads\",\n" +
            "    \"issues_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/issues{/number}\",\n" +
            "    \"pulls_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/pulls{/number}\",\n" +
            "    \"milestones_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/milestones{/number}\",\n" +
            "    \"notifications_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/notifications{?since,all,participating}\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/labels{/name}\",\n" +
            "    \"releases_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/releases{/id}\",\n" +
            "    \"deployments_url\": \"https://api.github.com/repos/YunaiV/SpringBoot-Labs/deployments\",\n" +
            "    \"created_at\": \"2018-09-12T14:51:46Z\",\n" +
            "    \"updated_at\": \"2021-08-25T09:43:55Z\",\n" +
            "    \"pushed_at\": \"2021-08-21T18:43:56Z\",\n" +
            "    \"git_url\": \"git://github.com/YunaiV/SpringBoot-Labs.git\",\n" +
            "    \"ssh_url\": \"git@github.com:YunaiV/SpringBoot-Labs.git\",\n" +
            "    \"clone_url\": \"https://github.com/YunaiV/SpringBoot-Labs.git\",\n" +
            "    \"svn_url\": \"https://github.com/YunaiV/SpringBoot-Labs\",\n" +
            "    \"homepage\": \"\",\n" +
            "    \"size\": 1757,\n" +
            "    \"stargazers_count\": 11682,\n" +
            "    \"watchers_count\": 11682,\n" +
            "    \"language\": \"Java\",\n" +
            "    \"has_issues\": true,\n" +
            "    \"has_projects\": true,\n" +
            "    \"has_downloads\": true,\n" +
            "    \"has_wiki\": true,\n" +
            "    \"has_pages\": false,\n" +
            "    \"forks_count\": 3823,\n" +
            "    \"mirror_url\": null,\n" +
            "    \"archived\": false,\n" +
            "    \"disabled\": false,\n" +
            "    \"open_issues_count\": 28,\n" +
            "    \"license\": null,\n" +
            "    \"forks\": 3823,\n" +
            "    \"open_issues\": 28,\n" +
            "    \"watchers\": 11682,\n" +
            "    \"default_branch\": \"master\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 8732561,\n" +
            "    \"node_id\": \"MDEwOlJlcG9zaXRvcnk4NzMyNTYx\",\n" +
            "    \"name\": \"useful-scripts\",\n" +
            "    \"full_name\": \"oldratlee/useful-scripts\",\n" +
            "    \"private\": false,\n" +
            "    \"owner\": {\n" +
            "      \"login\": \"oldratlee\",\n" +
            "      \"id\": 1063891,\n" +
            "      \"node_id\": \"MDQ6VXNlcjEwNjM4OTE=\",\n" +
            "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/1063891?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/oldratlee\",\n" +
            "      \"html_url\": \"https://github.com/oldratlee\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/oldratlee/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/oldratlee/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/oldratlee/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/oldratlee/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/oldratlee/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/oldratlee/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/oldratlee/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/oldratlee/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/oldratlee/received_events\",\n" +
            "      \"type\": \"User\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"html_url\": \"https://github.com/oldratlee/useful-scripts\",\n" +
            "    \"description\": \"\uD83D\uDC0C useful scripts for making developer's everyday life easier and happier, involved java, shell etc.\",\n" +
            "    \"fork\": false,\n" +
            "    \"url\": \"https://api.github.com/repos/oldratlee/useful-scripts\",\n" +
            "    \"forks_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/forks\",\n" +
            "    \"keys_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/keys{/key_id}\",\n" +
            "    \"collaborators_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/collaborators{/collaborator}\",\n" +
            "    \"teams_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/teams\",\n" +
            "    \"hooks_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/hooks\",\n" +
            "    \"issue_events_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/issues/events{/number}\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/events\",\n" +
            "    \"assignees_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/assignees{/user}\",\n" +
            "    \"branches_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/branches{/branch}\",\n" +
            "    \"tags_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/tags\",\n" +
            "    \"blobs_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/git/blobs{/sha}\",\n" +
            "    \"git_tags_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/git/tags{/sha}\",\n" +
            "    \"git_refs_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/git/refs{/sha}\",\n" +
            "    \"trees_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/git/trees{/sha}\",\n" +
            "    \"statuses_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/statuses/{sha}\",\n" +
            "    \"languages_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/languages\",\n" +
            "    \"stargazers_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/stargazers\",\n" +
            "    \"contributors_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/contributors\",\n" +
            "    \"subscribers_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/subscribers\",\n" +
            "    \"subscription_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/subscription\",\n" +
            "    \"commits_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/commits{/sha}\",\n" +
            "    \"git_commits_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/git/commits{/sha}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/comments{/number}\",\n" +
            "    \"issue_comment_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/issues/comments{/number}\",\n" +
            "    \"contents_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/contents/{+path}\",\n" +
            "    \"compare_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/compare/{base}...{head}\",\n" +
            "    \"merges_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/merges\",\n" +
            "    \"archive_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/{archive_format}{/ref}\",\n" +
            "    \"downloads_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/downloads\",\n" +
            "    \"issues_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/issues{/number}\",\n" +
            "    \"pulls_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/pulls{/number}\",\n" +
            "    \"milestones_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/milestones{/number}\",\n" +
            "    \"notifications_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/notifications{?since,all,participating}\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/labels{/name}\",\n" +
            "    \"releases_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/releases{/id}\",\n" +
            "    \"deployments_url\": \"https://api.github.com/repos/oldratlee/useful-scripts/deployments\",\n" +
            "    \"created_at\": \"2013-03-12T16:28:38Z\",\n" +
            "    \"updated_at\": \"2021-08-25T09:33:30Z\",\n" +
            "    \"pushed_at\": \"2021-08-17T06:58:18Z\",\n" +
            "    \"git_url\": \"git://github.com/oldratlee/useful-scripts.git\",\n" +
            "    \"ssh_url\": \"git@github.com:oldratlee/useful-scripts.git\",\n" +
            "    \"clone_url\": \"https://github.com/oldratlee/useful-scripts.git\",\n" +
            "    \"svn_url\": \"https://github.com/oldratlee/useful-scripts\",\n" +
            "    \"homepage\": \"https://github.com/oldratlee/useful-scripts\",\n" +
            "    \"size\": 1592,\n" +
            "    \"stargazers_count\": 5617,\n" +
            "    \"watchers_count\": 5617,\n" +
            "    \"language\": \"Shell\",\n" +
            "    \"has_issues\": true,\n" +
            "    \"has_projects\": false,\n" +
            "    \"has_downloads\": true,\n" +
            "    \"has_wiki\": false,\n" +
            "    \"has_pages\": false,\n" +
            "    \"forks_count\": 2420,\n" +
            "    \"mirror_url\": null,\n" +
            "    \"archived\": false,\n" +
            "    \"disabled\": false,\n" +
            "    \"open_issues_count\": 9,\n" +
            "    \"license\": {\n" +
            "      \"key\": \"apache-2.0\",\n" +
            "      \"name\": \"Apache License 2.0\",\n" +
            "      \"spdx_id\": \"Apache-2.0\",\n" +
            "      \"url\": \"https://api.github.com/licenses/apache-2.0\",\n" +
            "      \"node_id\": \"MDc6TGljZW5zZTI=\"\n" +
            "    },\n" +
            "    \"forks\": 2420,\n" +
            "    \"open_issues\": 9,\n" +
            "    \"watchers\": 5617,\n" +
            "    \"default_branch\": \"dev-2.x\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 206583780,\n" +
            "    \"node_id\": \"MDEwOlJlcG9zaXRvcnkyMDY1ODM3ODA=\",\n" +
            "    \"name\": \"vimac\",\n" +
            "    \"full_name\": \"dexterleng/vimac\",\n" +
            "    \"private\": false,\n" +
            "    \"owner\": {\n" +
            "      \"login\": \"dexterleng\",\n" +
            "      \"id\": 34204380,\n" +
            "      \"node_id\": \"MDQ6VXNlcjM0MjA0Mzgw\",\n" +
            "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/34204380?v=4\",\n" +
            "      \"gravatar_id\": \"\",\n" +
            "      \"url\": \"https://api.github.com/users/dexterleng\",\n" +
            "      \"html_url\": \"https://github.com/dexterleng\",\n" +
            "      \"followers_url\": \"https://api.github.com/users/dexterleng/followers\",\n" +
            "      \"following_url\": \"https://api.github.com/users/dexterleng/following{/other_user}\",\n" +
            "      \"gists_url\": \"https://api.github.com/users/dexterleng/gists{/gist_id}\",\n" +
            "      \"starred_url\": \"https://api.github.com/users/dexterleng/starred{/owner}{/repo}\",\n" +
            "      \"subscriptions_url\": \"https://api.github.com/users/dexterleng/subscriptions\",\n" +
            "      \"organizations_url\": \"https://api.github.com/users/dexterleng/orgs\",\n" +
            "      \"repos_url\": \"https://api.github.com/users/dexterleng/repos\",\n" +
            "      \"events_url\": \"https://api.github.com/users/dexterleng/events{/privacy}\",\n" +
            "      \"received_events_url\": \"https://api.github.com/users/dexterleng/received_events\",\n" +
            "      \"type\": \"User\",\n" +
            "      \"site_admin\": false\n" +
            "    },\n" +
            "    \"html_url\": \"https://github.com/dexterleng/vimac\",\n" +
            "    \"description\": \"Productive macOS keyboard-driven navigation\",\n" +
            "    \"fork\": false,\n" +
            "    \"url\": \"https://api.github.com/repos/dexterleng/vimac\",\n" +
            "    \"forks_url\": \"https://api.github.com/repos/dexterleng/vimac/forks\",\n" +
            "    \"keys_url\": \"https://api.github.com/repos/dexterleng/vimac/keys{/key_id}\",\n" +
            "    \"collaborators_url\": \"https://api.github.com/repos/dexterleng/vimac/collaborators{/collaborator}\",\n" +
            "    \"teams_url\": \"https://api.github.com/repos/dexterleng/vimac/teams\",\n" +
            "    \"hooks_url\": \"https://api.github.com/repos/dexterleng/vimac/hooks\",\n" +
            "    \"issue_events_url\": \"https://api.github.com/repos/dexterleng/vimac/issues/events{/number}\",\n" +
            "    \"events_url\": \"https://api.github.com/repos/dexterleng/vimac/events\",\n" +
            "    \"assignees_url\": \"https://api.github.com/repos/dexterleng/vimac/assignees{/user}\",\n" +
            "    \"branches_url\": \"https://api.github.com/repos/dexterleng/vimac/branches{/branch}\",\n" +
            "    \"tags_url\": \"https://api.github.com/repos/dexterleng/vimac/tags\",\n" +
            "    \"blobs_url\": \"https://api.github.com/repos/dexterleng/vimac/git/blobs{/sha}\",\n" +
            "    \"git_tags_url\": \"https://api.github.com/repos/dexterleng/vimac/git/tags{/sha}\",\n" +
            "    \"git_refs_url\": \"https://api.github.com/repos/dexterleng/vimac/git/refs{/sha}\",\n" +
            "    \"trees_url\": \"https://api.github.com/repos/dexterleng/vimac/git/trees{/sha}\",\n" +
            "    \"statuses_url\": \"https://api.github.com/repos/dexterleng/vimac/statuses/{sha}\",\n" +
            "    \"languages_url\": \"https://api.github.com/repos/dexterleng/vimac/languages\",\n" +
            "    \"stargazers_url\": \"https://api.github.com/repos/dexterleng/vimac/stargazers\",\n" +
            "    \"contributors_url\": \"https://api.github.com/repos/dexterleng/vimac/contributors\",\n" +
            "    \"subscribers_url\": \"https://api.github.com/repos/dexterleng/vimac/subscribers\",\n" +
            "    \"subscription_url\": \"https://api.github.com/repos/dexterleng/vimac/subscription\",\n" +
            "    \"commits_url\": \"https://api.github.com/repos/dexterleng/vimac/commits{/sha}\",\n" +
            "    \"git_commits_url\": \"https://api.github.com/repos/dexterleng/vimac/git/commits{/sha}\",\n" +
            "    \"comments_url\": \"https://api.github.com/repos/dexterleng/vimac/comments{/number}\",\n" +
            "    \"issue_comment_url\": \"https://api.github.com/repos/dexterleng/vimac/issues/comments{/number}\",\n" +
            "    \"contents_url\": \"https://api.github.com/repos/dexterleng/vimac/contents/{+path}\",\n" +
            "    \"compare_url\": \"https://api.github.com/repos/dexterleng/vimac/compare/{base}...{head}\",\n" +
            "    \"merges_url\": \"https://api.github.com/repos/dexterleng/vimac/merges\",\n" +
            "    \"archive_url\": \"https://api.github.com/repos/dexterleng/vimac/{archive_format}{/ref}\",\n" +
            "    \"downloads_url\": \"https://api.github.com/repos/dexterleng/vimac/downloads\",\n" +
            "    \"issues_url\": \"https://api.github.com/repos/dexterleng/vimac/issues{/number}\",\n" +
            "    \"pulls_url\": \"https://api.github.com/repos/dexterleng/vimac/pulls{/number}\",\n" +
            "    \"milestones_url\": \"https://api.github.com/repos/dexterleng/vimac/milestones{/number}\",\n" +
            "    \"notifications_url\": \"https://api.github.com/repos/dexterleng/vimac/notifications{?since,all,participating}\",\n" +
            "    \"labels_url\": \"https://api.github.com/repos/dexterleng/vimac/labels{/name}\",\n" +
            "    \"releases_url\": \"https://api.github.com/repos/dexterleng/vimac/releases{/id}\",\n" +
            "    \"deployments_url\": \"https://api.github.com/repos/dexterleng/vimac/deployments\",\n" +
            "    \"created_at\": \"2019-09-05T14:27:51Z\",\n" +
            "    \"updated_at\": \"2021-08-25T06:47:10Z\",\n" +
            "    \"pushed_at\": \"2021-05-15T08:11:52Z\",\n" +
            "    \"git_url\": \"git://github.com/dexterleng/vimac.git\",\n" +
            "    \"ssh_url\": \"git@github.com:dexterleng/vimac.git\",\n" +
            "    \"clone_url\": \"https://github.com/dexterleng/vimac.git\",\n" +
            "    \"svn_url\": \"https://github.com/dexterleng/vimac\",\n" +
            "    \"homepage\": \"https://vimacapp.com\",\n" +
            "    \"size\": 7603,\n" +
            "    \"stargazers_count\": 2326,\n" +
            "    \"watchers_count\": 2326,\n" +
            "    \"language\": \"Swift\",\n" +
            "    \"has_issues\": true,\n" +
            "    \"has_projects\": true,\n" +
            "    \"has_downloads\": true,\n" +
            "    \"has_wiki\": true,\n" +
            "    \"has_pages\": false,\n" +
            "    \"forks_count\": 49,\n" +
            "    \"mirror_url\": null,\n" +
            "    \"archived\": false,\n" +
            "    \"disabled\": false,\n" +
            "    \"open_issues_count\": 102,\n" +
            "    \"license\": {\n" +
            "      \"key\": \"gpl-3.0\",\n" +
            "      \"name\": \"GNU General Public License v3.0\",\n" +
            "      \"spdx_id\": \"GPL-3.0\",\n" +
            "      \"url\": \"https://api.github.com/licenses/gpl-3.0\",\n" +
            "      \"node_id\": \"MDc6TGljZW5zZTk=\"\n" +
            "    },\n" +
            "    \"forks\": 49,\n" +
            "    \"open_issues\": 102,\n" +
            "    \"watchers\": 2326,\n" +
            "    \"default_branch\": \"master\"\n" +
            "  } ]";

    @Test
    void json() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Repo> repo = objectMapper.reader()
                .forType(new TypeReference<List<Repo>>() {
                })
                .readValue(json);
        repo.forEach(System.out::println);
    }

    @Test
    void regex() {
        String word = "c++  abc *() ==0-0";
        System.out.println(word.trim().toLowerCase().replaceAll("\\W", "-"));
    }

    @Test
    void sortMap() {
        List<Repo> repos = JsonUtil.toObject(json, new TypeReference<List<Repo>>() {
        });
        Map<String, List<Repo>> map = repos.stream()
                .sorted(Comparator
                        .comparing(Repo::getWatchers, Comparator.reverseOrder())
                        .thenComparing(Repo::getForks, Comparator.reverseOrder()))
                .collect(Collectors.groupingBy(Repo::getLanguage));
        map.forEach((k,v)->{
            System.out.println(k);
            v.forEach(System.out::println);
            System.out.println("--------------");
        });
    }

}
