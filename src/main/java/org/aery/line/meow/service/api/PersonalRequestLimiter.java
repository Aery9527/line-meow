package org.aery.line.meow.service.api;

import java.util.function.Supplier;

public interface PersonalRequestLimiter {

    default <Result> Result limiting(String userId, Supplier<Result> allowAction) {
        return limiting(userId, allowAction, () -> null);
    }

    <Result> Result limiting(String userId, Supplier<Result> allowAction, Supplier<Result> denyAction);

}
