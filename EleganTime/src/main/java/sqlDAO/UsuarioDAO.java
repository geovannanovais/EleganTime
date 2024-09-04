package sqlDAO;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    // Criando o caminho/conexão com o banco de dados
    static String url = "jdbc:mysql://localhost:3306/elegantime";
    static String login = "admin";  // login do seu usuário/conexão no Workbench
    static String senha = "admin"; // senha do seu usuário/conexão Workbench

    public static boolean salvar(Usuario novoUsuario) {

        // Criando conexão
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet resultado = null;
        boolean retorno = false;

        try {
            // Carregando o Driver
            Class.forName("com.mysql.cj.jdbc.Driver"); // driver

            // Abrindo a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);

            // Preparando os comandos SQL a ser executado
            comandoSQL = conexao.prepareStatement(
                    "INSERT INTO Usuario (nome, cpf, email, grupo, senha, condicaoDoUsuario) VALUES(?, ?, ?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            // Pegando as informações do objeto para mandar para o banco
            comandoSQL.setString(1, novoUsuario.getNome());
            comandoSQL.setString(2, novoUsuario.getCpf());
            comandoSQL.setString(3, novoUsuario.getEmail());
            comandoSQL.setString(4, novoUsuario.getGrupo());
            comandoSQL.setInt(5, novoUsuario.getSenha());
            comandoSQL.setBoolean(6, novoUsuario.getCondicaoDoUsuario());

            // Executar os comandos
            int linhasAfetadas = comandoSQL.executeUpdate();

            if (linhasAfetadas > 0) {
                retorno = true;

                ResultSet rs = comandoSQL.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    int idGerado = rs.getInt(1);
                    novoUsuario.setIdUsuario(idGerado);
                }
                rs.close(); // Fechar o ResultSet após o uso
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            retorno = false;

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

        return retorno;
    }

    public static ArrayList<Usuario> listar() {

        // Criando conexão
        ArrayList<Usuario> lista = new ArrayList<>();
        Connection conexao = null;
        PreparedStatement comandoSQL = null;
        ResultSet rs = null;

        try {
            // Passo 1: Carregar o Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Passo 2: Abrir a conexão com o banco
            conexao = DriverManager.getConnection(url, login, senha);

            // Passo 3: Preparar os comandos SQL a ser executado
            comandoSQL = conexao.prepareStatement("SELECT * FROM Usuario");

            // Passo 4: Executar o comando SQL
            rs = comandoSQL.executeQuery();

            if (rs != null) {
                // Percorrer as linhas do ResultSet
                while (rs.next()) {

                    Usuario dados = new Usuario();

                    dados.setIdUsuario(rs.getInt("idUsuario"));
                    dados.setNome(rs.getString("nome"));
                    dados.setCpf(rs.getString("cpf"));
                    dados.setEmail(rs.getString("email"));
                    dados.setGrupo(rs.getString("grupo"));
                    dados.setSenha(rs.getInt("senha"));
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
