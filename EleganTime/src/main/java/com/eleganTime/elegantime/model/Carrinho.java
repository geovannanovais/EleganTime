package com.eleganTime.elegantime.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A classe Carrinho representa um carrinho de compras em uma loja.
 * Ela armazena uma lista de itens do carrinho e fornece métodos para
 * adicionar, remover e calcular o valor total dos itens.
 */
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCarrinho;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente; // Relacionamento com Cliente (FK)

    /**
     * Lista de itens no carrinho.
     */
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    /**
     * Adiciona um item ao carrinho.
     *
     * @param item o item a ser adicionado ao carrinho
     */
    public void adicionarItem(ItemCarrinho item) {
        itens.add(item);
    }

    /**
     * Remove um item do carrinho com base no ID do produto.
     *
     * @param produtoId o ID do produto a ser removido do carrinho
     */
    public void removerItem(int produtoId) {
        itens.removeIf(item -> item.getProduto().getId_Produto() == produtoId);
    }

    /**
     * Calcula o valor total dos itens no carrinho.
     *
     * @return o valor total de todos os itens no carrinho
     */
    public double calcularTotal() {
        return itens.stream()
                .mapToDouble(item -> item.getProduto().getPreco() * item.getQuantidade())
                .sum();
    }

    /**
     * Retorna a lista de itens no carrinho.
     *
     * @return uma lista de itens do carrinho
     */
    public List<ItemCarrinho> getItens() {
        return itens;
    }
}
