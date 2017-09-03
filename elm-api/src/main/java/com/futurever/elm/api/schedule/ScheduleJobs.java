package com.futurever.elm.api.schedule;

import com.futurever.elm.api.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by wxcsdb88 on 2017/8/13 17:32.
 */
@Configuration
@EnableScheduling
public class ScheduleJobs {
    public final static long SECOND = 1 * 1000;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    //    固定等待时间 @Scheduled(fixedDelay = 时间间隔 )
    @Scheduled(fixedDelay = SECOND * 60)
    public void fixedDelayJob() throws InterruptedException {
        log.debug("[FixedDelayJob Execute]" + DateUtils.currentDateTime());
    }

    //    固定间隔时间 @Scheduled(fixedRate = 时间间隔 )
    @Scheduled(fixedRate = SECOND * 60)
    public void fixedRateJob() {
        log.debug("[FixedRateJob Execute]" + DateUtils.currentDateTime());
    }

    // Corn表达式 @Scheduled(cron = Corn表达式)
    @Scheduled(cron = "0/30 * * * * ?")
    public void cronJob() {
        log.debug("[CronJob Execute]" + DateUtils.currentDateTime());
    }
}
