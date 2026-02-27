package main.application.subscription.interactor;

import main.application.subscription.dto.CreateSubscriptionRequest;
import main.application.subscription.dto.SubscriptionResponse;
import main.application.subscription.external.PlanRepository;
import main.application.subscription.external.SubscriptionRepository;
import main.application.subscription.external.UserRepository;
import main.application.subscription.internal.CreateSubscriptionUseCase;
import main.domain.plan.internal.Plan;
import main.domain.subscription.entity.Payment;
import main.domain.subscription.internal.Subscription;
import main.domain.subscription.internal.SubscriptionRoot;
import main.domain.subscription.vo.Status;
import main.domain.user.internal.User;

import java.time.LocalDate;

public class CreateSubscriptionInteractor implements CreateSubscriptionUseCase {
    private final SubscriptionRepository subscriptionRepository;
    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public CreateSubscriptionInteractor(SubscriptionRepository subscriptionRepository,
                                      PlanRepository planRepository,
                                      UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SubscriptionResponse execute(CreateSubscriptionRequest request) {
        Long userId = request.getUserId();
        Long planId = request.getPlanId();

        User user = userRepository.findById(userId);
        Plan plan = planRepository.findById(planId);

        Subscription subscription = createNewSubscription(plan);
        subscriptionRepository.save(subscription);

        return new SubscriptionResponse(
            subscription.getId(),
            subscription.getStatus().toString(),
            planId,
            subscription.getCharge(),
            subscription.getStartDate(),
            subscription.getEndDate()
        );
    }

    private Subscription createNewSubscription(Plan plan) {
        Long charge = plan.getCharge();
        Long period = plan.getPeriod();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(period);
        Payment payment = new Payment(charge);
        Status status = Status.DEACTIVATED;

        return new SubscriptionRoot(null, status, plan, charge, period, startDate, endDate, payment);
    }
}
