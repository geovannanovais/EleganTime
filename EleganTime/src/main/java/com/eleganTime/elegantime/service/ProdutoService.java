package com.eleganTime.elegantime.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.eleganTime.elegantime.model.Produto;
import com.eleganTime.elegantime.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvar(Produto novoProduto) {
        return produtoRepository.save(novoProduto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto atualizarProduto(int id, Produto produto) {
        produto.setIdProduto(id);
        return produtoRepository.save(produto);
    }

    public void deletarProduto(Integer idProduto) {
        produtoRepository.deleteById(idProduto);
    }

    public Optional<Produto> buscarProdutoPorId(Integer idProduto) {
        return produtoRepository.findById(idProduto);
    }
}
