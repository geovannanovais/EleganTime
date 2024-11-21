package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class DetalhesProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/produto/{id}")
    public String mostrarProduto(@PathVariable("id") int id, Model model, Authentication authentication) {
        // Buscar o produto no banco
        Optional<Produto> produtoOptional = produtoService.buscarProdutoPorId(id);

        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();
            model.addAttribute("produto", produto);

            // Verificar se o usuário tem a role "ADMINISTRADOR"
            boolean isAdmin = authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"));
            model.addAttribute("isAdmin", isAdmin);

            return "detalhesProduto";  // nome da página de detalhes
        } else {
            model.addAttribute("erro", "Produto não encontrado");
            return "erro";
        }
    }
}
