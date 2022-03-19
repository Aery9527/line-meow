package org.aery.line.meow.mapping;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@LineMessageHandler
public class EchoMessageHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @EventMapping
    public Message handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        this.logger.info("event: " + event);
        final String originalMessageText = event.getMessage().getText();
        return new TextMessage(originalMessageText);
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }

}
