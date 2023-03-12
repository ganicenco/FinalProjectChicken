package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.itschool.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findByName(String name);
    @Query(
            value = "SELECT * FROM product p WHERE p.name LIKE %:keyword% ",
            nativeQuery = true)
    List<Product> searchProductByName(@Param("keyword") String keyword);
}
