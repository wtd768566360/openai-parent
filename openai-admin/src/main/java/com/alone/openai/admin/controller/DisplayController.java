package com.alone.openai.admin.controller;

import com.alone.openai.admin.annotation.SessionValidator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面转发控制器
 */
@SessionValidator
@Controller
public class DisplayController {

    @SessionValidator(validated = false)
    @GetMapping("/")
    public String home() {
        return "page/login";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @SessionValidator(validated = false)
    @GetMapping("page/login")
    public String login() {
        return "page/login";
    }

    @GetMapping("page/welcome")
    public String welcome() {
        return "page/welcome";
    }

    @GetMapping("page/user/table")
    public String userTable() {
        return "page/user/table";
    }

    @GetMapping("page/user/add")
    public String userAdd() {
        return "page/user/add";
    }

    @GetMapping("page/user/edit")
    public String userEdit() {
        return "page/user/edit";
    }

    @GetMapping("page/admin/setting")
    public String userSetting() {
        return "page/admin/setting";
    }

    @GetMapping("page/admin/password")
    public String userPassword() {
        return "page/admin/password";
    }

    @GetMapping("page/key/table")
    public String keyTable() {
        return "page/key/table";
    }

    @GetMapping("page/key/add")
    public String keyAdd() {
        return "page/key/add";
    }

    @GetMapping("page/flow/table")
    public String flowTable() {
        return "page/flow/table";
    }

    @GetMapping("page/flow/add")
    public String flowAdd() {
        return "page/flow/add";
    }


}