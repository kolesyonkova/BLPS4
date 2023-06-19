package itmo.blps.mommy.service.database;

import itmo.blps.mommy.entity.Product;
import itmo.blps.mommy.exception.EntityNotFoundException;
import itmo.blps.mommy.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductDbService {
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public Product findProduct(Integer productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Unknown product: " + productId));
    }

    @Transactional(readOnly = true)
    public List<Product> findProducts(String name, int page, int perPage) {
        return productRepository.findProducts(name.strip(), perPage, (page - 1) * perPage);
    }

    @Transactional
    public Product create(Product product) {
        return productRepository.save(product);

    }
}
