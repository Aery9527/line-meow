package org.aery.line.meow.service.impl;

import org.aery.line.meow.service.api.MeowImageService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Service
public class MeowImageServicePreset implements MeowImageService {

    // cataas.com doesn't block GCP Cloud Run ASNs unlike thecatapi.com (Cloudflare error 1010)
    private final String meowApi = "https://cataas.com/cat?json=true";

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public URI randomUri() throws URISyntaxException {
        Map map = restTemplate.exchange(meowApi, HttpMethod.GET, HttpEntity.EMPTY, Map.class).getBody();
        return new URI((String) map.get("url"));
    }

}
