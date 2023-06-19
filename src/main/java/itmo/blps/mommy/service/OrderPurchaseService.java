package itmo.blps.mommy.service;

import itmo.blps.mommy.dto.OrderPurchaseDto;
import itmo.blps.mommy.entity.Purchase;
import itmo.blps.mommy.entity.User;
import itmo.blps.mommy.entity.UserPurchase;
import itmo.blps.mommy.exception.EntityNotFoundException;
import itmo.blps.mommy.mapper.UserPurchaseMapper;
import itmo.blps.mommy.repository.PurchaseRepository;
import itmo.blps.mommy.service.database.UserPurchaseDbService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class OrderPurchaseService {
    private UserPurchaseDbService userPurchaseDbService;
    private PurchaseRepository purchaseRepository;
    private UserPurchaseMapper userPurchaseMapper;
    private PurchaseCountService purchaseCountService;
    private UserService userService;


    public OrderPurchaseDto addOrderPurchase(OrderPurchaseDto orderPurchaseDto) throws Exception {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        purchaseRepository.findById(orderPurchaseDto.getPurchaseId())
                .orElseThrow(() -> new EntityNotFoundException("Сущность выкупа не найдена"));
        User user = userService.findByEmail(userName);
        UserPurchase userPurchase = userPurchaseDbService.findByUserIdAndPurchaseId(user.getId(), orderPurchaseDto.getPurchaseId());
        userPurchase.setDateCreated(Instant.now());
        int countOfPurchase = 0;
        if (userPurchase.getProductsCount() != null) {
            countOfPurchase += userPurchase.getProductsCount();
        }
        userPurchase.setProductsCount(orderPurchaseDto.getCountOfProducts() + countOfPurchase);
        UserPurchase db = userPurchaseDbService.create(userPurchase);
        purchaseCountService.countPurchasedProducts(db);
        return userPurchaseMapper.toDto(db);
    }

    public OrderPurchaseDto getOrderPurchase(Integer purchaseId) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);
        return userPurchaseMapper.toDto(userPurchaseDbService.findByUserIdAndPurchaseId(user.getId(), purchaseId));
    }

    public void deleteOrderPurchase(Integer purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new EntityNotFoundException("Сущность выкупа не найдена"));
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);
        UserPurchase userPurchase = userPurchaseDbService.findByUserIdAndPurchaseId(user.getId(), purchaseId);
        userPurchaseDbService.delete(userPurchase);
        purchase.setCurCount(purchase.getCurCount() - userPurchase.getProductsCount());
        purchaseRepository.save(purchase);
    }

    public Page<UserPurchase> getPagedOrderPurchase(int page, int pageSize) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);
        return userPurchaseDbService.findAllByUserId(user.getId(), PageRequest.of(page, pageSize));
    }
}
