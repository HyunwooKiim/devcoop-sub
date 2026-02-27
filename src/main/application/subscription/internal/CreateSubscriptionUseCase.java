package main.application.subscription.internal;

import main.application.subscription.dto.CreateSubscriptionRequest;
import main.application.subscription.dto.SubscriptionResponse;

public interface CreateSubscriptionUseCase {
    SubscriptionResponse execute(CreateSubscriptionRequest request);
}
