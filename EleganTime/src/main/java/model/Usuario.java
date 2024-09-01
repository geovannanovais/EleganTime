package model;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String cpf;
    private String email;
    private String grupo; // Administrador ou Estoquista
    private int senha;
    private boolean condicaoDoUsuario;

    // Construtor vazio
    public Usuario() {

    }

    // Construtor para fazer o login
    public Usuario(String email, int senha) {
        this.email = email;
        this.senha = senha;
    }

    // Construtor com todos os parâmetros
    public Usuario(int idUsuario, String nome, String cpf, String email, String grupo, int senha, boolean condicaoDoUsuario) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.grupo = grupo;
        this.senha = senha;
        this.condicaoDoUsuario = condicaoDoUsuario;
    }

    // Construtor sem o parâmetro idUsuario
    public Usuario(String nome, String cpf, String email, String grupo, int senha, boolean condicaoDoUsuario) {
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

    public int getSenha() {
        return senha;
    }

    public void setSenha(int senha) {
        this.senha = senha;
    }

    public boolean getCondicaoDoUsuario() {
        return condicaoDoUsuario;
    }

    public void setCondicaoDoUsuario(boolean condicaoDoUsuario) {
        this.condicaoDoUsuario = condicaoDoUsuario;
    }

    // Para exibir
    @Override
    public String toString() {
        return idUsuario + " | " + nome + " | " + cpf + " | " + email + " | " + grupo + " | " + senha + " | " + condicaoDoUsuario;
    }
}
