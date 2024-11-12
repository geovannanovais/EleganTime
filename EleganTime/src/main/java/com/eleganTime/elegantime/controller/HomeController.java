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

// HomeController.java

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CarrinhoService carrinhoService;

    /**
     * Exibe a página inicial com a lista de produtos.
     * Se o cliente estiver logado, mostra o carrinho do cliente, senão, usa carrinho anônimo.
     */
    @GetMapping("/home")
    public String home(Model model) {
        // Obtém a lista de produtos
        List<Produto> produtos = produtoService.listarProdutos();
        model.addAttribute("products", produtos);

        // Verifica se o cliente está logado usando Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailCliente = authentication != null ? authentication.getName() : null;  // O 'name' é o email do cliente

        Carrinho carrinho;
        if (emailCliente != null) {
            // Para clientes logados, pega o carrinho associado ao cliente pelo email
            Optional<Carrinho> carrinhoOpt = carrinhoService.obterCarrinhoPorCliente(emailCliente);
            carrinho = carrinhoOpt.orElseGet(Carrinho::new);  // Caso o carrinho não exista, cria um novo
            model.addAttribute("quantidadeCarrinho", carrinho.getItens().size());
        } else {
            // Para usuários não logados, cria um carrinho anônimo (não precisa persistir no banco)
            carrinho = carrinhoService.getCarrinhoEmMemoria(emailCliente);  // Método para obter carrinho em memória
            model.addAttribute("quantidadeCarrinho", carrinho.getItens().size());
        }

        return "Home";  // Exibe a página inicial
    }

    /**
     * Adiciona um produto ao carrinho.
     * Se o usuário estiver logado, usa o carrinho do cliente, senão, usa o carrinho anônimo.
     */
    @PostMapping("/home/adicionar/{produtoId}")
    public String adicionarAoCarrinho(@PathVariable int produtoId,
                                      @RequestParam int quantidade) {
        // Verifica se o cliente está logado usando Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailCliente = authentication != null ? authentication.getName() : null;  // O 'name' é o email do cliente

        Carrinho carrinho;
        if (emailCliente != null) {
            // Para clientes logados, pega o carrinho associado ao cliente pelo email
            Optional<Carrinho> carrinhoOpt = carrinhoService.obterCarrinhoPorCliente(emailCliente);
            carrinho = carrinhoOpt.orElseGet(Carrinho::new);  // Caso o carrinho não exista, cria um novo
        } else {
            // Para usuários não logados, cria um carrinho anônimo
            carrinho = carrinhoService.getCarrinhoEmMemoria(emailCliente);  // Método para obter carrinho em memória
        }

        // Adiciona o produto ao carrinho (não persiste no banco diretamente)
        carrinhoService.adicionarItem(produtoId, quantidade, emailCliente);

        // Atualiza o carrinho no contexto (não salva no banco diretamente)
        return "redirect:/home";  // Redireciona de volta à página inicial ou carrinho
    }
}
