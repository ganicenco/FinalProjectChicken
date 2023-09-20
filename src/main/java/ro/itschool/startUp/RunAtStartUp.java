package ro.itschool.startUp;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ro.itschool.entity.*;
import ro.itschool.pojo.Address;
import ro.itschool.pojo.Description;
import ro.itschool.repository.ClientRepository;
import ro.itschool.repository.ProductRepository;
import ro.itschool.repository.RoleRepository;
import ro.itschool.repository.ShoppingCartProductQuantityRepository;
import ro.itschool.service.impl.CartServiceImpl;
import ro.itschool.service.ClientService;
import ro.itschool.util.Constants;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ro.itschool.util.Constants.ROLE_ADMIN;
import static ro.itschool.util.Constants.ROLE_USER;

@Component
@RequiredArgsConstructor
public class RunAtStartUp {

    private ClientRepository clientRepository;

    private final ClientService clientService;

    private final RoleRepository roleRepository;

    private final ProductRepository productRepository;

    private final CartServiceImpl cartService;
    private final ShoppingCartProductQuantityRepository quantityRepository;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {


        roleRepository.save(new Role(ROLE_USER));
        roleRepository.save(new Role(ROLE_ADMIN));

        saveClient();
        saveAdminUser();
        saveClientToDelete();
        startProducts();

    }

    private void saveAdminUser() {
        Client client = new Client();
        client.setUsername("admin");
        client.setPassword("admin");
        client.setAddress(new Address("US","NY", "5th Avenue", "34", "2" ));
        client.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ROLE_ADMIN));
        client.setRoles(roles);
        client.setEnabled(true);
        client.setAccountNonExpired(true);
        client.setAccountNonLocked(true);
        client.setCredentialsNonExpired(true);
        client.setEmail("admin1@gmail.com");
        client.setPhoneNumber("0034986521343");
        client.setName("John");
        client.setSurname("Admin");
        client.setPasswordConfirm("admin");
        client.setRandomTokenEmail("randomToken");

        clientService.saveClient(client);
    }

    public void saveClient() {
        Client client = new Client();
        client.setUsername("client");
        client.setPassword("client");
        client.setName("John");
        client.setSurname("Smith");
        client.setEmail("john.smith@gmail.com");
        client.setPhoneNumber("+40742346332");
        client.setAddress(new Address("Romania", "Cluj", "Sf. Ion", "39", "4"));
        client.setRandomToken("randomToken");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ROLE_USER));
        client.setRoles(roles);
        client.setEnabled(true);
        client.setAccountNonExpired(true);
        client.setAccountNonLocked(true);
        client.setCredentialsNonExpired(true);
        client.setPasswordConfirm("client");
        client.setRandomTokenEmail("randomToken");

        Client client1 = clientService.saveClient(client);
        Cart cart = client1.getCart();
        cart.setClient(client1);
        cart.setProducts(getProducts());

        clientService.updateClient(client1);
    }

    private List<Product> getProducts() {
        return productRepository.saveAll(List.of(
                new ChickenProduct("Brahma Silver Laced", 5, 150, 2, "rooster", new Description("Brahma", "eggs", "silver laced", 92)),
                new ChickenProduct("Cochinchina Bantam", 6, 50, 2, "hen", new Description("Cochinchina", "eggs", "mottled", 93)),
                new ChickenProduct("Silkie", 20, 40, 3, "rooster", new Description("Silkie", "ornament", "white", 94)),
                new ChickenProduct("Brahma Gold", 4, 200, 1, "hen", new Description("Brahma", "eggs", "gold", 92)),
                new ChickenProduct("Brahma Black", 10, 100, 2, "hen", new Description("Brahma", "eggs", "black", 92)),
                new ChickenProduct("White Face Spanish", 8, 250, 2, "hen", new Description("White Face Spanish", "ornament", "standard", 96))
        ));
    }


    private List<Product> startProducts() {
        return (productRepository.saveAll(List.of(
                new DogProduct("Big Boy", 4, 800, 1, "male", "Malamute", true, true, 23459875),
                new ChickenProduct("Blue Orpinghton", 3, 140, 2, "hen", new Description("Orphinghton", "eggs", "blue", 93)),
                new ChickenProduct("White Orpington", 5, 140, 3, "rooster", new Description("Silkie", "ornament", "white", 94)))));

    }

    public void saveClientToDelete() {
        Client client = new Client();
        client.setUsername("clientToDelete");
        client.setPassword("client");
        client.setRandomToken("randomToken");
        client.setPhoneNumber("0040744265432");
        final Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(ROLE_USER));
        client.setRoles(roles);
        client.setEnabled(true);
        client.setAccountNonExpired(true);
        client.setAccountNonLocked(true);
        client.setCredentialsNonExpired(true);
        client.setEmail("userDeleteMe@gmail.com");
        client.setName("Ion");
        client.setSurname("DeleteMe User");
        client.setPasswordConfirm("client");
        client.setRandomTokenEmail("randomToken");
        client.setAddress(new Address("Romania", "Iasi", "Independentei", "23", "2"));

        clientService.saveClient(client);

    }
}
