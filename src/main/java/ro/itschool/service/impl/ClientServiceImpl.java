package ro.itschool.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.itschool.entity.Client;
import ro.itschool.entity.Role;
import ro.itschool.repository.ClientRepository;
import ro.itschool.service.ClientService;
import ro.itschool.service.email.EmailBodyService;
import ro.itschool.service.email.EmailSender;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final  ClientRepository clientRepository;

    private final  EmailBodyService emailBodyService;
    private final  EmailSender emailSender;

    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public Client findClientByUsername(String userName) {
        return clientRepository.findByUsernameIgnoreCase(userName);
    }


    public Client findClientByRandomToken(String randomToken) {
        return clientRepository.findByRandomToken(randomToken);
    }

    public boolean findClientByUserNameAndPassword(String userName, String password) {
        final Optional<Client> client = Optional.ofNullable(clientRepository.findByUsernameIgnoreCase(userName));
        return client.filter(myClient -> BCrypt.checkpw(password, myClient.getPassword())).isPresent();
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public void deleteById(long id) {
        clientRepository.deleteById(id);
    }

    public Client saveClient(Client receivedClient) {
        Client client = new Client(receivedClient);
        client.setPassword(new BCryptPasswordEncoder().encode(receivedClient.getPassword()));
        client.setRandomToken(UUID.randomUUID().toString());
        emailSender.sendEmail(client.getEmail(), "Activate your Account", emailBodyService.emailBody(client));
        return clientRepository.save(client);
    }

    public Client updateClient(Client receivedClient) {
        return clientRepository.save(receivedClient);
    }

//   public void updateClient(Client Client) {
//        List<GrantedAuthority> actualAuthorities = getClientAuthority(Client.getRoles());
//     Authentication newAuth = new ClientnamePasswordAuthenticationToken(Client.getClientName(), Client.getPassword(), actualAuthorities);
//       SecurityContextHolder.getContext().setAuthentication(newAuth);
//        ClientRepository.save(Client);
//    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }
    @Override
    public List<Client> searchClient(String keyword) {
        return clientRepository.searchClient(Objects.requireNonNullElse(keyword, ""));
    }

    private List<GrantedAuthority> getClientAuthority(Set<Role> clientRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Role role : clientRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new ArrayList<>(roles);
    }

}