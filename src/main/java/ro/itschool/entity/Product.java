package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer quantity;
    private Integer price;
    private Integer age;
    private String gender;


    public Product(Integer id, String name, Integer quantity, Integer price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;

    }

    public Product(String name, Integer quantity, Integer price, Integer age, String gender) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.age = age;
        this.gender = gender;
    }
}


