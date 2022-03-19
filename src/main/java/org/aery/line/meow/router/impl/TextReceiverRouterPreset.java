package org.aery.line.meow.router.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import org.aery.line.meow.receiver.text.api.SpecifiedTextReceiver;
import org.aery.line.meow.router.api.TextReceiverRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class TextReceiverRouterPreset implements TextReceiverRouter {

    static class UnhandledSpecifiedTextReceiver implements SpecifiedTextReceiver {
        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public void setupHandledText(Consumer<String> specifiedTextSetter) {
        }

        @Override
        public Message service(String message, MessageEvent<TextMessageContent> event) {
            this.logger.debug("unhandled : {}", message);
            return null;
        }
    }

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UnhandledSpecifiedTextReceiver unhandledSpecifiedTextReceiver = new UnhandledSpecifiedTextReceiver();

    @Autowired
    private ObjectProvider<SpecifiedTextReceiver> specifiedTextReceivers;

    @Autowired
    private ObjectMapper objectMapper;

    private final Map<String, SpecifiedTextReceiver> specifiedTextReceiverMapper = new HashMap<>();

    @PostConstruct
    public void initial() throws JsonProcessingException {
        Function<SpecifiedTextReceiver, String> receiverToString = (receiver) -> {
            return receiver.getClass().getSimpleName() + "(" + receiver + ")";
        };

        Map<String, List<String>> duplicate = new HashMap<>();
        BiConsumer<String, SpecifiedTextReceiver> recordDuplicateReceiver = (textTerm, receiver) -> {
            List<String> list = duplicate.computeIfAbsent(textTerm, key -> new ArrayList<>());
            list.add(receiverToString.apply(receiver));
        };

        this.specifiedTextReceivers.forEach((receiver) -> {
            receiver.setupHandledText((textTerm) -> {
                SpecifiedTextReceiver existingReceiver = this.specifiedTextReceiverMapper.put(textTerm, receiver);
                if (existingReceiver != null) {
                    recordDuplicateReceiver.accept(textTerm, receiver);
                    recordDuplicateReceiver.accept(textTerm, existingReceiver);
                }
            });
        });

        ObjectWriter prettyPrinter = this.objectMapper.writerWithDefaultPrettyPrinter();
        if (duplicate.isEmpty()) {
            Map<String, String> map = this.specifiedTextReceiverMapper.entrySet().stream()
                    .reduce(new HashMap<>(), (m, entry) -> {
                        String key = entry.getKey();
                        String val = receiverToString.apply(entry.getValue());
                        m.put(key, val);
                        return m;
                    }, (m1, m2) -> null);
            String mappingMsg = prettyPrinter.writeValueAsString(map);
            this.logger.info(SpecifiedTextReceiver.class.getSimpleName() + " mapping : " + mappingMsg);
        } else {
            String duplicateTextTermMsg = prettyPrinter.writeValueAsString(duplicate);
            throw new RuntimeException(duplicateTextTermMsg);
        }
    }

    @Override
    public Message route(String message, MessageEvent<TextMessageContent> event) throws Exception {
        SpecifiedTextReceiver receiver = this.specifiedTextReceiverMapper.getOrDefault(message, this.unhandledSpecifiedTextReceiver);
        return receiver.handle(event);
    }

}
