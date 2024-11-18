package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String exibirDetalhesProduto(@PathVariable("id") int id, Model model) {
        Optional<Produto> produtoOptional = produtoService.buscarProdutoPorId(id);

        if (produtoOptional.isPresent()) {
            Produto produto = produtoOptional.get();  // Obtem o produto se presente
            model.addAttribute("produto", produto);
            return "detalhesProduto"; // Nome da página HTML
        } else {
            model.addAttribute("erro", "Produto não encontrado");
            return "erro"; // Redireciona para uma página de erro, caso o produto não exista
        }
    }
}