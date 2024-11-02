package com.eleganTime.elegantime.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // Nome da coluna no banco de dados
    private int id;

    @Column(name = "nome") // Nome da coluna no banco de dados
    private String nome;

    @Column(name = "caminho", nullable = false) // Nome da coluna no banco de dados
    private String caminho;

    @Column(name = "principal", nullable = false) // Nome da coluna no banco de dados
    private boolean principal;

    @ManyToOne
    @JoinColumn(name = "id_Produto") // Chave estrangeira para a tabela Produto
    @JsonIgnore // Ignora a serialização do Produto nesta relação para evitar loop
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
