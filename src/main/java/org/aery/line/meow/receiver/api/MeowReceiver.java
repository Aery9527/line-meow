package org.aery.line.meow.receiver.api;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.MessageContent;
import com.linecorp.bot.model.message.Message;

public interface MeowReceiver<MessageContentType extends MessageContent> {

    Message handle(MessageEvent<MessageContentType> event) throws Exception;

}
