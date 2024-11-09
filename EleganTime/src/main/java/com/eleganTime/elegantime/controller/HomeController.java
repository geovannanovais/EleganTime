package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.CarrinhoService;
import com.eleganTime.elegantime.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
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
    public String home(@RequestParam(value = "clienteId", required = false) Integer clienteId, Model model) {
        // Obtém a lista de produtos
        List<Produto> produtos = produtoService.listarProdutos();
        model.addAttribute("products", produtos);

        if (clienteId != null) {
            // Para usuários logados, pega o carrinho do cliente
            Optional<Carrinho> carrinhoOpt = carrinhoService.obterCarrinhoPorCliente(clienteId);
            Carrinho carrinho = carrinhoOpt.orElseGet(Carrinho::new);

            // Exibe a quantidade de itens no carrinho
            model.addAttribute("quantidadeCarrinho", carrinho.getItens().size());
            model.addAttribute("clienteId", clienteId);  // Passa o clienteId para o template
        } else {
            // Para usuários não logados, o carrinho será tratado de forma anônima
            model.addAttribute("quantidadeCarrinho", 0);  // Sem clienteId, carrinho vazio
        }

        return "Home";  // Exibe a mesma página de home
    }




    @PostMapping("/home/adicionar/{produtoId}")
    public String adicionarAoCarrinho(@PathVariable int produtoId,
                                      @RequestParam int quantidade,
                                      @RequestParam("clienteId") int clienteId) {
        // Recupera o carrinho do cliente
        Optional<Carrinho> carrinhoOpt = carrinhoService.obterCarrinhoPorCliente(clienteId);
        Carrinho carrinho = carrinhoOpt.orElseGet(Carrinho::new);

        // Adiciona o produto ao carrinho
        carrinhoService.adicionarItem(carrinho.getIdCarrinho(), produtoId, quantidade);

        // Redireciona para a página inicial com o carrinho atualizado
        return "redirect:/home?clienteId=" + clienteId;
    }

}
