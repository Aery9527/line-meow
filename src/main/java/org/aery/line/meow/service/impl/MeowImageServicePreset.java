package org.aery.line.meow.service.impl;

import org.aery.line.meow.service.api.MeowImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@Service
public class MeowImageServicePreset implements MeowImageService {

    private String meowApi = "https://api.thecatapi.com/v1/images/search";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public URI randomUri() throws URISyntaxException {
        List list = this.restTemplate.getForObject(this.meowApi, List.class);
        Map map = (Map) list.get(0);
        String url = (String) map.get("url");
        return new URI(url);
    }

}
