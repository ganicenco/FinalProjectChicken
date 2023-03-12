package ro.itschool.pojo;

import jakarta.persistence.*;
import lombok.*;
import ro.itschool.entity.Product;

@Setter
@Getter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor

public class Description {
    private String breed;
    private String purposeForBreeding;
    private String color;
    private Integer competitionScore;
}
