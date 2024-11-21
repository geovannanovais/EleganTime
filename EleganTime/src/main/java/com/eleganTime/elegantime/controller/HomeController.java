package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.CarrinhoService;
import com.eleganTime.elegantime.service.ProdutoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private CarrinhoService carrinhoService;

    // Método para exibir a página inicial
    @GetMapping("/home")
    public String home(Model model, HttpSession session, Authentication authentication) {
        // Lista de produtos ativos
        List<Produto> produtosAtivos = produtoService.listarProdutosAtivos();
        model.addAttribute("products", produtosAtivos);

        // Verifica se o usuário está logado
        String emailCliente = authentication != null ? authentication.getName() : null;

        // Verifica se o usuário tem a role "ADMINISTRADOR" ou "ESTOQUISTA"
        boolean isAdminOrEstoquista = authentication != null && authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMINISTRADOR") ||
                        grantedAuthority.getAuthority().equals("ROLE_ESTOQUISTA"));

        // Se não for ADMIN ou ESTOQUISTA, cria o carrinho (se for cliente)
        if (!isAdminOrEstoquista) {
            // Se o email for null (não está logado), cria um carrinho para o cliente anônimo
            Carrinho carrinho = carrinhoService.obterCarrinho(emailCliente, session);

            // Passa a quantidade de itens no carrinho para o frontend
            model.addAttribute("quantidadeCarrinho", carrinho.getItens().size());
        } else {
            // Se for ADMIN ou ESTOQUISTA, o carrinho não é necessário
            model.addAttribute("quantidadeCarrinho", 0);
        }

        // Passa a informação de se o usuário é ADMINISTRADOR ou ESTOQUISTA
        model.addAttribute("isAdminOrEstoquista", isAdminOrEstoquista);

        return "home";  // Retorna a view home
    }

    // Método para adicionar item ao carrinho
    @PostMapping("/home/adicionar/{produtoId}")
    public String adicionarAoCarrinho(@PathVariable int produtoId,
                                      @RequestParam int quantidade,
                                      HttpSession session, Authentication authentication) {

        // Recupera o email do cliente logado, se houver
        String emailCliente = authentication != null ? authentication.getName() : null;

        // Adiciona o item ao carrinho
        carrinhoService.adicionarItem(produtoId, quantidade, emailCliente, session);

        // Redireciona para a página inicial após adicionar o item
        return "redirect:/home";  // Redireciona para a página inicial
    }
}
