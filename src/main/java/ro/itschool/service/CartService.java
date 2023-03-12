package ro.itschool.service;

import ro.itschool.entity.Cart;
import ro.itschool.entity.Order;

import java.util.List;
import java.util.Optional;

public interface CartService {
     Optional<Cart> findById(Integer id);
     Order convertCartToOrder(Cart cart);
     Cart update(Cart cart);
     Cart save(Cart sc);
     void deleteProductByIdFromCart(Integer productId);

    List<Cart> findAll();
}
