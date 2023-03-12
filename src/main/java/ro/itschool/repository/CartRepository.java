package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
