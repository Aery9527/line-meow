package org.aery.line.meow.service.impl;

import org.aery.line.meow.service.api.MeowImageService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest
public class MeowImageServicePresetTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MeowImageService meowImageService;

    @Test
    public void randomUri() throws URISyntaxException {
        URI url = this.meowImageService.randomUri();
        this.logger.info(url.toString());
    }

}
