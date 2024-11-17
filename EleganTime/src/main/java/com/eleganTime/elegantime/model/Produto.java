package com.eleganTime.elegantime.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_Produto;
    private String nome;
    private double avaliacao;
    private String descricao;
    private double preco;
    private int quantidadeEmEstoque;
    private boolean condicaoDoProduto = true;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagem> imagens = new ArrayList<>();

    public Produto() {
    }

    public Produto(String nome, double avaliacao, String descricao, double preco, int quantidadeEmEstoque,
            boolean condicaoDoProduto, List<Imagem> imagens) {
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.condicaoDoProduto = condicaoDoProduto;
        this.imagens = imagens != null ? imagens : new ArrayList<>(); // Garantindo uma lista não nula
    }

    public int getId_Produto() {
        return id_Produto;
    }

    public void setIdProduto(int idProduto) {
        this.id_Produto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }

    public boolean isCondicaoDoProduto() {
        return condicaoDoProduto;
    }

    public void setCondicaoDoProduto(boolean condicaoDoProduto) {
        this.condicaoDoProduto = condicaoDoProduto;
    }

    public List<Imagem> getImagens() {
        return imagens;
    }

    public void setImagens(List<Imagem> imagens) {
        this.imagens = imagens != null ? imagens : new ArrayList<>(); // Garantindo uma lista não nula
    }

    @Override
    public String toString() {
        return "Produto{" +
                "idProduto=" + id_Produto +
                ", nome='" + nome + '\'' +
                ", avaliacao=" + avaliacao +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidadeEmEstoque=" + quantidadeEmEstoque +
                ", condicaoDoProduto=" + condicaoDoProduto +
                ", imagens=" + imagens +
                '}';
    }
}