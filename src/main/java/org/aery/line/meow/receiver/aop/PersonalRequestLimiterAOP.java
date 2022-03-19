package org.aery.line.meow.receiver.aop;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.source.Source;
import org.aery.line.meow.service.api.PersonalRequestLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PersonalRequestLimiterAOP {

    @Autowired
    private PersonalRequestLimiter personalRequestLimiter;

    @Around(value = "within(org.aery.line.meow.receiver..*) " +
            "&& execution(* org.aery.line.meow.receiver.api.MeowReceiver+.handle(com.linecorp.bot.model.event.MessageEvent))"
    )
    public Object around(ProceedingJoinPoint pjp) throws Exception {
        Object[] args = pjp.getArgs();
        MessageEvent<?> event = (MessageEvent<?>) args[0];
        Source source = event.getSource();
        String userId = source.getUserId();
        return this.personalRequestLimiter.limiting(userId, () -> {
            try {
                return pjp.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }


}
