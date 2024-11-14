package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.CarrinhoService;
import com.eleganTime.elegantime.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Produto> produtosAtivos = produtoService.listarProdutosAtivos();
        model.addAttribute("products", produtosAtivos);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailCliente = authentication != null ? authentication.getName() : null;  // O 'name' é o email do cliente

        Carrinho carrinho;
        if (emailCliente != null) {
            Optional<Carrinho> carrinhoOpt = carrinhoService.obterCarrinhoPorCliente(emailCliente);
            carrinho = carrinhoOpt.orElseGet(Carrinho::new);
            model.addAttribute("quantidadeCarrinho", carrinho.getItens().size());
        } else {
            carrinho = carrinhoService.getCarrinhoEmMemoria(emailCliente);
            model.addAttribute("quantidadeCarrinho", carrinho.getItens().size());
        }

        return "home";
    }

    @PostMapping("/home/adicionar/{produtoId}")
    public String adicionarAoCarrinho(@PathVariable int produtoId,
                                      @RequestParam int quantidade) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailCliente = authentication != null ? authentication.getName() : null;

        Carrinho carrinho;
        if (emailCliente != null) {
            Optional<Carrinho> carrinhoOpt = carrinhoService.obterCarrinhoPorCliente(emailCliente);
            carrinho = carrinhoOpt.orElseGet(Carrinho::new);
        } else {
            carrinho = carrinhoService.getCarrinhoEmMemoria(emailCliente);
        }

        carrinhoService.adicionarItem(produtoId, quantidade, emailCliente);

        return "redirect:/home";  // Redireciona de volta à página inicial ou carrinho
    }
}
