package com.eleganTime.elegantime.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Date;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_Cliente", nullable = false)
    private int idCliente;

    @Column(name = "id_Carrinho", nullable = false)
    private int idCarrinho;

    @Column(name = "id_Pagamento")
    private Integer idPagamento;

    @Column(nullable = false)
    private String status = "Aguardando Pagamento";  // Status do pedido alterado para "Aguardando Pagamento"

    @Column(name = "numero_Pedido", unique = true, nullable = false)
    private int numeroPedido;

    @Column(name = "endereco_Entrega", nullable = false)
    private String enderecoEntrega;

    @Column(name = "valor_frete", nullable = false, columnDefinition = "DOUBLE(10,2) DEFAULT 0.00")
    private double frete;

    @Column(name = "total", nullable = false, columnDefinition = "DOUBLE(10,2) DEFAULT 0.00")
    private double total;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens;  // Itens do pedido

    // Adiciona a nova coluna para data de criação
    @Column(name = "data_criacao", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdCarrinho() {
        return idCarrinho;
    }

    public void setIdCarrinho(int idCarrinho) {
        this.idCarrinho = idCarrinho;
    }

    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(int numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public double getFrete() {
        return frete;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
