package service;

import model.Usuario;
import sqlDAO.UsuarioDAO;

import java.util.ArrayList;

public class UsuarioService {

    public boolean cadastrarUsuario(Usuario usuario) {
        return UsuarioDAO.salvar(usuario);
    }

    public boolean atualizarUsuario(Usuario usuario) {
        return UsuarioDAO.atualizarUsuario(usuario);
    }

    //public boolean deletarUsuario(int idUsuario) {
        //return UsuarioDAO.deletarUsuario(idUsuario);  // Supondo que você implemente o método de exclusão
    // }

    public ArrayList<Usuario> listar() {
        return UsuarioDAO.listar();
    }

    public Usuario buscarUsuarioPorId(int idUsuario) {
        return UsuarioDAO.buscarUsuarioPorId(idUsuario);  // Se você implementar essa função
    }
}
