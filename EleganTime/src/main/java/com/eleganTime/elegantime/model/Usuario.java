package com.eleganTime.elegantime.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity // Define a classe como uma entidade JPA
@Table(name = "usuario") // Mapeia a entidade para a tabela "usuario" no banco de dados
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define o campo como chave primária com geração automática
    @Column(name = "id_Usuario") // Nome da coluna no banco de dados
    private int idUsuario;

    @NotBlank(message = "O nome é obrigatório") // Valida que o campo não é nulo, vazio ou apenas espaços em branco
    @Column(name = "nome", nullable = false) // Nome da coluna no banco de dados e define como obrigatório
    private String nome;

    @NotBlank(message = "O CPF é obrigaório")
    @Column(name = "cpf", nullable = false) // Nome da coluna no banco de dados e define como obrigatório
    private String cpf;

    @Email(message = "Insira um e-mail válido!")
    @NotBlank(message = "O e-mail é obrigaório")
    @Column(name = "email", nullable = false, unique = true) // Nome da coluna, obrigatório e valor único
    private String email;

    @NotBlank(message = "O grupo é obrigatório!")
    @Column(name = "grupo", nullable = false) // Nome da coluna e define como obrigatório
    private String grupo;

    @NotBlank(message = "A senha é obrigatório")
    @Column(name = "senha", nullable = false) // Nome da coluna e define como obrigatório
    private String senha;

    @Column(name = "condicao_Do_Usuario", nullable = false) // Nome da coluna e define como obrigatório
    private boolean condicaoDoUsuario = true; // Define valor padrão como verdadeiro (ativo)

    // Construtor vazio, necessário para o JPA
    public Usuario() {
    }

    // Construtor para login com email e senha
    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    // Construtor completo para todos os atributos
    public Usuario(String nome, String cpf, String email, String grupo, String senha, boolean condicaoDoUsuario) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.grupo = grupo;
        this.senha = senha;
        this.condicaoDoUsuario = condicaoDoUsuario;
    }

    // Métodos getters e setters para acesso e modificação dos atributos

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
        // Representação em String do objeto Usuario
        return idUsuario + " | " + nome + " | " + cpf + " | " + email + " | " + grupo + " | " + condicaoDoUsuario
                + " | " + senha;
    }
}
