package org.aery.line.meow.receiver.text.api;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import org.aery.line.meow.receiver.api.MeowReceiver;

import java.util.function.Consumer;

public interface SpecifiedTextReceiver extends MeowReceiver<TextMessageContent> {

    @Override
    default Message handle(MessageEvent<TextMessageContent> event) throws Exception {
        TextMessageContent textMessageContent = event.getMessage();
        String message = textMessageContent.getText();
        return service(message, event);
    }

    void setupHandledText(Consumer<String> specifiedTextSetter);

    Message service(String message, MessageEvent<TextMessageContent> event) throws Exception;

}
