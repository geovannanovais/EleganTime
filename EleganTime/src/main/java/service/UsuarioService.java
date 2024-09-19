package service;

import model.Produto;
import model.Usuario;
import sqlDAO.ProdutoDAO;
import sqlDAO.UsuarioDAO;

import java.util.ArrayList;

public class UsuarioService {

    public boolean cadastrarUsuario(Usuario usuario) {
        return UsuarioDAO.salvar(usuario);
    }

    public boolean atualizarUsuario(Usuario usuario) {
        return UsuarioDAO.atualizarUsuario(usuario);
    }

    public static boolean cadastrarProduto(Produto produto) { return ProdutoDAO.salvar(produto);
    }

    public static boolean atualizarProduto(Produto produto) { return ProdutoDAO.atualizarProduto(produto);
    }

    public ArrayList<Usuario> listarUsuario() {
        return UsuarioDAO.listarUsuario();
    }

    public ArrayList<Produto> listarProduto() {
        return ProdutoDAO.listarProduto();
    }

    public Usuario buscarUsuarioPorId(int idUsuario) {
        return UsuarioDAO.buscarUsuarioPorId(idUsuario);
    }

    public Produto buscarProdutoPorId (int idProduto) {
        return ProdutoDAO.buscarProdutoPorId(idProduto);
    }

    // Fazer login
    public String validarLogin(String email, String senha) {
        Usuario usuario = UsuarioDAO.buscarUsuarioPorEmail(email);  // Agora pode chamar como estático


        if (usuario == null) {
            return "Usuário não encontrado.";
        }

        if (!usuario.getCondicaoDoUsuario()) {
            return "Usuário inativo.";
        }

        if (!usuario.getSenha().equals(senha)) {
            return "Email ou senha incorretos.";
        }

        return "Login realizado com sucesso!";
    }
}
