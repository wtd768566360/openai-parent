package com.alone.openai.admin.controller;

import com.alone.openai.admin.annotation.SessionValidator;
import com.alone.openai.admin.constant.Constant;
import com.alone.openai.admin.pojo.dto.key.CreateDto;
import com.alone.openai.admin.pojo.dto.user.AvailableDto;
import com.alone.openai.admin.pojo.entity.AdminEntity;
import com.alone.openai.admin.service.ApiKeyService;
import com.alone.openai.common.controller.BaseController;
import com.alone.openai.common.response.Restful;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@SessionValidator
@RestController
@RequestMapping("api/key")
public class ApiKeyController extends BaseController {

    public final ApiKeyService apiKeyService;

    private final HttpServletRequest httpServletRequest;

    public ApiKeyController(ApiKeyService apiKeyService, HttpServletRequest httpServletRequest) {
        this.apiKeyService = apiKeyService;
        this.httpServletRequest = httpServletRequest;
    }

    @GetMapping("query")
    public Restful query(@RequestParam int page,
                         @RequestParam int limit,
                         @RequestParam List<String> state,
                         @RequestParam(required = false) String origin) {
        return apiKeyService.query(page, limit, state, origin);
    }

    /**
     * 添加APIKEY
     *
     * @return
     */
    @PostMapping("create")
    public Restful create(@RequestBody CreateDto createDto) {
        HttpSession httpSession = httpServletRequest.getSession();
        AdminEntity user = (AdminEntity) httpSession.getAttribute(Constant.SESSION_NAME);
        int result = apiKeyService.create(user, createDto.getApiKey(), createDto.getOrigin());
        return result > 0 ? toSuccess("API-KEY添加成功") : toError("API-KEY添加成功");
    }


    /**
     * 设置key的状态(可用/不可用)
     *
     * @return
     */
    @PatchMapping("available")
    public Restful available(@RequestBody AvailableDto availableDto) {
        int result = apiKeyService.available(availableDto.getId(), availableDto.getState());
        return result > 0 ? toSuccess("状态修改成功") : toError("状态修改失败");
    }

    /**
     * 删除
     *
     * @return
     */
    @DeleteMapping("{id}")
    public Restful delete(@PathVariable String id) {
        return apiKeyService.delete(id) > 0 ? toSuccess("删除成功") : toError("删除失败");
    }

}
