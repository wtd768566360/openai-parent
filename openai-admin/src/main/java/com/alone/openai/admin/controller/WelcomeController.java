package com.alone.openai.admin.controller;

import com.alone.openai.admin.service.WelcomeService;
import com.alone.openai.common.controller.BaseController;
import com.alone.openai.common.response.Restful;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("welcome")
public class WelcomeController extends BaseController {

    private final WelcomeService welcomeService;

    public WelcomeController(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }

    /**
     * 首页的统计
     */
    @GetMapping("statistics")
    public Restful statistics() {
        Map<String, Long> result = welcomeService.statistics();
        if (result == null) {
            return toError("统计失败");
        }
        return toSuccess("统计成功", result);
    }

    /**
     * 首页报表折线图
     */
    @GetMapping("echarts/records")
    public Restful echartsRecords() {
        Map<String, List<Object>> result = welcomeService.echartsRecords();
        return toSuccess("统计成功", result);
    }
}
