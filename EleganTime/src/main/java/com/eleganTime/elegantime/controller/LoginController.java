package com.eleganTime.elegantime.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    // Página de login
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Usuário ou senha incorretos!");
        }
        return "login";  // A página de login (login.html)
    }

    // Página de acesso negado (quando o usuário não tem permissão para acessar algo)
    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";  // A página de acesso negado (accessDenied.html)
    }
}
