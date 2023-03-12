package ro.itschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.itschool.entity.Client;
import ro.itschool.entity.Order;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUsernameIgnoreCase(String username);
    Client findByEmail(String username);
    Client findByRandomToken(String randomToken);

    @Query(
            value = "SELECT * FROM client u WHERE u.username LIKE %:keyword% OR u.name LIKE %:keyword% OR u.email LIKE %:keyword% " +
                    "OR u.client_id LIKE %:keyword%",
            nativeQuery = true)
    List<Client> searchClient(@Param("keyword") String keyword);
}

