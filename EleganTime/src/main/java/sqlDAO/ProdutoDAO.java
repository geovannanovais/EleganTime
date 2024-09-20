package sqlDAO;

import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProdutoDAO {

    // Dados de conexão
    private static final String URL = "jdbc:mysql://localhost:3306/elegantime";
    private static final String LOGIN = "root";
    private static final String SENHA = "root";

    public static boolean salvar(Produto novoProduto) {
        String query = "INSERT INTO Produto (nome, avaliacao, descricao, preco, quantidadeEmEstoque, condicaoDoProduto) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA); PreparedStatement comandoSQL = conexao.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            // Configurando os parâmetros
            comandoSQL.setString(1, novoProduto.getNome());
            comandoSQL.setDouble(2, novoProduto.getAvaliacao());
            comandoSQL.setString(3, novoProduto.getDescricao());
            comandoSQL.setDouble(4, novoProduto.getPreco());
            comandoSQL.setInt(5, novoProduto.getQuantidadeEmEstoque());
            comandoSQL.setBoolean(6, novoProduto.getCondicaoDoProduto()); // Ajuste aqui

            int linhasAfetadas = comandoSQL.executeUpdate();

            if (linhasAfetadas > 0) {
                try (ResultSet rs = comandoSQL.getGeneratedKeys()) {
                    if (rs != null && rs.next()) {
                        int idGerado = rs.getInt(1);
                        novoProduto.setIdProduto(idGerado);
                    }
                }
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, "Erro ao salvar produto", ex);
        }

        return false;
    }

    public static boolean atualizarProduto(Produto produto) {
        String query = "UPDATE Produto SET nome = ?, avaliacao = ?, descricao = ?, preco = ?, quantidadeEmEstoque = ?, condicaoDoProduto = ? WHERE idProduto = ?";

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA); PreparedStatement comandoSQL = conexao.prepareStatement(query)) {

            comandoSQL.setString(1, produto.getNome());
            comandoSQL.setDouble(2, produto.getAvaliacao());
            comandoSQL.setString(3, produto.getDescricao());
            comandoSQL.setDouble(4, produto.getPreco());
            comandoSQL.setInt(5, produto.getQuantidadeEmEstoque());
            comandoSQL.setBoolean(6, produto.getCondicaoDoProduto());
            comandoSQL.setInt(7, produto.getIdProduto());

            int linhasAfetadas = comandoSQL.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, "Erro ao atualizar produto: ", ex);
        }

        return false;
    }

    public static ArrayList<Produto> listarProduto() {
        String query = "SELECT * FROM Produto";
        ArrayList<Produto> lista = new ArrayList<>();

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA); PreparedStatement comandoSQL = conexao.prepareStatement(query); ResultSet rs = comandoSQL.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto"));
                produto.setNome(rs.getString("nome"));
                produto.setAvaliacao(rs.getDouble("avaliacao"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setQuantidadeEmEstoque(rs.getInt("quantidadeEmEstoque"));
                produto.setCondicaoDoProduto(rs.getBoolean("condicaoDoProduto"));

                lista.add(produto);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, "Erro ao listar produtos", ex);
        }

        return lista;
    }

    public static Produto buscarProdutoPorId(int idProduto) {
        String query = "SELECT * FROM Produto WHERE idProduto = ?";
        Produto produtoEncontrado = null;

        try (Connection conexao = DriverManager.getConnection(URL, LOGIN, SENHA); PreparedStatement comandoSQL = conexao.prepareStatement(query)) {

            comandoSQL.setInt(1, idProduto);

            try (ResultSet rs = comandoSQL.executeQuery()) {
                if (rs.next()) {
                    produtoEncontrado = new Produto();
                    produtoEncontrado.setIdProduto(rs.getInt("idProduto"));
                    produtoEncontrado.setNome(rs.getString("nome"));
                    produtoEncontrado.setAvaliacao(rs.getDouble("avaliacao"));
                    produtoEncontrado.setDescricao(rs.getString("descricao"));
                    produtoEncontrado.setPreco(rs.getDouble("preco"));
                    produtoEncontrado.setQuantidadeEmEstoque(rs.getInt("quantidadeEmEstoque"));
                    produtoEncontrado.setCondicaoDoProduto(rs.getBoolean("condicaoDoProduto"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, "Erro ao buscar produto por ID", ex);
        }

        return produtoEncontrado;
    }

    public static boolean atualizarQuantidadeEstoque(Produto produto) {
        String sql = "UPDATE Produto SET quantidadeEmEstoque = ? WHERE idProduto = ?";

        // Utilizando DriverManager para obter a conexão
        try (Connection conn = DriverManager.getConnection(URL, LOGIN, SENHA); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, produto.getQuantidadeEmEstoque());
            stmt.setInt(2, produto.getIdProduto());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
