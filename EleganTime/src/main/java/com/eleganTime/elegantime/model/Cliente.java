package com.eleganTime.elegantime.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
public class Cliente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    private String nome;

    @Column(unique = true)
    private String cpf;

    @Column(unique = true)
    private String email;

    private String senha;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private boolean condicaoDoCliente;
    private String dataNascimento;
    private String genero;
    private String cep;
    private String enderecoEntrega;

    public Cliente() {}

    public Cliente(String nome, String cpf, String email, String senha,
                   String logradouro, String numero, String complemento,
                   String bairro, String cidade, String uf,
                   boolean condicaoDoCliente, String dataNascimento,
                   String genero, String cep, String enderecoEntrega) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.condicaoDoCliente = condicaoDoCliente;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.cep = cep;
        this.enderecoEntrega = enderecoEntrega;
    }

    public int getIdCliente() {
        return id;
    }

    public void setIdCliente(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public boolean isCondicaoDoCliente() {
        return condicaoDoCliente;
    }

    public void setCondicaoDoCliente(boolean condicaoDoCliente) {
        this.condicaoDoCliente = condicaoDoCliente;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Neste caso, se você tiver um campo "grupo" ou "role", você pode atribuir a ele.
        // Exemplo: "ROLE_CLIENTE"
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_CLIENTE"));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;  // Aqui, normalmente usamos o e-mail ou outro identificador único
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Podemos adicionar lógica personalizada se for o caso (ex: data de expiração da conta)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Caso tenha uma flag para bloquear a conta (ex: status de ativação)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Se você tiver algo que defina expiração de credenciais (ex: senha expirada), implemente aqui
    }

    @Override
    public boolean isEnabled() {
        return condicaoDoCliente;  // Aqui podemos usar a condição do cliente como um indicativo se ele está ativo
    }

    // Métodos de getter e setter...

    @Override
    public String toString() {
        return id + " | " + nome + " | " + cpf + " | " + email + " | "
                + logradouro + ", " + numero + " " + complemento + " | "
                + bairro + " | " + cidade + " | " + uf + " | " + condicaoDoCliente
                + " | Data Nascimento: " + dataNascimento + " | Gênero: " + genero
                + " | CEP: " + cep + " | Endereço de Entrega: " + enderecoEntrega;
    }
}