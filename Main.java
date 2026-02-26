import controller.Controller;
import domain.plan.Plan;
import domain.plan.PlanType;
import domain.user.User;
import repository.MemoryRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MemoryRepository.plans.put(1L, new Plan(1L, PlanType.FREE, 0L, 100L));
        MemoryRepository.plans.put(2L, new Plan(2L, PlanType.PRO, 9900L, 1000L));
        MemoryRepository.plans.put(3L, new Plan(3L, PlanType.BUSINESS, 29900L, 10000L));

        Long currentUserId = 1L;
        MemoryRepository.users.put(currentUserId, new User(currentUserId));

        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            controller.showMenu();
            String input = scanner.nextLine();

            if (input.equals("1")) controller.listPlans();
            else if (input.equals("2")) controller.myInfo(currentUserId);
            else if (input.equals("3")) controller.subscribe(currentUserId);
            else if (input.equals("4")) controller.simulatePayment();
            else if (input.equals("5")) controller.cancelSubscription(currentUserId);
            else if (input.equals("0")) {
                System.out.println("종료합니다.");
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
}