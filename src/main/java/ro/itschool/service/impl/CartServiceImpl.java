package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Cart;
import ro.itschool.entity.Order;
import ro.itschool.repository.CartRepository;
import ro.itschool.service.CartService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    public Optional<Cart> findById(Integer id) {
        return cartRepository.findById(id);
    }

    public Order convertCartToOrder(Cart cart) {
        Order order = new Order();
        order.getProducts().addAll(cart.getProducts());
        order.setOrderDate(LocalDateTime.now());
        order.setClient(cart.getClient()); //cu SpringSecurity folosim Principal - adica usarul care este logat in momentul de fata
        return order;

    }

    public Cart update(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart save(Cart sc) {
        return cartRepository.save(sc);
    }

    public void deleteProductByIdFromCart(Integer productId) {
        cartRepository.findAll().stream()
                .filter(cart -> cart.getProducts().removeIf(product -> product.getId().equals(productId)))
                .toList();

        // public List<Product> findAll(Product product) {
        //   return cartRepository.findAll();
        // }
    }

    @Override
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }
}
