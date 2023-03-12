package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ro.itschool.entity.Client;
import ro.itschool.entity.Order;
import ro.itschool.entity.Product;
import ro.itschool.repository.OrderRepository;
import ro.itschool.repository.ShoppingCartProductQuantityRepository;
import ro.itschool.repository.ProductRepository;
import ro.itschool.service.impl.CartServiceImpl;
import ro.itschool.service.ClientService;

import java.util.List;

import static ro.itschool.util.Constants.CART_PAGE;
import static ro.itschool.util.Constants.ORDER_SUCCESSFUL_PAGE;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/cart")
public class CartController {
    private final CartServiceImpl cartService;
    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;
    private final ClientService clientService;
    private final ShoppingCartProductQuantityRepository quantityRepository;

    @RequestMapping(value = "/to-order")
    public String convertToOrder(Model model) {

        //stabilim care e username-ul user-ului autentificat
        Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //aducem userul din db pe baza username-ului

        Client clientByUsername = clientService.findClientByUsername(principal.getUsername());

        List<Product> productsByShoppingCartId = quantityRepository.getProductsByShoppingCartId(clientByUsername.getId());
        cartService.findById(clientByUsername.getId().intValue()).ifPresent(cart -> {
            cart.setProducts(productsByShoppingCartId);
            clientByUsername.setCart(cart);
        });

      Order order = orderRepository.save(cartService.convertCartToOrder(clientByUsername.getCart()));

        clientByUsername.getCart().getProducts().clear();
        quantityRepository.deleteByShoppingCartId(clientByUsername.getId().intValue());
        model.addAttribute("order", order);

        return ORDER_SUCCESSFUL_PAGE;
    }

    @RequestMapping
    public String getCartForPrincipal(Model model) {
        Client principal = (Client) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //aducem userul din db pe baza username-ului
        Client clientByUsername = clientService.findClientByUsername(principal.getUsername());

        List<Product> productsByShoppingCartId = quantityRepository.getProductsByShoppingCartId(clientByUsername.getId());

        model.addAttribute("products", productsByShoppingCartId);

        return CART_PAGE;
    }



}