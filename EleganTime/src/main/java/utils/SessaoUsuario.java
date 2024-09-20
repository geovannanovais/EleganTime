package utils;

import model.Usuario;

public class SessaoUsuario {
    private static Usuario usuarioLogado;

    public static void setUsuario(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuario() {
        return usuarioLogado;
    }

    public static void logout() {
        usuarioLogado = null;
    }
}
