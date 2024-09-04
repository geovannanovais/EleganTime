package controller;

import model.Usuario;
import sqlDAO.UsuarioDAO;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        String nome = "Teste";
        String cpf = "11122233344";
        String email = "teste@teste.com.br";
        String grupo = "Administrador";
        String senha = "121212";
        boolean condicaoDoUsuario = true;

        // Criando um novo usuário com os dados fornecidos
        Usuario user = new Usuario(nome, cpf, email, grupo, senha, condicaoDoUsuario);

        // Salvando o usuário no banco de dados
        if (UsuarioDAO.salvar(user)) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Falha ao cadastrar o usuário.");
        }

        System.out.println("-------------");

        System.out.println("Lista de usuários:");

        // Obtém a lista de usuários
        ArrayList<Usuario> usuarios = UsuarioDAO.listar();

        // Imprime o cabeçalho da tabela
        System.out.println("ID | Nome  |      CPF       |     E-mail     |      Grupo    |  Senha  | Condição");
        System.out.println("---------------------------------------------------------------------");

        // Itera e imprime cada usuário
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
            System.out.println("---------------------------------------------------------------------"); // Linha separadora
        }
    }
}
