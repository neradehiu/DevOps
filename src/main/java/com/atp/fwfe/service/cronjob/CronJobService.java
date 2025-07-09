package com.atp.fwfe.service.cronjob;

import com.atp.fwfe.model.work.WorkPosted;
import com.atp.fwfe.service.account.AccService;
import com.atp.fwfe.service.mailer.MailService;
import com.atp.fwfe.service.work.WorkPostedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CronJobService {

    private final WorkPostedService workPostedService;
    private final AccService accService;
    private final MailService mailService;

    @Scheduled(cron = "0 */2 * * * *")
    public void notifyNewJobs() {
        log.info("Đang gửi email công việc mới...");

        List<WorkPosted> newJobs = workPostedService.findUnnotified();

    }

}
