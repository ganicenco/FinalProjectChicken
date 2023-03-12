package ro.itschool.mapper;

import org.springframework.stereotype.Component;
import ro.itschool.controller.model.ProductDTO;
import ro.itschool.entity.Product;

@Component
public class ProductMapper {
    public ProductDTO fromEntity(Product product){
return new ProductDTO(product.getId(), product.getName(), product.getQuantity(), product.getPrice(), product.getAge(), product.getGender());
    }
    public Product toEntity(ProductDTO productDTO){
return new Product(productDTO.getName(), productDTO.getQuantity(), productDTO.getPrice(), productDTO.getAge(), productDTO.getGender());
    }
}
