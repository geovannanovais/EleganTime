package com.eleganTime.elegantime.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A classe ItemCarrinho representa um item em um carrinho de compras,
 * contendo informações sobre o produto e sua quantidade.
 */
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idItemCarrinho;

    @ManyToOne
    @JoinColumn(name = "id_produto")
    private Produto produto; // Relacionamento com Produto (FK)

    @ManyToOne
    @JoinColumn(name = "id_carrinho")
    private Carrinho carrinho; // Relacionamento com Carrinho (FK)

    private int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    /**
     * Retorna o produto no item do carrinho.
     *
     * @return o produto no item do carrinho
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Retorna a quantidade do produto no item do carrinho.
     *
     * @return a quantidade do produto no item do carrinho
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do produto no item do carrinho.
     *
     * @param quantidade a nova quantidade do produto no item do carrinho
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
