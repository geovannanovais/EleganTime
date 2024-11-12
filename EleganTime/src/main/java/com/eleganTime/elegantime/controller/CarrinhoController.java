package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    // Método para ver o carrinho
    @GetMapping("/carrinho")
    public String verCarrinho(Model model, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;

        // Obtemos o carrinho baseado no email (anônimo ou logado)
        Carrinho carrinho = carrinhoService.obterCarrinho(email);

        // Adiciona o carrinho e o total à view
        model.addAttribute("carrinho", carrinho);
        model.addAttribute("total", carrinhoService.calcularTotal(carrinho));

        return "carrinho";
    }

    // Método para adicionar item ao carrinho
    @PostMapping("/adicionar/{produtoId}")
    public String adicionarAoCarrinho(@PathVariable int produtoId,
                                      @RequestParam int quantidade, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        carrinhoService.adicionarItem(produtoId, quantidade, email);
        return "redirect:/carrinho";  // Redireciona para a página do carrinho
    }

    // Método para remover item do carrinho
    @GetMapping("/carrinho/remover/{produtoId}")
    public String removerItem(@PathVariable int produtoId, Authentication authentication) {
        String email = authentication != null ? authentication.getName() : null;
        carrinhoService.removerItem(produtoId, email);
        return "redirect:/carrinho";  // Redireciona para a página do carrinho
    }
}
