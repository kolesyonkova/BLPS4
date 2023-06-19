package itmo.blps.mommy.repository;

import itmo.blps.mommy.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {

    @Query(value = "select * from purchases pur " +
            "join products pro on pur.product_id = pro.id " +
            "where pro.name like concat('%', :name, '%') " +
            "and cast(pur.status as varchar) = :status and is_deleted = false " +
            "limit :limit offset :offset",
            nativeQuery = true)
    List<Purchase> findPurchases(
            @Param("name") String name,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset,
            @Param("status") String purchaseStatus
    );

    @Modifying
    @Transactional
    @Query(value = "update purchases set is_deleted = true where id = :id", nativeQuery = true)
    int deletePurchase(@Param("id") int id);

    Optional<Purchase> findById(Integer id);

    @Query(value = "select id from purchases where is_deleted = true limit :limit", nativeQuery = true)
    List<Integer> getDeleted(@Param("limit") int limit);
}
