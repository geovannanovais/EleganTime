package sqlDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Usuario;

public class UsuarioDAO {

    // Dados de conexão
    private static final String URL = "jdbc:mysql://localhost:3306/elegantime";
    private static final String LOGIN = "root";
    private static final String SENHA = "2004Vit@";

    public static boolean salvar(Usuario novoUsuario) {

        String query = "INSERT INTO Usuario (nome, cpf, email, grupo, senha, condicaoDoUsuario) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA);
             PreparedStatement comandoSQL = conexao.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Configurando os parâmetros
            comandoSQL.setString(1, novoUsuario.getNome());
            comandoSQL.setString(2, novoUsuario.getCpf());
            comandoSQL.setString(3, novoUsuario.getEmail());
            comandoSQL.setString(4, novoUsuario.getGrupo());
            comandoSQL.setString(5, novoUsuario.getSenha());
            comandoSQL.setBoolean(6, novoUsuario.getCondicaoDoUsuario());

            int linhasAfetadas = comandoSQL.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = comandoSQL.getGeneratedKeys()) {
                    if (rs != null && rs.next()) {
                        int idGerado = rs.getInt(1);
                        novoUsuario.setIdUsuario(idGerado);
                    }
                }
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao salvar usuário", ex);
        }

        return false;
    }

    public static boolean atualizarSenha(int idUsuario, String novaSenha) {
        String query = "UPDATE Usuario SET senha = ? WHERE idUsuario = ?";

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA);
             PreparedStatement comandoSQL = conexao.prepareStatement(query)) {

            comandoSQL.setString(1, novaSenha);
            comandoSQL.setInt(2, idUsuario);

            int linhasAfetadas = comandoSQL.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao atualizar senha", ex);
        }

        return false;
    }



    public static boolean atualizarUsuario(Usuario usuario) {
        String query = "UPDATE Usuario SET nome = ?, cpf = ?, grupo = ?, senha = ?, condicaoDoUsuario = ? WHERE idUsuario = ?";

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA);
             PreparedStatement comandoSQL = conexao.prepareStatement(query)) {

            comandoSQL.setString(1, usuario.getNome());
            comandoSQL.setString(2, usuario.getCpf());
            comandoSQL.setString(3, usuario.getGrupo());
            comandoSQL.setString(4, usuario.getSenha());
            comandoSQL.setBoolean(5, usuario.getCondicaoDoUsuario());
            comandoSQL.setInt(6, usuario.getIdUsuario());

            int linhasAfetadas = comandoSQL.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao atualizar usuário", ex);
        }

        return false;
    }

    public static ArrayList<Usuario> listarUsuario() {

        String query = "SELECT * FROM Usuario";
        ArrayList<Usuario> lista = new ArrayList<>();

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA);
             PreparedStatement comandoSQL = conexao.prepareStatement(query);
             ResultSet rs = comandoSQL.executeQuery()) {

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

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao listar usuários", ex);
        }

        return lista;
    }

    public static Usuario buscarUsuarioPorId(int idUsuario) {
        String query = "SELECT * FROM Usuario WHERE idUsuario = ?";
        Usuario usuarioEncontrado = null;

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA);
             PreparedStatement comandoSQL = conexao.prepareStatement(query)) {

            comandoSQL.setInt(1, idUsuario);

            try (ResultSet rs = comandoSQL.executeQuery()) {
                if (rs.next()) {
                    usuarioEncontrado = new Usuario();
                    usuarioEncontrado.setIdUsuario(rs.getInt("idUsuario"));
                    usuarioEncontrado.setNome(rs.getString("nome"));
                    usuarioEncontrado.setCpf(rs.getString("cpf"));
                    usuarioEncontrado.setEmail(rs.getString("email"));
                    usuarioEncontrado.setGrupo(rs.getString("grupo"));
                    usuarioEncontrado.setSenha(rs.getString("senha"));
                    usuarioEncontrado.setCondicaoDoUsuario(rs.getBoolean("condicaoDoUsuario"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao buscar usuário por ID", ex);
        }

        return usuarioEncontrado;
    }

    // funcao para validar o login
    public static Usuario buscarUsuarioPorEmail(String email) {
        String query = "SELECT * FROM Usuario WHERE email = ?";
        Usuario usuarioEncontrado = null;

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA);
             PreparedStatement comandoSQL = conexao.prepareStatement(query)) {

            comandoSQL.setString(1, email);

            try (ResultSet rs = comandoSQL.executeQuery()) {
                if (rs.next()) {
                    usuarioEncontrado = new Usuario();
                    usuarioEncontrado.setIdUsuario(rs.getInt("idUsuario"));
                    usuarioEncontrado.setNome(rs.getString("nome"));
                    usuarioEncontrado.setCpf(rs.getString("cpf"));
                    usuarioEncontrado.setEmail(rs.getString("email"));
                    usuarioEncontrado.setGrupo(rs.getString("grupo"));
                    usuarioEncontrado.setSenha(rs.getString("senha"));
                    usuarioEncontrado.setCondicaoDoUsuario(rs.getBoolean("condicaoDoUsuario"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, "Erro ao buscar usuário por email", ex);
        }

        return usuarioEncontrado;
    }
}