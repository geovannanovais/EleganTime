package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping()
    public List<Produto> listarProdutosInterno() {
        List<Produto> lista = produtoService.listarProdutos();
        return lista;
    }

    public Produto salvar(Produto produto) {
        return produtoService.salvar(produto);
    }

    public Optional<Produto> buscarProdutoPorId(int id) {
        return produtoService.buscarProdutoPorId(id);
    }

    public Produto atualizarProdutoInterno(int id, Produto produto) {
        return produtoService.atualizarProduto(id, produto);
    }

    public void deletarProdutoInterno(int id) {
        produtoService.deletarProduto(id);
    }
}