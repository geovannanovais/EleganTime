package com.eleganTime.elegantime.service;

import com.eleganTime.elegantime.model.Carrinho;
import com.eleganTime.elegantime.model.ItemCarrinho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eleganTime.elegantime.model.Produto;

/**
 * A classe CarrinhoService fornece serviços relacionados ao carrinho de
 * compras,
 * como adicionar e remover itens, além de calcular o total do carrinho.
 */

@Service
public class CarrinhoService {

    /** O carrinho de compras associado a este serviço. */
    private Carrinho carrinho = new Carrinho();

    /** Serviço para manipulação de produtos, usado para buscar produtos por ID. */
    @Autowired
    private ProdutoService produtoService;

    /**
     * Adiciona um item ao carrinho de compras.
     *
     * @param produtoId  o ID do produto a ser adicionado
     * @param quantidade a quantidade do produto a ser adicionada
     * @throws RuntimeException se o produto não for encontrado
     */
    public void adicionarItem(int produtoId, int quantidade) {
        Produto produto = produtoService.buscarProdutoPorId(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // Verifica se o produto já está no carrinho
        ItemCarrinho itemExistente = carrinho.getItens().stream()
                .filter(item -> item.getProduto().getId_Produto() == produtoId)
                .findFirst()
                .orElse(null);

        if (itemExistente != null) {
            itemExistente.setQuantidade(itemExistente.getQuantidade() + quantidade);
        } else {
            carrinho.adicionarItem(new ItemCarrinho(produto, quantidade));
        }
    }

    /**
     * Remove um item do carrinho de compras com base no ID do produto.
     *
     * @param produtoId o ID do produto a ser removido
     */
    public void removerItem(int produtoId) {
        carrinho.removerItem(produtoId);
    }

    /**
     * Obtém o carrinho de compras atual.
     *
     * @return o carrinho de compras
     */
    public Carrinho obterCarrinho() {
        return carrinho;
    }

    /**
     * Calcula o valor total dos itens no carrinho de compras.
     *
     * @return o valor total do carrinho de compras
     */
    public double calcularTotal() {
        return carrinho.calcularTotal();
    }
}
