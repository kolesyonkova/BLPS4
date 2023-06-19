package itmo.blps.mommy.repository;

import itmo.blps.mommy.entity.UserPurchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPurchaseRepository extends JpaRepository<UserPurchase, Integer> {
    Optional<UserPurchase> findById_UserIdAndId_PurchaseId(Integer userId, Integer purchaseId);

    @Transactional
    Optional<UserPurchase> deleteById_UserIdAndId_PurchaseId(Integer userId, Integer purchaseId);

    Page<UserPurchase> findAllById_UserId(Integer userId, Pageable pageable);

    List<UserPurchase> findAllById_PurchaseId(Integer purchaseId);
}
