package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@ManyToMany (fetch = FetchType.EAGER)
    @Transient
    private List<Product> products = new ArrayList<>();

    @OneToOne (mappedBy = "cart")
    private Client client;

   public void addProductToCart(Product p){
       this.products.add(p);
    }
    public void removeProductFromCart(Product product) {
        this.products.remove(product);
    }
}
