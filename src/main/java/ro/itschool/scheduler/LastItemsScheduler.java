package ro.itschool.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ro.itschool.service.CartService;
import ro.itschool.service.ProductService;

@Component
@RequiredArgsConstructor
public class LastItemsScheduler {

    private final ProductService productService;
    // private final CartService cartService;

    @Scheduled(cron = "10 */2 * * * *")
    public void notifyOnlyFewItemsLeft() {
        productService.findAll().stream()
                .filter(product -> product.getQuantity() < 3)
                .forEach(product -> System.out.println("Sendig mail to client: " + product.getQuantity()));
    }

//       @Scheduled(cron = "*/10 * * * * *")
//    public void notifyItemsInCart() {
//        cartService.findAll().stream()
//                .forEach(product -> System.out.println("It looks like you forgot something in cart. Would you like to order?"));
//
//    }


}