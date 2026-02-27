package main.application.subscription.interactor;

import main.application.subscription.dto.ProcessPaymentRequest;
import main.application.subscription.dto.PaymentResponse;
import main.application.subscription.external.SubscriptionRepository;
import main.application.subscription.internal.ProcessPaymentUseCase;
import main.domain.subscription.internal.Subscription;
import main.domain.subscription.entity.Payment;

public class ProcessPaymentInteractor implements ProcessPaymentUseCase {
    private final SubscriptionRepository subscriptionRepository;

    public ProcessPaymentInteractor(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public PaymentResponse execute(ProcessPaymentRequest request) {
        Long subscriptionId = request.getSubscriptionId();
        Subscription subscription = subscriptionRepository.findById(subscriptionId);

        if (request.isPaymentSucceeded()) {
            subscription.payPayment();
        } else {
            subscription.failPayment();
        }

        subscriptionRepository.update(subscription);

        Payment payment = subscription.getPayment();
        return new PaymentResponse(
            subscription.getId(),
            payment.getStatus().toString(),
            payment.getCharge(),
            subscription.getStatus().toString()
        );
    }
}
