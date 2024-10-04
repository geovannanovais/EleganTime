package com.eleganTime.elegantime.controller;

import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    public List<Produto> listarProdutosInterno() {
        return produtoService.listarProdutos();
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