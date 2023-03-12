package ro.itschool.controller.model;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.itschool.entity.Product;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @CsvBindByPosition(position = 0)
    private Integer id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private Integer quantity;
    @CsvBindByPosition(position = 3)
    private Integer price;
    @CsvBindByPosition(position = 4)
    private Integer age;
    @CsvBindByPosition(position = 5)
    private String gender;

    public Product toEntity() {
        Product myProduct = new Product();
        myProduct.setId(this.id);
        myProduct.setName(this.name);
        myProduct.setQuantity(this.quantity);
        myProduct.setPrice(this.price);
        myProduct.setAge(this.age);
        myProduct.setGender(this.gender);

        return myProduct;
    }
}
