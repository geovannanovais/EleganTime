package com.eleganTime.elegantime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AreaUsuarioController {
    @GetMapping("/areaUsuario")
    public String areaUsuario() {
        return "areaUsuario";
    }
}
