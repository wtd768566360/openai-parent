package com.alone.openai.admin.task;

import com.alone.openai.admin.service.FlowService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FlowTask {

    private final FlowService flowService;

    public FlowTask(FlowService flowService) {
        this.flowService = flowService;
    }

    /**
     * 每1小时执行一次同步
     */
    @Scheduled(cron = "0 0 */1 * * ?")
    public void sync() {
        flowService.syncCacheToDatabase();
    }
}
