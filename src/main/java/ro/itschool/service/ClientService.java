package ro.itschool.service;

import org.springframework.stereotype.Service;
import ro.itschool.entity.Client;

import java.util.List;
import java.util.Optional;

@Service
public interface ClientService {

    Client findClientByEmail(String email);

    Client findClientByUsername(String username);

    Client findClientByRandomToken(String randomToken);

    boolean findClientByUserNameAndPassword(String username, String password);

    List<Client> findAll();

    void deleteById(long id);

    Client saveClient(Client c);

    Client updateClient(Client client);

    Optional<Client> findById(Long id);

    List<Client> searchClient(String keyword);
}