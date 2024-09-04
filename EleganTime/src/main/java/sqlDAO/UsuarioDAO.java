package sqlDAO;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    // Criando o caminho/conexão com o banco de dados
    static String url = "jdbc:mysql://localhost:3306/elegantime";
    static String login = "root";  // login do seu usuário/conexão no Workbench
    static String senha = "root"; // senha do seu usuário/conexão Workbench

    public static boolean salvar(Usuario novoUsuario) {

        // Criando conexão
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet resultado = null;
        boolean salvarUsuario = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // driver

            conexao = DriverManager.getConnection(url, login, senha);

            comandoSQL = conexao.prepareStatement(
                    "INSERT INTO Usuario (nome, cpf, email, grupo, senha, condicaoDoUsuario) VALUES(?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            // Pegando as informações do objeto para mandar para o banco
            comandoSQL.setString(1, novoUsuario.getNome());
            comandoSQL.setString(2, novoUsuario.getCpf());
            comandoSQL.setString(3, novoUsuario.getEmail());
            comandoSQL.setString(4, novoUsuario.getGrupo());
            comandoSQL.setString(5, novoUsuario.getSenha());
            comandoSQL.setBoolean(6, novoUsuario.getCondicaoDoUsuario());

            int linhasAfetadas = comandoSQL.executeUpdate();

            if (linhasAfetadas > 0) {
                salvarUsuario = true;

                ResultSet rs = comandoSQL.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    int idGerado = rs.getInt(1);
                    novoUsuario.setIdUsuario(idGerado);
                }
                rs.close(); // Fechar o ResultSet após o uso
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            salvarUsuario = false;

        } finally {
            if (comandoSQL != null) {
                try {
                    comandoSQL.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return salvarUsuario;
    }

    public static boolean atualizarSenha(int idUsuario, String novaSenha) {

        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        boolean atualizarSenha = false;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexao = DriverManager.getConnection(url, login, senha);

            comandoSQL = conexao.prepareStatement("UPDATE Usuario SET senha = ? WHERE idUsuario = ?"
            );

            // Pegando as informações do objeto para mandar para o banco
            comandoSQL.setString(1, novaSenha);
            comandoSQL.setInt(2, idUsuario);

            int linhasAfetadas = comandoSQL.executeUpdate();

            if (linhasAfetadas > 0) {
                atualizarSenha = true;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            atualizarSenha = false;

        } finally {
            if (comandoSQL != null) {
                try {
                    comandoSQL.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return atualizarSenha;
    }

    public static boolean atualizarUsuario(Usuario usuario) {

        if (usuario.getIdUsuario() == usuario.getIdUsuario()) {
            System.out.println("Você não pode alterar o seu próprio grupo.");
            return false;
        }
        // Criando conexão
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        boolean usuarioAtualizado = false;

        try {
            // Carregando o Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // driver

            // Abrindo a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);

            // Preparando o comando SQL para atualização dos dados do usuário
            comandoSQL = conexao.prepareStatement(
                    "UPDATE Usuario SET nome = ?, cpf = ?, grupo = ?"
            );

            // Definindo os valores dos parâmetros
            comandoSQL.setString(1, usuario.getNome());
            comandoSQL.setString(2, usuario.getCpf());
            comandoSQL.setString(3, usuario.getGrupo());

            // Executar o comando
            int linhasAfetadas = comandoSQL.executeUpdate();

            // Verifica se a atualização foi bem-sucedida
            if (linhasAfetadas > 0) {
                usuarioAtualizado = true;
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            usuarioAtualizado = false;

        } finally {
            // Fechar conexão, statements, etc.
            if (comandoSQL != null) {
                try {
                    comandoSQL.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return usuarioAtualizado;
    }

    public static ArrayList<Usuario> listar() {

        ArrayList<Usuario> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexao = DriverManager.getConnection(url, login, senha);

            comandoSQL = conexao.prepareStatement("SELECT * FROM Usuario");

            rs = comandoSQL.executeQuery();

            if (rs != null) {
                while (rs.next()) {

                    Usuario dados = new Usuario();

                    dados.setIdUsuario(rs.getInt("idUsuario"));
                    dados.setNome(rs.getString("nome"));
                    dados.setCpf(rs.getString("cpf"));
                    dados.setEmail(rs.getString("email"));
                    dados.setGrupo(rs.getString("grupo"));
                    dados.setSenha(rs.getString("senha"));
                    dados.setCondicaoDoUsuario(rs.getBoolean("condicaoDoUsuario"));

                    lista.add(dados);
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return lista;
    }
}
