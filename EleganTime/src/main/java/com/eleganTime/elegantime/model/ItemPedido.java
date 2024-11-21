package com.eleganTime.elegantime.model;

import jakarta.persistence.*;

@Entity
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Item_Pedido")  // Adiciona o nome correto da coluna
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id_Produto", nullable = false)
    private Produto produto;  // Produto associado ao item

    @Column(nullable = false)
    private int quantidade;  // Quantidade do produto no pedido

    @Column(nullable = false)
    private double valorUnitario;  // Preço unitário do produto

    @Column(nullable = false)
    private double valorTotal;  // Valor total do item (quantidade * valorUnitario)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}