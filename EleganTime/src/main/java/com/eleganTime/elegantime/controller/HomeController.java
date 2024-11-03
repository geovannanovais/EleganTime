package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class HomeController {


    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Produto> produtos = produtoService.listarProdutos();
        model.addAttribute("products", produtos);

        return "Home";
    }
}
