# github-stars

Generate a list of GitHub user's stars and save it to a Markdown file.

## Table of Contents

* [Cloning the repository](#cloning-the-repository)
* [Configuration](#configuration)
* [Building and Running](#building-and-running)
* [Usage](#usage)
* [Defining Custom Sorting Order](#defining-custom-sorting-order)
* [My GitHub Stars](#my-github-stars)
* [License](#license)

## Cloning the repository

Clone the repository into a local directory:

```bash
# Clone the repository
git clone git@github.com:LearnDifferent/github-stars.git

# Go into the repository
cd github-stars
```

Or you can [click here](https://github.com/LearnDifferent/github-stars/archive/refs/heads/master.zip) to download the zip file containing the code.

## Configuration

[Create a personal access token](https://github.com/settings/tokens) and paste your access token into `access-token` field in [application.yml](https://github.com/LearnDifferent/github-stars/blob/master/src/main/resources/application.yml) :

```yaml
spring:
  application:
    name: github-stars
server:
  port: 8080
access-token: # paste your access token
```

---

FAQ:

Why do I need a personal access token?

- [For unauthenticated requests, the rate limit allows for up to 60 requests per hour](https://docs.github.com/en/rest/overview/resources-in-the-rest-api#rate-limiting) and this application may exceed the rate limit.

How to create a personal access token?

- Checkout [Creating a personal access token](https://docs.github.com/en/github/authenticating-to-github/keeping-your-account-and-data-secure/creating-a-personal-access-token#creating-a-token)

If you don't use a personal access token, the application will throw an exception：

```
org.springframework.web.client.HttpClientErrorException$Unauthorized: 401 Unauthorized: [{"message":"Bad credentials","documentation_url":"https://docs.github.com/rest"}]
```

## Building and Running

Build a JAR file and run it from the command line:

```bash
# Build a JAR file
./mvnw package

# Run the JAR file
java -jar target/*.jar
```

Or you can run the application from Maven directly using the Spring Boot Maven plugin:

```bash
./mvnw spring-boot:run
```

## Usage

| Method | Path               | Description                                                  |
| ------ | ------------------ | ------------------------------------------------------------ |
| GET    | /                  | Return "Hello".                                              |
| GET    | /{username}        | Generate a list of GitHub user's stars, which will only show the major programming language of the starred repositories. |
| GET    | /{username}/{mode} | Generate a list of GitHub user's stars according to the mode. |

> The list of GitHub user's stars will be saved to a Markdown file in current working directory.

---

To check if application is running:

```bash
curl http://localhost:8080
```

You will receive the response "Hello".

---

To generate a list of GitHub user's stars by default:

```bash
curl http://localhost:8080/{username}
```

Remember to replace the `{username}` placeholder with the actual username.

---

To choose whether to show all the programming languages:

```bash
curl http://localhost:8080/{username}/{mode}
```

You can replace the `{mode}` placeholder with `0` or `1` :

- 0: Get all languages
- 1: Get only the main language

For example, if you want the list to show all the programming languages of the starred repositories:

```bash
curl http://localhost:8080/{username}/0
```

Or you can let the list to show only the major language of the starred repositories:

```bash
curl http://localhost:8080/{username}/1
```

## Defining Custom Sorting Order

The sort order is defined in [RepoServiceImpl.java](./src/main/java/com/github/learndifferent/githubstars/service/impl/RepoServiceImpl.java):

```java
@Override
public LinkedHashMap<String, List<Repo>> getSortedRepoMap(List<Repo> repos) {
    log.info("Sort Starred Repo by Language, Stars and Forks.");
    return repos.stream()
            .sorted(Comparator
                    .comparing(Repo::getWatchers, Comparator.reverseOrder())
                    .thenComparing(Repo::getForks, Comparator.reverseOrder()))
            .peek(repo -> {
                if (repo.getLanguage() == null) {
                    repo.setLanguage("Others");
                }
            })
            .collect(Collectors.groupingBy(
                    Repo::getLanguage,
                    LinkedHashMap::new,
                    Collectors.toList()))
            .entrySet().stream()
            .sorted(Comparator.comparing(entry -> {
                        if (entry.getKey().equalsIgnoreCase("others")) {
                            return -1;
                        }
                        return entry.getValue().size();
                    }, Comparator.reverseOrder())
            )
            .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (a, b) -> {
                        throw new ServiceException("Key Conflicts");
                    },
                    LinkedHashMap::new));
}
```

You can change the code to define a custom sort order.

## My GitHub Stars

Checkout [My GitHub Stars](https://github.com/LearnDifferent/my-github-stars)

## License

Released under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0.txt).