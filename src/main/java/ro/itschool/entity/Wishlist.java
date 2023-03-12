package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.*;
import ro.itschool.controller.model.ProductDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Product> products = new ArrayList<>();

    @OneToOne (mappedBy = "wishlist")
    private Client client;

    public void addProductToWishlist(Product pr) {
        this.products.add(pr);
    }
}
