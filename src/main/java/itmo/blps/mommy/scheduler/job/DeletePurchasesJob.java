package itmo.blps.mommy.scheduler.job;

import itmo.blps.mommy.scheduler.service.DeletePurchasesService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeletePurchasesJob implements Job {

    @Autowired
    private DeletePurchasesService deletePurchasesService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        deletePurchasesService.deletePurchases();
        log.info("Purchases successfully deleted");
    }
}
