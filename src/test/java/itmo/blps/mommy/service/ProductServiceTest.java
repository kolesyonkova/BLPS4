package itmo.blps.mommy.service;

import itmo.blps.mommy.dto.ProductDTO;
import itmo.blps.mommy.entity.Product;
import itmo.blps.mommy.mapper.ProductMapper;
import itmo.blps.mommy.service.database.ProductDbService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.util.Assert;

public class ProductServiceTest {

    private static ProductDbService productDbService;
    private static ProductMapper productMapper;
    private static ProductService productService;

    @BeforeAll
    public static void init() {
        productDbService = Mockito.mock(ProductDbService.class);
        productMapper = new ProductMapper();
        productService = new ProductService(productDbService, productMapper);
    }


    @Test
    public void productCanBeUpdated() {
        Product product = new Product(1, "name", null, null);
        ProductDTO productDTO = new ProductDTO(1, "updatedName", null, null);
        Mockito.when(productDbService.findProduct(Mockito.eq(productDTO.getId()))).thenReturn(product);
        Mockito.when(productDbService.create(Mockito.eq(productMapper.fromDto(productDTO))))
                .thenReturn(productMapper.fromDto(productDTO));
        Assert.equals(productDTO.getName(), productService.updateProduct(productDTO).getName());
    }
}
