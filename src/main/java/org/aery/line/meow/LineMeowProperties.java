package org.aery.line.meow;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "line.meow")
@Component
public class LineMeowProperties {

    private int requestLimitMillisecond = 5000;

    public int getRequestLimitMillisecond() {
        return requestLimitMillisecond;
    }

    public void setRequestLimitMillisecond(int requestLimitMillisecond) {
        this.requestLimitMillisecond = requestLimitMillisecond;
    }

}
