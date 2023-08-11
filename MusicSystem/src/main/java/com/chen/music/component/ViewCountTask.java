package com.chen.music.component;

import com.chen.music.service.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ViewCountTask {

    @Autowired
    private ISettingsService settingsService;

    @Scheduled(cron = "0 0 0 * * ?")   //每天0点执行一次
    public void execute() {
        settingsService.updateHistoryViewCount();
    }
}
