package com.alone.openai.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alone.openai.admin.annotation.SessionValidator;
import com.alone.openai.admin.pojo.dto.user.AvailableDto;
import com.alone.openai.admin.pojo.dto.user.CreateDto;
import com.alone.openai.admin.pojo.dto.user.ModifyDto;
import com.alone.openai.admin.pojo.entity.UserEntity;
import com.alone.openai.admin.service.UserService;
import com.alone.openai.common.controller.BaseController;
import com.alone.openai.common.response.Restful;
import org.springframework.web.bind.annotation.*;

@SessionValidator
@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @GetMapping("query")
    public Restful query(@RequestParam int page,
                         @RequestParam int limit,
                         @RequestParam(required = false) String type,
                         @RequestParam(required = false) String name,
                         @RequestParam(required = false) String phone,
                         @RequestParam(required = false) String email) {
        return userService.query(page, limit, type, name, phone, email);
    }

    /**
     * 添加用户
     *
     * @return
     */
    @PostMapping("create")
    public Restful create(@RequestBody CreateDto createDto) {
        UserEntity user = new UserEntity();
        BeanUtil.copyProperties(createDto, user);
        int result = userService.create(user);
        return result == 1 ? toSuccess("创建成功") : toError("创建失败");
    }

    /**
     * 修改用户
     *
     * @return
     */
    @PutMapping("modify")
    public Restful modify(@RequestBody ModifyDto modifyDto) {
        UserEntity user = new UserEntity();
        BeanUtil.copyProperties(modifyDto, user);
        int result = userService.modify(user);

        return result == 1 ? toSuccess("修改成功") : toError("修改失败");
    }

    /**
     * 冻结/启用/删除用户
     *
     * @return
     */
    @PatchMapping("available")
    public Restful available(@RequestBody AvailableDto availableDto) {
        int result = userService.available(availableDto.getId(), availableDto.getState());
        return result > 0 ? toSuccess("操作成功") : toError("操作失败");
    }

}
