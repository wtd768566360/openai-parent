package com.alone.openai.admin.api;

import com.alone.openai.admin.pojo.dto.flow.FlowAddDto;
import com.alone.openai.common.response.Restful;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 访问web端接口
 */
@FeignClient(name = "openai-web", url = "${openai.web.url}")
public interface WebClient {

    /**
     * 禁用用户
     */
    @PostMapping(value = "/admin/disable/{id}")
    Restful userDisable(@PathVariable(name = "id") String id);

    /**
     * 添加api-key
     */
    @PostMapping(value = "/admin/key/{key}")
    Restful keyAdd(@PathVariable(name = "key") String key);

    /**
     * 删除api-key
     */
    @DeleteMapping(value = "/admin/key/{key}")
    Restful keyDelete(@PathVariable(name = "key") String key);

    /**
     * 同步openai流量到数据库
     */
    @PostMapping(value = "/admin/flow/sync")
    Restful flowSync();

    /**
     * 向某个用户添加openai访问次数
     */
    @PostMapping(value = "/admin/flow/add")
    Restful flowAdd(@RequestBody FlowAddDto flowAddDto);


}
