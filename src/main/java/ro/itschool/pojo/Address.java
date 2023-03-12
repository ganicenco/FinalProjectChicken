package ro.itschool.pojo;

import jakarta.persistence.*;
import lombok.*;
import ro.itschool.entity.Client;

@Getter
@Setter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    private String country;
    private String city;
    private String streetName;
    private String streetNo;
    private String buildingNo;

}