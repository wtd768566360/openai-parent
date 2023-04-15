package com.alone.openai.web.controller;

import com.alone.openai.common.controller.BaseController;
import com.alone.openai.common.response.Restful;
import com.alone.openai.web.pojo.dto.admin.FlowAddDto;
import com.alone.openai.web.service.FlowService;
import com.alone.openai.web.service.UserJwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "针对用户管理的,此接口被admin调用")
@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {

    private final UserJwtService userJwtService;

    private final FlowService flowService;

    private final List<String> databaseApiKeyList;

    public AdminController(UserJwtService userJwtService,
                           @Qualifier("database-api-keys") List<String> databaseApiKeyList,
                           FlowService flowService) {
        this.userJwtService = userJwtService;
        this.databaseApiKeyList = databaseApiKeyList;
        this.flowService = flowService;
    }

    @Operation(summary = "服务端调用这个接口,通过这个接口删掉JWT的缓存,直接踢出系统")
    @PostMapping("disable/{id}")
    public Restful disable(@PathVariable String id) {
        if (userJwtService.deleteByUserId(id) > 0) {
            return toError("禁止用户失败");
        }
        return toSuccess("禁止用户成功,已经成功删除其所有jwt存储");
    }

    @Operation(summary = "通过这个接口,可以添加正在运行时的API-KEY")
    @PostMapping("key/{key}")
    public Restful keyAdd(@PathVariable String key) {
        if (databaseApiKeyList.add(key)) {
            return toSuccess("API-KEY动态添加成功");
        }
        return toError("API-KEY动态添加失败");
    }

    @Operation(summary = "通过这个接口,可以删除正在缓存中运行时的API-KEY")
    @DeleteMapping("key/{key}")
    public Restful keyDelete(@PathVariable String key) {
        if (databaseApiKeyList.remove(key)) {
            return toSuccess("API-KEY动态添加成功");
        }
        return toError("API-KEY动态添加失败");
    }

    @Operation(summary = "通过这个接口,可以手动同步所有用户的openai访问次数到数据库")
    @PostMapping("flow/sync")
    public Restful flowSync() {
        flowService.syncCacheToDatabase();
        return toSuccess("同步完成");
    }

    @Operation(summary = "通过这个接口,可以手动向某个用户添加openai访问次数")
    @PostMapping("flow/add")
    public Restful flowAdd(@RequestBody FlowAddDto flowAddDto) {
        flowService.add(flowAddDto.getUserId(), flowAddDto.getNumber());
        return toSuccess("添加完成");
    }
}
