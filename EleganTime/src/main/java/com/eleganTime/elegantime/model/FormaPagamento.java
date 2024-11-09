package com.eleganTime.elegantime.model;

public class FormaPagamento {

    private int idCliente;

    public FormaPagamento(int idCliente, String nome, String numeroCartao, String validade, String cvv, int parcelas) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.numeroCartao = numeroCartao;
        this.validade = validade;
        this.cvv = cvv;
        this.parcelas = parcelas;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    private String nome;
        private String numeroCartao;
        private String validade;
        private String cvv;
        private int parcelas;

    public FormaPagamento(String nome, String numeroCartao, String validade, String cvv, int parcelas) {
        this.nome = nome;
        this.numeroCartao = numeroCartao;
        this.validade = validade;
        this.cvv = cvv;
        this.parcelas = parcelas;
    }

    public FormaPagamento() {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public void setNumeroCartao(String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public int getParcelas() {
        return parcelas;
    }

    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }
}
