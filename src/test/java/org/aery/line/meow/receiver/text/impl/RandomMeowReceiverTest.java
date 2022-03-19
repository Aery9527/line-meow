package org.aery.line.meow.receiver.text.impl;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.event.source.UserSource;
import org.aery.line.meow.receiver.aop.PersonalRequestLimiterAOP;
import org.aery.line.meow.service.api.PersonalRequestLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
public class RandomMeowReceiverTest {

    @Autowired
    private RandomMeowReceiver randomMeowReceiver;

    @SpyBean
    private PersonalRequestLimiter personalRequestLimiter;

    /**
     * {@link PersonalRequestLimiterAOP#around(ProceedingJoinPoint)}
     */
    @Test
    public void aop() throws Exception {
        final String userId = "9527";

        Source source = UserSource.builder().userId(userId).build();
        MessageEvent.MessageEventBuilder<TextMessageContent> builder = MessageEvent.builder();
        MessageEvent<TextMessageContent> event = builder.source(source).build();

        AtomicReference<String> checkPoint = new AtomicReference<>();

        Mockito.doAnswer((invocationOnMock) -> {
            String receiveUserId = invocationOnMock.getArgument(0, String.class);
            checkPoint.set(receiveUserId);
            return null;
        }).when(this.personalRequestLimiter).limiting(Mockito.anyString(), Mockito.any(), Mockito.any());

        this.randomMeowReceiver.handle(event);

        Assertions.assertThat(checkPoint.get()).isNotNull().isEqualTo(userId);
    }

}
