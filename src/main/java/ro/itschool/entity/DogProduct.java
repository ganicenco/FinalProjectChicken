package ro.itschool.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DogProduct extends Product {
    private String breedType;
    private Boolean neuter;
    private Boolean pedigree;
    private Integer microchipNo;

    public DogProduct(String name, Integer quantity, Integer price, Integer age, String gender, String breedType, Boolean neuter, Boolean pedigree, Integer microchipNo) {
        super(name, quantity, price, age, gender);
        this.breedType = breedType;
        this.neuter = neuter;
        this.pedigree = pedigree;
        this.microchipNo = microchipNo;
    }
}
