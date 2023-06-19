package itmo.blps.mommy.mapper;

import itmo.blps.mommy.dto.ProductDTO;
import itmo.blps.mommy.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getWeight(),
                product.getConsumerInfo()
        );
    }

    public Product fromDto(ProductDTO productDTO) {
        return new Product(
                productDTO.getName(),
                productDTO.getWeight(),
                productDTO.getConsumerInfo()
        );
    }
}
