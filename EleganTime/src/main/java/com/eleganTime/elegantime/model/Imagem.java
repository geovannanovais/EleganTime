package com.eleganTime.elegantime.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String caminho;
    private boolean principal;

    @ManyToOne
    @JoinColumn(name = "idProduto")
    // @JoinColumn(name = "produto_id")
    private Produto produto;

    public Imagem() {
    }

    public Imagem(String nome, String caminho, boolean principal, Produto produto) {
        this.nome = nome;
        this.caminho = caminho;
        this.principal = principal;
        this.produto = produto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Imagem{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", caminho='" + caminho + '\'' +
                ", principal=" + principal +
                ", produto=" + (produto != null ? produto.getId_Produto() : "null") +
                '}';
    }
}
