package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.itschool.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
}
