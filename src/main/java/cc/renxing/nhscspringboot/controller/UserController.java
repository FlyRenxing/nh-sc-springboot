package cc.renxing.nhscspringboot.controller;

import cc.renxing.nhscspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    // 渲染注册页面
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        return "register";
    }

    // 处理用户注册请求
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirmPassword,
                               Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "密码和确认密码不一致！");
            return "register";
        }

        // 调用服务层保存用户信息
        try {
            userService.registerUser(username, password);
            return "redirect:/login"; // 注册成功，重定向到登录页面
        } catch (Exception e) {
            model.addAttribute("error", "注册失败：" + e.getMessage());
            return "register";
        }
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout-success")
    public String logoutSuccess() {
        return "logout-success";  // 返回退出成功的页面
    }
}
