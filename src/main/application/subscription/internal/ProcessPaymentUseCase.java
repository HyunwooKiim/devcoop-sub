package main.application.subscription.internal;

import main.application.subscription.dto.ProcessPaymentRequest;
import main.application.subscription.dto.PaymentResponse;

public interface ProcessPaymentUseCase {
    PaymentResponse execute(ProcessPaymentRequest request);
}
