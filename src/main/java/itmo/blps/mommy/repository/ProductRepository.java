package itmo.blps.mommy.repository;

import itmo.blps.mommy.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from products p " +
            "where p.name like concat('%', :name, '%') " +
            "limit :limit offset :offset",
            nativeQuery = true)
    List<Product> findProducts(
            @Param("name") String name,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );
}
