package com.eleganTime.elegantime.model;

import jakarta.persistence.*;

@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;  // Relacionamento com o cliente

    @Column(nullable = false)
    private boolean principal; // Indica se é o endereço principal

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    @Override
    public String toString() {
        String logradouroCompleto = logradouro != null ? logradouro : "";
        String numeroCompleto = numero != null ? numero : "";
        String complementoCompleto = (complemento != null && !complemento.isEmpty()) ? " - " + complemento : "";
        String bairroCompleto = bairro != null ? bairro : "";
        String cidadeCompleta = cidade != null ? cidade : "";
        String ufCompleto = uf != null ? uf : "";
        String cepCompleto = cep != null ? cep : "";

        return String.format("%s, %s%s, %s, %s - %s, CEP: %s",
                logradouroCompleto,
                numeroCompleto,
                complementoCompleto,
                bairroCompleto,
                cidadeCompleta,
                ufCompleto,
                cepCompleto);
    }

}
