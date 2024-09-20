package service;

import java.util.ArrayList;

import model.Imagem;
import model.Produto;
import model.Usuario;
import sqlDAO.ProdutoDAO;
import sqlDAO.UsuarioDAO;

public class UsuarioService {

    public boolean cadastrarUsuario(Usuario usuario) {
        return UsuarioDAO.salvar(usuario);
    }

    public boolean atualizarUsuario(Usuario usuario) {
        return UsuarioDAO.atualizarUsuario(usuario);
    }

    public boolean cadastrarProduto(Produto produto) {
        boolean sucesso = ProdutoDAO.salvar(produto);

        // Se o produto foi cadastrado com sucesso, exibe as imagens
        if (sucesso) {
            if (!produto.getImagens().isEmpty()) {
                System.out.println("Produto cadastrado com sucesso! Imagens cadastradas:");
                for (Imagem imagem : produto.getImagens()) {
                    System.out.println(" - " + imagem.getCaminho());
                }
            } else {
                System.out.println("Produto cadastrado com sucesso! Nenhuma imagem cadastrada.");
            }
        }

        return sucesso;
    }

    public boolean atualizarProduto(Produto produto) {
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

    // Novo método para carregar imagens de um produto
    public ArrayList<Imagem> carregarImagensProduto(int idProduto) {
        return ProdutoDAO.carregarImagens(idProduto);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = UsuarioDAO.buscarUsuarioPorEmail(email);

        if (usuario == null) {
            System.out.println("Usuário não encontrado.");
            return null;
        }

        if (!usuario.getCondicaoDoUsuario()) {
            System.out.println("Usuário inativo.");
            return null;
        }

        if (!usuario.getSenha().equals(senha)) {
            System.out.println("Email ou senha incorretos.");
            return null;
        }

        return usuario;
    }

    public boolean atualizarProdutoEstoque(Produto produto) {
        return ProdutoDAO.atualizarQuantidadeEstoque(produto);
    }
}
