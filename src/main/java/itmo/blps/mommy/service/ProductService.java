package itmo.blps.mommy.service;

import itmo.blps.mommy.dto.ProductDTO;
import itmo.blps.mommy.entity.Product;
import itmo.blps.mommy.mapper.ProductMapper;
import itmo.blps.mommy.service.database.ProductDbService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {
    private ProductDbService productDbService;
    private ProductMapper productMapper;


    public ProductDTO getProduct(Integer productId) {
        return productMapper.toDto(productDbService.findProduct(productId));
    }

    public List<ProductDTO> suggestProducts(String name, int page, int perPage) {
        List<Product> products = productDbService.findProducts(name.strip(), perPage, (page - 1) * perPage);
        return products
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productDbService.create(productMapper.fromDto(productDTO));
        return productMapper.toDto(product);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        if (productDTO.getId() == null) {
            throw new RuntimeException("Product id should not be null");
        }
        Product product = productDbService.findProduct(productDTO.getId());
        product.setName(productDTO.getName());
        product.setWeight(productDTO.getWeight());
        product.setConsumerInfo(productDTO.getConsumerInfo());
        return productMapper.toDto(productDbService.create(product));
    }
}
