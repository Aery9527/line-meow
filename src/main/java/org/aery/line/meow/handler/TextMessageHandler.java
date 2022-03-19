package org.aery.line.meow.handler;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.aery.line.meow.router.api.TextReceiverRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@LineMessageHandler
public class TextMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TextReceiverRouter textReceiverRouter;

    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) throws Exception {
        return this.textReceiverRouter.handle(event);
    }

}
