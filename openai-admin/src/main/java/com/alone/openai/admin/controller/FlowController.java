package com.alone.openai.admin.controller;

import com.alone.openai.admin.annotation.SessionValidator;
import com.alone.openai.admin.pojo.dto.flow.FlowAddDto;
import com.alone.openai.admin.service.FlowService;
import com.alone.openai.common.controller.BaseController;
import com.alone.openai.common.response.Restful;
import org.springframework.web.bind.annotation.*;

@SessionValidator
@RestController
@RequestMapping("flow")
public class FlowController extends BaseController {

    private final FlowService flowService;

    public FlowController(FlowService flowService) {
        this.flowService = flowService;
    }

    /**
     * 查询所有用户
     */
    @GetMapping("query")
    public Restful query(@RequestParam int page,
                         @RequestParam int limit,
                         @RequestParam(required = false) String account,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) String email) {
        return flowService.query(page, limit, account, name, email);
    }

    /**
     * 向指定用户添加访问量
     */
    @PostMapping("add")
    public Restful totalAdd(@RequestBody FlowAddDto flowAddDto) {
        int result = flowService.totalAdd(flowAddDto.getUserId(), flowAddDto.getNumber());
        return result > 0 ? toSuccess("访问量添加成功") : toError("访问量添加失败");
    }

    @PostMapping("sync")
    public Restful flowSync() {
        int result = flowService.syncCacheToDatabase();
        return result > 0 ? toSuccess("同步成功") : toError("同步失败");
    }

}
