package itmo.blps.mommy.controller;

import itmo.blps.mommy.dto.ProductDTO;
import itmo.blps.mommy.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<?> getProducts(
            @RequestParam(value = "name", required = false, defaultValue = "") String name,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "per_page", required = false, defaultValue = "20") Integer perPage
    ) {
        return ResponseEntity.ok(productService.suggestProducts(name, page, perPage));
    }

    @PostMapping("/admin/product/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProduct(@PathVariable int id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PutMapping("/admin/product/update")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.updateProduct(productDTO));

    }

}
