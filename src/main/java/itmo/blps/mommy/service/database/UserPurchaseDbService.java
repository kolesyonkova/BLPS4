package itmo.blps.mommy.service.database;

import itmo.blps.mommy.entity.UserPurchase;
import itmo.blps.mommy.repository.UserPurchaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserPurchaseDbService {
    private UserPurchaseRepository userPurchaseRepository;

    @Transactional(readOnly = true)
    public UserPurchase findByUserIdAndPurchaseId(Integer userId, Integer orderPurchaseId) {
        return userPurchaseRepository.findById_UserIdAndId_PurchaseId(userId, orderPurchaseId)
                .orElse(new UserPurchase(userId, orderPurchaseId));
    }

    @Transactional(readOnly = true)
    public List<UserPurchase> findAllByPurchaseId(Integer purchaseId) {
        return userPurchaseRepository.findAllById_PurchaseId(purchaseId);
    }

    @Transactional(readOnly = true)
    public Page<UserPurchase> findAllByUserId(Integer userId, Pageable pageable) {
        return userPurchaseRepository.findAllById_UserId(userId, pageable);
    }

    @Transactional
    public UserPurchase create(UserPurchase userPurchase) {
        return userPurchaseRepository.save(userPurchase);
    }

    @Transactional
    public void delete(UserPurchase userPurchase) {
        userPurchaseRepository.delete(userPurchase);
    }
}
