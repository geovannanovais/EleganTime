package com.eleganTime.elegantime.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Usuario") // Nome da coluna no banco de dados
    private int idUsuario;

    @Column(name = "nome", nullable = false) // Nome da coluna no banco de dados
    private String nome;

    @Column(name = "cpf", nullable = false) // Nome da coluna no banco de dados
    private String cpf;

    @Column(name = "email", nullable = false, unique = true) // Nome da coluna no banco de dados
    private String email;

    @Column(name = "grupo", nullable = false) // Nome da coluna no banco de dados
    private String grupo;

    @Column(name = "senha", nullable = false) // Nome da coluna no banco de dados
    private String senha;

    @Column(name = "condicao_Do_Usuario", nullable = false) // Nome da coluna no banco de dados
    private boolean condicaoDoUsuario = true;

    public Usuario() {
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String cpf, String email, String grupo, String senha, boolean condicaoDoUsuario) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.grupo = grupo;
        this.senha = senha;
        this.condicaoDoUsuario = condicaoDoUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isCondicaoDoUsuario() {
        return condicaoDoUsuario;
    }

    public void setCondicaoDoUsuario(boolean condicaoDoUsuario) {
        this.condicaoDoUsuario = condicaoDoUsuario;
    }

    @Override
    public String toString() {
        return idUsuario + " | " + nome + " | " + cpf + " | " + email + " | " + grupo + " | " + condicaoDoUsuario
                + " | " + senha;
    }
}
