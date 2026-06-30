package org.aery.line.meow.service.impl;

import org.aery.line.meow.service.api.MeowImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
public class MeowImageServicePreset implements MeowImageService {

    private final String meowApi = "https://api.thecatapi.com/v1/images/search";

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${cat.api.key:}")
    private String catApiKey;

    @Override
    public URI randomUri() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        if (!catApiKey.isEmpty()) {
            headers.set("x-api-key", catApiKey);
        }
        List list = restTemplate.exchange(meowApi, HttpMethod.GET, new HttpEntity<>(headers), List.class).getBody();
        Map map = (Map) list.get(0);
        return new URI((String) map.get("url"));
    }

}
