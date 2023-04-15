package com.alone.openai.web.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alone.openai.common.controller.BaseController;
import com.alone.openai.common.response.Restful;
import com.alone.openai.common.constant.Constant;
import com.alone.openai.web.pojo.dto.system.*;
import com.alone.openai.web.pojo.entity.FlowEntity;
import com.alone.openai.web.pojo.entity.UserEntity;
import com.alone.openai.web.pojo.vo.system.LoginVo;
import com.alone.openai.web.service.EmailService;
import com.alone.openai.web.service.FlowService;
import com.alone.openai.web.service.UserJwtService;
import com.alone.openai.web.service.UserService;
import com.google.common.cache.CacheLoader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "系统模块")
@RestController
@RequestMapping("system")
public class SystemController extends BaseController {

    private final UserService userService;

    private final UserJwtService userJwtService;

    private final EmailService emailService;

    private final FlowService flowService;

    public SystemController(UserService userService,
                            UserJwtService userJwtService,
                            EmailService emailService,
                            FlowService flowService) {
        this.userService = userService;
        this.userJwtService = userJwtService;
        this.emailService = emailService;
        this.flowService = flowService;
    }

    @Operation(summary = "登录")
    @PostMapping("login")
    public Restful login(@RequestBody LoginDto loginDto) {
        UserEntity user = userService.login(loginDto.getAccount(), loginDto.getPassword());
        if (user != null) {
            if (!UserEntity.STATE_AVAILABLE.equals(user.getState())) {
                return toError("账号已被禁用,请联系管理员");
            }
            String token = userJwtService.create(user.getId(), user.getName(), user.getUserType().getCode());
            String value = userJwtService.put(token, user.getId());
            if (!user.getId().equals(value)) {
                return toError("登录失败,验证失败");
            }
            LoginVo loginVo = new LoginVo();
            loginVo.setEmail(user.getEmail());
            loginVo.setAccount(user.getAccount());
            loginVo.setName(user.getName());
            loginVo.setToken(token);
            return toSuccess("登录成功", loginVo);
        }
        return toError("登录失败,请检查账号密码");
    }

    @Operation(summary = "注销")
    @PostMapping("logout")
    public Restful logout(ServerHttpRequest serverHttpRequest) {
        String token = serverHttpRequest.getHeaders().getFirst(Constant.JWT_TOKEN);
        if (userJwtService.remove(token) > 0) {
            return toSuccess("注销成功");
        }
        return toError("注销失败");
    }

    @Operation(summary = "修改密码")
    @PatchMapping("password")
    public Restful password(ServerHttpRequest serverHttpRequest, @RequestBody PasswordDto passwordDto) {
        String token = serverHttpRequest.getHeaders().getFirst(Constant.JWT_TOKEN);
        UserEntity user = userService.findById(userJwtService.get(token));
        int result = userService.password(user, passwordDto.getOldPassword(), passwordDto.getNewPassword());
        if (result == 1) {
            return toSuccess("密码修改成功,点击确认按钮将退出登录");
        }
        if (result == -1) {
            return toError("修改失败,原密码错误");
        }
        return toError("密码修改失败");
    }

    @Operation(summary = "修改个人信息")
    @PutMapping("modify")
    public Restful modify(ServerHttpRequest serverHttpRequest, @RequestBody ModifyDto modifyDto) {
        String token = serverHttpRequest.getHeaders().getFirst(Constant.JWT_TOKEN);
        modifyDto.setId(userJwtService.get(token));
        int result = userService.modify(modifyDto.getId(), modifyDto.getName(), modifyDto.getPhone(), modifyDto.getEmail());
        return result == 1 ? toSuccess("修改成功") : toError("修改失败");
    }


    @Operation(summary = "重置密码")
    @PostMapping("forget/password")
    public Restful forgetPassword(@RequestBody ForgetPasswordDto passwordDto) {
        try {
            String code = emailService.getCacheCode(passwordDto.getEmail());
            if (passwordDto.getCode().equals(code)) {
                userService.forgetPassword(passwordDto.getEmail(), passwordDto.getAccount(), passwordDto.getPassword());
                return toSuccess("重置密码成功");
            }
        } catch (CacheLoader.InvalidCacheLoadException exception) {
            return toError("验证码已经失效");
        }
        return toError("验证码不正确");
    }

    @Operation(summary = "注册账号")
    @PostMapping("registry")
    public Restful registry(@RequestBody RegistryDto registryDto) {
        //查询用户名是否已经注册账号
        UserEntity dbUser = userService.findByAccount(registryDto.getAccount());
        if (!Objects.isNull(dbUser)) {
            return toError("该登录名已被使用,请更换其他用户名！");
        }
        String code = emailService.getCacheCode(registryDto.getEmail());
        if (registryDto.getCode().equals(code)) {
            UserEntity user = new UserEntity();
            BeanUtil.copyProperties(registryDto, user);
            UserEntity userEntity = userService.create(user);
            //注册账号,设置其访问次数
            FlowEntity flowEntity = new FlowEntity();
            flowEntity.setUser(userEntity);
            flowService.create(flowEntity);
            return toSuccess("创建成功,开启您的AI之旅");
        }
        return toError("验证码验证失败");
    }

}
