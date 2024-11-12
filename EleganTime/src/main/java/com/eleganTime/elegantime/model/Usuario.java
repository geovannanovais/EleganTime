package com.eleganTime.elegantime.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private int idUsuario;

    private String nome;
    private String cpf;
    private String email;
    private String grupo;
    private String senha;
    private boolean condicaoDoUsuario;

    public Usuario() {}

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

    // Getters e Setters

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

    // Implementação dos métodos da interface UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aqui, você pode retornar os papéis do usuário, por exemplo, 'ROLE_ADMIN' ou 'ROLE_USUARIO'
        // Vamos assumir que o campo "grupo" representa o papel do usuário, então retornamos esse grupo
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + grupo.toUpperCase()));
    }

    @Override
    public String getPassword() {
        return senha;  // A senha do usuário
    }

    @Override
    public String getUsername() {
        return email;  // O nome de usuário, que é o email neste caso
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Podemos adicionar lógica para verificar se a conta expirou, mas aqui retornamos true
    }

    @Override
    public boolean isAccountNonLocked() {
        return condicaoDoUsuario;  // Retorna verdadeiro se a conta não estiver bloqueada, baseado no campo condicaoDoUsuario
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Retorna verdadeiro se as credenciais não estiverem expiradas
    }

    @Override
    public boolean isEnabled() {
        return condicaoDoUsuario;  // Retorna verdadeiro se a conta do usuário estiver habilitada (baseado no campo condicaoDoUsuario)
    }

    @Override
    public String toString() {
        return idUsuario + " | " + nome + " | " + cpf + " | " + email + " | " + grupo + " | " + condicaoDoUsuario + " | " + senha;
    }
}
