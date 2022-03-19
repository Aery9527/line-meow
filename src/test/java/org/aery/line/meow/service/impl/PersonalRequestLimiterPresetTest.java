package org.aery.line.meow.service.impl;

import org.aery.line.meow.service.api.PersonalRequestLimiter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest(properties = {
        "line.meow.request-limit-second=" + PersonalRequestLimiterPresetTest.TEST_SECOND
})
class PersonalRequestLimiterPresetTest {

    public static final int TEST_SECOND = 1;

    public static final int TEST_MILLISECONDS = TEST_SECOND * 1000;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PersonalRequestLimiter personalRequestLimiter;

    @Test
    public void limiting() throws Exception {
        String userId = "9527";

        AtomicReference<Boolean> allowCheckPoint = new AtomicReference<>();
        AtomicReference<Boolean> denyCheckPoint = new AtomicReference<>();

        Runnable test = () -> {
            allowCheckPoint.set(null);
            denyCheckPoint.set(null);

            this.personalRequestLimiter.limiting(userId, () -> {
                allowCheckPoint.set(true);
                return null;
            }, () -> {
                denyCheckPoint.set(true);
                return null;
            });
        };

        test.run();
        Assertions.assertThat(allowCheckPoint.get()).isNotNull().isTrue();
        Assertions.assertThat(denyCheckPoint.get()).isNull();

        Thread.sleep(TEST_MILLISECONDS / 2);

        test.run();
        Assertions.assertThat(allowCheckPoint.get()).isNull();
        Assertions.assertThat(denyCheckPoint.get()).isNotNull().isTrue();

        Thread.sleep((TEST_MILLISECONDS / 2) + 1);

        test.run();
        Assertions.assertThat(allowCheckPoint.get()).isNotNull().isTrue();
        Assertions.assertThat(denyCheckPoint.get()).isNull();
    }

}
