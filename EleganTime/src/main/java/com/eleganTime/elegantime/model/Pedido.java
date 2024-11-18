package com.eleganTime.elegantime.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Geração automática do ID no banco
    private int id;

    @Column(name = "id_Cliente", nullable = false)
    private int idCliente;  // Apenas o ID do Cliente

    @Column(name = "id_Carrinho", nullable = false)
    private int idCarrinho;  // Apenas o ID do Carrinho

    @Column(name = "id_Pagamento")
    private Integer idPagamento;  // ID do Pagamento (opcional)

    @Column(nullable = false)
    private String status = "Pendente";  // Status padrão

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
}
