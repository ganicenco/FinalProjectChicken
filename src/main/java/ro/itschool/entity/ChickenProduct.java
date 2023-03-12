package ro.itschool.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.itschool.entity.Product;
import ro.itschool.pojo.Description;
@Setter
@Getter
@Entity
@NoArgsConstructor
public class ChickenProduct extends Product {
    @Embedded
    private Description description;

    public ChickenProduct(String name, Integer quantity, Integer price, Integer age, String gender, Description description) {
        super(name, quantity, price, age, gender);
        this.description = description;
    }
}
