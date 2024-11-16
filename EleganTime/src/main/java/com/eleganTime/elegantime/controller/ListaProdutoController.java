package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ListaProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listaProduto")
    public String listaProdutos(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Produto> produtos;

        if (search != null && !search.isEmpty()) {
            produtos = produtoService.buscarProdutosPorNome(search);
        } else {
            produtos = produtoService.listarProdutos();
        }

        model.addAttribute("produtos", produtos);
        model.addAttribute("search", search); // Para preencher o campo de busca
        return "listaProduto";
    }

    @PostMapping("/produto/{produtoId}/toggleStatus")
    @ResponseBody
    public ResponseEntity<?> toggleStatus(@PathVariable Integer produtoId) {
        try {
            Produto produto = produtoService.obterProdutoPorId(produtoId);
            if (produto != null) {
                // Inverte o status do produto
                produto.setCondicaoDoProduto(!produto.isCondicaoDoProduto());
                produtoService.salvarProduto(produto); // Salva a alteração no banco de dados
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao atualizar o status do produto.");
        }
    }

}
