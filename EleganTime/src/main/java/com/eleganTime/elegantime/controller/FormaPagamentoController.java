package com.eleganTime.elegantime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class FormaPagamentoController {

    @GetMapping("/pagamento")
    public String exibirFormularioPagamento(Model model) {


        return "formaPagamento";
    }


}
