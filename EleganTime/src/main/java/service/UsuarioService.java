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

    public static boolean cadastrarProduto(Produto produto) {
        return ProdutoDAO.salvar(produto);
    }

    public static boolean atualizarProduto(Produto produto) {
        return ProdutoDAO.atualizarProduto(produto);
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

    public Produto buscarProdutoPorId(int idProduto) {
        return ProdutoDAO.buscarProdutoPorId(idProduto);
    }

    // Implementação do método de autenticação de usuário
    public Usuario autenticar(String email, String senha) {
        // Busca o usuário pelo email
        Usuario usuario = UsuarioDAO.buscarUsuarioPorEmail(email);

        // Verifica se o usuário existe
        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return null;
        }

        // Verifica se o usuário está ativo
        if (!usuario.getCondicaoDoUsuario()) {
            System.out.println("Usuário inativo.");
            return null;
        }

        // Verifica se a senha está correta
        if (!usuario.getSenha().equals(senha)) {
            System.out.println("Email ou senha incorretos.");
            return null;
        }

        // Retorna o usuário autenticado
        return usuario;
    }

    public boolean atualizarProdutoEstoque(Produto produto) {
        // Chama o DAO para atualizar apenas a quantidade em estoque
        return ProdutoDAO.atualizarQuantidadeEstoque(produto);
    }
}
