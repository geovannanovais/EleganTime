package com.eleganTime.elegantime.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinho")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Carrinho")
    private int idCarrinho;

    private double total;
    private double valorFrete;


    @ManyToOne
    @JoinColumn(name = "id_Cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    public Carrinho() {}

    public Carrinho(Cliente cliente) {
        this.cliente = cliente;
    }

    public void adicionarItem(ItemCarrinho item) {
        itens.add(item);
    }

    public void removerItem(int produtoId) {
        itens.removeIf(item -> item.getProduto().getId_Produto() == produtoId);
    }


    public int getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(int idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }

    public void setItens(List<ItemCarrinho> itens) {
        this.itens = itens;
    }

    public double getValorTotal() {
        return total;
    }

    public void setValorTotal(double total) {
        this.total = total;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    @Override
    public String toString() {
        return idCarrinho + " | " + cliente.getNome();
    }
}
