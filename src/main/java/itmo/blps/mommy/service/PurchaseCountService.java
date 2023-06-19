package itmo.blps.mommy.service;

import itmo.blps.mommy.config.RabbitConfig;
import itmo.blps.mommy.dto.EmailDto;
import itmo.blps.mommy.entity.Purchase;
import itmo.blps.mommy.entity.User;
import itmo.blps.mommy.entity.UserPurchase;
import itmo.blps.mommy.service.database.PurchaseDbService;
import itmo.blps.mommy.service.database.UserDbService;
import itmo.blps.mommy.service.database.UserPurchaseDbService;
import lombok.AllArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static itmo.blps.mommy.entity.PurchaseStatus.WAIT_PAYMENT;

@Service
@AllArgsConstructor
public class PurchaseCountService {
    private final UserPurchaseDbService userPurchaseDbService;
    private final PurchaseDbService purchaseDbService;
    private final UserDbService userDbService;
    //    private final EmailService emailService;
    private final JmsTemplate jmsTemplate;
    private final RabbitConfig rabbitConfig;


    public void countPurchasedProducts(UserPurchase db) throws Exception {
        Purchase purchase = purchaseDbService.findById(db.getId().getPurchaseId());

        purchase.setCurCount(purchase.getCurCount() + db.getProductsCount());

        if (purchase.getCurCount() >= purchase.getMinCount()) {
            List<UserPurchase> userList = userPurchaseDbService.findAllByPurchaseId(db.getId().getPurchaseId());
            for (UserPurchase u : userList) {
                if (!userDbService.existsById(u.getId().getUserId())) continue;
                User user = userDbService.getById(u.getId().getUserId());
                purchase.setPurchaseStatus(WAIT_PAYMENT);
                String message = generateMessage(purchase);
//                emailService.send(user.getEmail(), "Выкуп товара", message);
                jmsTemplate.convertAndSend(rabbitConfig.getQueueName(), new EmailDto().setEmailTo(user.getEmail())
                        .setSubject("Выкуп товара")
                        .setMessage(message));
            }
        }
        purchaseDbService.create(purchase);
    }

    private String generateMessage(Purchase purchase) {
        return "Пора начинать выкупать товар " + purchase.getProduct().getName() + "!";
    }
}
