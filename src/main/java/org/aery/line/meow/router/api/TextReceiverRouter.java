package org.aery.line.meow.router.api;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import org.aery.line.meow.receiver.api.MeowReceiver;

public interface TextReceiverRouter extends MeowReceiver<TextMessageContent> {

    @Override
    default Message handle(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent textMessageContent = event.getMessage();
        String message = textMessageContent.getText();
        return route(message, event);
    }

    Message route(String message, MessageEvent<TextMessageContent> event) throws Exception;
}
