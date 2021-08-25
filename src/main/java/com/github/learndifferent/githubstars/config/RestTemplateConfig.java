package com.github.learndifferent.githubstars.config;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * RestTemplate 的配置类
 * <p>问题 1：sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target</p>
 * <p>解决问题 1：参考 <a href="https://blog.codeleak.pl/2016/02/skip-ssl-certificate-verification-in.html">Skip SSL certificate verification in Spring Rest Template</a></p>
 * <p>问题 2： HttpClientErrorException$Forbidden: 403 rate limit exceeded</p>
 * <p>解决问题 2：参考 <a href="https://blog.csdn.net/weixin_43274247/article/details/106577653">如何增加GitHub api的访问次数（从每小时60到每小时5000）</a></p>
 */
@Configuration
public class RestTemplateConfig {

    @Value("${access-token}")
    public String token;

    @Bean
    public RestTemplate restTemplate(HttpComponentsClientHttpRequestFactory requestFactory) {
        return new RestTemplate(requestFactory);
    }

    @Bean
    public HttpComponentsClientHttpRequestFactory requestFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();

        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        Header header = new BasicHeader(HttpHeaders.AUTHORIZATION, "token " + token);
        List<Header> headers = new ArrayList<>();
        headers.add(header);

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .setDefaultHeaders(headers)
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
        return requestFactory;
    }
}
