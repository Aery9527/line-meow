package org.aery.line.meow.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aery.line.meow.LineMeowProperties;
import org.aery.line.meow.service.api.PersonalRequestLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class PersonalRequestLimiterPreset implements PersonalRequestLimiter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LineMeowProperties lineMeowProperties;

    private Cache<String, Long> cache;

    @PostConstruct
    public void initial() {
        int requestLimitMillisecond = this.lineMeowProperties.getRequestLimitMillisecond();
        this.cache = CacheBuilder.newBuilder()
                .expireAfterWrite(requestLimitMillisecond, TimeUnit.MILLISECONDS)
                .concurrencyLevel(10) // 設定併發級別為10
                .recordStats() // 開啟快取統計
                .build();

        this.logger.info("requestLimitSecond : " + requestLimitMillisecond + "ms");
    }

    @Override
    public <Result> Result limiting(String userId, Supplier<Result> allowAction, Supplier<Result> denyAction) {
        try {
            long currentTime = System.currentTimeMillis();
            Long cacheTs = this.cache.get(userId, () -> currentTime);
            boolean allow = currentTime == cacheTs;
            return allow ? allowAction.get() : denyAction.get();

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
