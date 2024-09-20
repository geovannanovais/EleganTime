package model;

import java.util.ArrayList;

public class Produto {

    private int idProduto;
    private String nome;
    private double avaliacao;
    private String descricao;
    private double preco;
    private int quantidadeEmEstoque;
    private boolean condicaoDoProduto; // Condição do produto
    private ArrayList<Imagem> imagens; // Lista de imagens associadas ao produto

    // Construtor vazio
    public Produto() {
        this.imagens = new ArrayList<>(); // Inicializando a lista de imagens
    }

    // Construtor com todos os parâmetros
    public Produto(int idProduto, String nome, double avaliacao, String descricao, double preco, int quantidadeEmEstoque, boolean condicaoDoProduto, ArrayList<Imagem> imagens) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.condicaoDoProduto = condicaoDoProduto; // Inicializando condição do produto
        this.imagens = imagens != null ? imagens : new ArrayList<>(); // Inicializando a lista de imagens
    }

    // Construtor sem o parâmetro idProduto
    public Produto(String nome, double avaliacao, String descricao, double preco, int quantidadeEmEstoque, boolean condicaoDoProduto, ArrayList<Imagem> imagens) {
        this.nome = nome;
        this.avaliacao = avaliacao;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.condicaoDoProduto = condicaoDoProduto; // Inicializando condição do produto
        this.imagens = imagens != null ? imagens : new ArrayList<>(); // Inicializando a lista de imagens
    }

    // Getters e Setters
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
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

    public boolean getCondicaoDoProduto() {
        return condicaoDoProduto; // Getter para condição do produto
    }

    public void setCondicaoDoProduto(boolean condicaoDoProduto) {
        this.condicaoDoProduto = condicaoDoProduto; // Setter para condição do produto
    }

    public ArrayList<Imagem> getImagens() {
        return imagens; // Getter para imagens
    }

    public void setImagens(ArrayList<Imagem> imagens) {
        this.imagens = imagens != null ? imagens : new ArrayList<>(); // Setter para imagens
    }

    // Método toString para exibir as informações do produto
    @Override
    public String toString() {
        return idProduto + " | " + nome + " | " + avaliacao + " | " + descricao + " | " + preco + " | " + quantidadeEmEstoque + " | " + condicaoDoProduto + " | Imagens: " + imagens;
    }
}
