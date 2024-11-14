package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.repository.ProdutoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.eleganTime.elegantime.model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Produto salvar(Produto novoProduto) {
        return produtoRepository.save(novoProduto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto obterProdutoPorId(Integer produtoId) {
        Optional<Produto> produto = produtoRepository.findById(produtoId);
        return produto.orElse(null);
    }

    public void salvarProduto(Produto produto) {
        produtoRepository.save(produto);
    }

    public Produto atualizarProduto(int id, Produto produto) {

        Optional<Produto> produtoExistenteOpt = produtoRepository.findById(id);
        if (produtoExistenteOpt.isPresent()) {
            Produto produtoExistente = produtoExistenteOpt.get();


            produtoExistente.setNome(produto.getNome());
            produtoExistente.setPreco(produto.getPreco());
            produtoExistente.setQuantidadeEmEstoque(produto.getQuantidadeEmEstoque());
            produtoExistente.setDescricao(produto.getDescricao());
            produtoExistente.setAvaliacao(produto.getAvaliacao());
            produtoExistente.setImagens(produto.getImagens());

            return produtoRepository.save(produtoExistente);
        } else {
            return null;
        }
    }

    public void deletarProduto(Integer idProduto) {
        produtoRepository.deleteById(idProduto);
    }

    public Optional<Produto> buscarProdutoPorId(Integer idProduto) {
        return produtoRepository.findById(idProduto);
    }

    public List<Produto> listarProdutosAtivos() {
        return produtoRepository.findByCondicaoDoProduto(true);
    }
}

