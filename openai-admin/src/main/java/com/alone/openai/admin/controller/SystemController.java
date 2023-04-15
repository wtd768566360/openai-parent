package com.alone.openai.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alone.openai.admin.annotation.SessionValidator;
import com.alone.openai.admin.constant.Constant;
import com.alone.openai.admin.pojo.dto.system.LoginDto;
import com.alone.openai.admin.pojo.dto.system.PasswordDto;
import com.alone.openai.admin.pojo.dto.user.ModifyDto;
import com.alone.openai.admin.pojo.entity.AdminEntity;
import com.alone.openai.admin.properties.OpenAIAdminProperties;
import com.alone.openai.admin.service.AdminService;
import com.alone.openai.common.controller.BaseController;
import com.alone.openai.common.response.Restful;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@SessionValidator
@RestController
@RequestMapping("system")
public class SystemController extends BaseController {

    private final AdminService adminService;

    private final HttpServletRequest httpServletRequest;

    private final OpenAIAdminProperties openAIAdminProperties;

    public SystemController(AdminService adminService, HttpServletRequest httpServletRequest,
                            OpenAIAdminProperties openAIAdminProperties) {
        this.adminService = adminService;
        this.httpServletRequest = httpServletRequest;
        this.openAIAdminProperties = openAIAdminProperties;
    }

    /**
     * 登录
     *
     * @return
     */
    @SessionValidator(validated = false)
    @PostMapping("login")
    public Restful login(@RequestBody LoginDto loginDto) {
        AdminEntity admin = adminService.login(loginDto.getAccount(), loginDto.getPassword());
        if (admin != null) {
            HttpSession httpSession = httpServletRequest.getSession();
            httpSession.setAttribute(Constant.SESSION_NAME, admin);
            AdminEntity newAdmin = new AdminEntity();
            BeanUtil.copyProperties(admin, newAdmin, "password");
            return toSuccess("登录成功", newAdmin);
        }
        return toError("登录失败,请检查账号密码");
    }

    /**
     * 登出
     *
     * @return
     */
    @PostMapping("logout")
    public Restful logout() {
        HttpSession httpSession = httpServletRequest.getSession();
        httpSession.removeAttribute(Constant.SESSION_NAME);
        return toSuccess("注销成功");
    }

    /**
     * 修改个人密码
     *
     * @return
     */
    @PatchMapping("password")
    public Restful password(@RequestBody PasswordDto passwordDto) {
        HttpSession httpSession = httpServletRequest.getSession();
        AdminEntity admin = (AdminEntity) httpSession.getAttribute(Constant.SESSION_NAME);
        int result = adminService.password(admin, passwordDto.getOldPassword(), passwordDto.getNewPassword());
        if (result == 1) {
            httpSession.removeAttribute(Constant.SESSION_NAME);
            return toSuccess("密码修改成功,点击确认按钮将退出登录");
        }
        if (result == -1) {
            return toError("修改失败,原密码错误");
        }
        return toError("密码修改失败");
    }

    /**
     * 修改个人信息
     */
    @PutMapping("modify")
    public Restful modify(@RequestBody ModifyDto modifyDto) {
        HttpSession httpSession = httpServletRequest.getSession();
        AdminEntity admin = (AdminEntity) httpSession.getAttribute(Constant.SESSION_NAME);
        int result = adminService.modify(admin.getId(), modifyDto.getName(), modifyDto.getPhone(), modifyDto.getEmail());
        return result == 1 ? toSuccess("修改成功") : toError("修改失败");
    }


    /**
     * 获取公钥
     *
     * @return
     */
    @SessionValidator(validated = false)
    @GetMapping("public/key")
    public Restful getPublicKey() {
        String publicKey = openAIAdminProperties.getPublicKey();
        if (StrUtil.isEmpty(publicKey)) {
            return toError("公钥获取失败");
        }
        return toSuccess("公钥获取成功", publicKey);
    }
}
