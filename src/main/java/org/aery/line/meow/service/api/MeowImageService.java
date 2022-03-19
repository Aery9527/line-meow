package org.aery.line.meow.service.api;

import java.net.URI;
import java.net.URISyntaxException;

public interface MeowImageService {

    URI randomUri() throws URISyntaxException;

}
