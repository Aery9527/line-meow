package org.aery.line.meow.service.impl;

import org.aery.line.meow.service.api.MeowImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
public class MeowImageServicePreset implements MeowImageService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String meowApi = "https://api.thecatapi.com/v1/images/search";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${CAT_API_KEY:}")
    private String catApiKey;

    @PostConstruct
    public void init() {
        logger.info("catApiKey configured: {}", catApiKey.isEmpty() ? "NO (empty)" : "YES (length=" + catApiKey.length() + ")");
    }

    @Override
    public URI randomUri() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        if (!catApiKey.isEmpty()) {
            headers.set("x-api-key", catApiKey);
        }
        logger.info("calling thecatapi with x-api-key header: {}", !catApiKey.isEmpty());
        List list = restTemplate.exchange(meowApi, HttpMethod.GET, new HttpEntity<>(headers), List.class).getBody();
        Map map = (Map) list.get(0);
        return new URI((String) map.get("url"));
    }

}
