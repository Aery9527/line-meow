package org.aery.line.meow.receiver.text.impl;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.ImageMessage;
import com.linecorp.bot.model.message.Message;
import org.aery.line.meow.receiver.text.api.SpecifiedTextReceiver;
import org.aery.line.meow.service.api.MeowImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.function.Consumer;

@Component
public class RandomMeowReceiver implements SpecifiedTextReceiver {

    @Autowired
    private MeowImageService meowImageService;

    @Override
    public void setupHandledText(Consumer<String> specifiedTextSetter) {
        specifiedTextSetter.accept("貓貓");
    }

    @Override
    public Message service(String message, MessageEvent<TextMessageContent> event) throws Exception {
        URI url = this.meowImageService.randomUri();
        URI originalContentUrl = url;
        URI previewImageUrl = url;
        return new ImageMessage(originalContentUrl, previewImageUrl);
    }

}
