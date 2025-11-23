package com.rodem.shop.settlement.batch;

import com.rodem.shop.seller.domain.Seller;
import com.rodem.shop.seller.domain.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class SellerSettlementScheduler {

    private static final Logger log = LoggerFactory.getLogger(SellerSettlementScheduler.class);

    private final SellerRepository sellerRepository;

    private final JobLauncher jobLauncher;
    private final Job sellerSettlementJob;
    private final ThreadPoolTaskExecutor settlementTaskExecutor;
    @Value("${settlement.async.enabled}")
    private boolean settlementAsyncEnabled;


    @Scheduled(cron = "${spring.task.scheduling.cron.settlement}")
    public void runMidnightSettlements() {
        Pageable pageable = Pageable.ofSize(100);
        Page<UUID> page;
        do {
            page = sellerRepository.findAll(pageable).map(Seller::getId);
            List<UUID> sellerIds = page.getContent();
            if (sellerIds.isEmpty()) {
                break;
            }
            log.info("Settlement batch chunk for {} sellers (page {}/{})", sellerIds.size(), page.getNumber() + 1, page.getTotalPages());
            sellerIds.forEach(this::runJobForSeller);
            pageable = page.hasNext() ? page.nextPageable() : Pageable.unpaged();
        } while (page.hasNext());
    }

    private void runJobForSeller(UUID sellerId) {
        try {
            Runnable executeJob = () -> {
                try {
                    JobParameters parameters = new JobParametersBuilder()
                            .addLong("timestamp", System.currentTimeMillis())
                            .addString("sellerId", sellerId.toString())
                            .toJobParameters();
                    jobLauncher.run(sellerSettlementJob, parameters);
                    log.info("Settlement job triggered for seller {}", sellerId);
                } catch (Exception ex) {
                    log.error("Failed to run settlement job for seller {}", sellerId, ex);
                }
            };

            if (settlementAsyncEnabled) {
                settlementTaskExecutor.execute(executeJob);
            } else {
                executeJob.run();
            }
        } catch (Exception ex) {
            log.error("Failed to run settlement job for seller {}", sellerId, ex);
        }
    }

}
