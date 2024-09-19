package model;

public class Produto {

    private int idProduto;
    private String nome;
    private String descricao;
    private double preco;
    private int quantidadeEmEstoque;
    private boolean condicaoDoProduto; // Adicionando condição do produto

    // Construtor vazio
    public Produto() {
    }

    // Construtor com todos os parâmetros
    public Produto(int idProduto, String nome, String descricao, double preco, int quantidadeEmEstoque, boolean condicaoDoProduto) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.condicaoDoProduto = condicaoDoProduto; // Inicializando condição do produto
    }

    // Construtor sem o parâmetro idProduto
    public Produto(String nome, String descricao, double preco, int quantidadeEmEstoque, boolean condicaoDoProduto) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidadeEmEstoque = quantidadeEmEstoque;
        this.condicaoDoProduto = condicaoDoProduto; // Inicializando condição do produto
    }

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

    // Para exibir
    @Override
    public String toString() {
        return idProduto + " | " + nome + " | " + descricao + " | " + preco + " | " + quantidadeEmEstoque + " | " + condicaoDoProduto;
    }
}
