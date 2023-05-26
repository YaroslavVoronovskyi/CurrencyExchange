package com.gmail.voronovskyi.yaroslav.currencyexchange.config;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AppConfig.class)
public class AppConfigTest {

    @Bean
    public TestRestTemplate restTemplate() {
        return new TestRestTemplate();
    }
}
