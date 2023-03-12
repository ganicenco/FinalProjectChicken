package ro.itschool.entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ro.itschool.pojo.Address;

import java.io.Serial;
import java.util.*;

@Entity
@Setter
@Getter

public class Client implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id", nullable = false)
    private Long id;
    @Column(nullable = false, length = 30)
    private String name;
    @Column(length = 30)
    private String surname;
    private String phoneNumber;
    @Column(nullable = false, length = 30, unique = true)
    private String email;
    @Column(nullable = false) //de adaugat constraints pt format
    private String password;

    @Column(nullable = false, unique = true)
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired; //????
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    @Column
    private String randomToken;
    @Transient
    private String randomTokenEmail;
    @Embedded
    private Address address;
    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;
    @OneToOne(cascade = CascadeType.ALL)
    private Wishlist wishlist;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "clients_roles",
            joinColumns = @JoinColumn(name = "client_id", referencedColumnName = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
    private Set<Role> roles;
    @Transient
    private String passwordConfirm;
    @Transient
    private List<GrantedAuthority> authorities;
    public Client() {
        this.cart = new Cart();
        this.orders = new ArrayList<>();
        this.roles = new HashSet<>();
        this.wishlist = new Wishlist();
    }

    public Client(String name, String surname, String phoneNumber,Set<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.roles = roles;

    }

    public void addOrderToClient(Order order) {
        this.orders.add(order);
        order.setClient(this);
    }

    public Client(Client client) {
        this.enabled = client.isEnabled();
        this.roles = client.getRoles();
        this.username = client.getUsername();
        this.name = client.getName();
        this.surname = client.getSurname();
        this.address =client.getAddress();
        this.id = client.getId();
        this.accountNonExpired = client.isAccountNonExpired();
        this.accountNonLocked = client.isAccountNonLocked();
        this.credentialsNonExpired = client.isCredentialsNonExpired();
        this.email = client.getEmail();
        this.cart = client.getCart();
        this.wishlist = client.getWishlist();
        this.orders = client.getOrders();
        this.phoneNumber = client.getPhoneNumber();

    }

    public Client(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    @Override
   public String getUsername() {
       return username;
    }

    public Client(String name, String surname, String phoneNumber, String email, String password, String username, Address address) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.username = username;
        this.address = address;
    }
}



