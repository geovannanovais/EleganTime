package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * O controlador CarrinhoController gerencia as operações do carrinho de
 * compras,
 * incluindo exibir o carrinho, adicionar e remover itens.
 */
@Controller
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping
    public String verCarrinho(Model model) {
        model.addAttribute("carrinho", carrinhoService.obterCarrinho());
        model.addAttribute("total", carrinhoService.calcularTotal());
        return "carrinho";
    }

    @PostMapping("/adicionar/{produtoId}")
    public String adicionarAoCarrinho(@PathVariable int produtoId, @RequestParam int quantidade) {
        carrinhoService.adicionarItem(produtoId, quantidade);
        return "redirect:/carrinho"; // Redireciona para a página do carrinho
    }

    @PostMapping("/remover/{produtoId}")
    public String removerDoCarrinho(@PathVariable int produtoId) {
        carrinhoService.removerItem(produtoId);
        return "redirect:/carrinho";
    }
}
