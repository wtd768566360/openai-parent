package com.alone.openai.web.controller;

import com.alone.openai.common.controller.BaseController;
import com.alone.openai.common.response.Restful;
import com.alone.openai.web.pojo.dto.email.EmailDto;
import com.alone.openai.web.pojo.entity.UserEntity;
import com.alone.openai.web.service.EmailService;
import com.alone.openai.web.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "邮件模块")
@RestController
@RequestMapping("email")
public class EmailController extends BaseController {

    private final EmailService emailService;

    private final UserService userService;

    public EmailController(EmailService emailService,
                           UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @Operation(summary = "注册账号")
    @PostMapping("/create/code")
    public Restful sendCreateCodeMail(@RequestBody EmailDto email) {
        UserEntity data = userService.findByEmail(email.getEmail());
        if (data != null) {
            return toError("此邮箱已经绑定其他账号!");
        }
        emailService.sendCreateCodeMail(email.getEmail());
        return toSuccess("注册验证码发送成功");
    }

    @Operation(summary = "重置密码")
    @PostMapping("/forget/password/code")
    public Restful sendForgetPasswordCodeMail(@RequestBody EmailDto email) {
        UserEntity data = userService.findByEmailAndAccount(email.getEmail(), email.getAccount());
        if (data == null) {
            return toError("账号与邮箱不匹配,请检查");
        }
        emailService.sendForgetPasswordCodeMail(email.getEmail());
        return toSuccess("验证码发送成功");
    }

    @Operation(summary = "找回账号")
    @PostMapping("find/account")
    public Restful findAccount(@RequestBody EmailDto emailDto) {
        UserEntity user = userService.findByEmail(emailDto.getEmail());
        if (user == null) {
            return toError("该邮箱未绑定账号");
        }
        String account = emailService.sendAccount(user.getEmail(), user.getAccount());
        if (account != null) {
            return toSuccess("账号已发送您的邮箱");
        }
        return toError("发送失败");
    }
}
