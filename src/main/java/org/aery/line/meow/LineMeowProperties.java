package org.aery.line.meow;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "line.meow")
@Component
public class LineMeowProperties {

    private int requestLimitSecond = 5;

    public int getRequestLimitSecond() {
        return requestLimitSecond;
    }

    public void setRequestLimitSecond(int requestLimitSecond) {
        this.requestLimitSecond = requestLimitSecond;
    }
}
