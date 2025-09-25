package sistemavendas.dao;

import sistemavendas.beans.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    private final Connection conn;

    public ProdutoDAO(Connection conn) {
        this.conn = conn;
    }

    // ========================= INSERIR PRODUTO =========================
    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produto (nome, descricao, preco, estoque) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getEstoque());
            stmt.executeUpdate();

            // Obtendo o ID gerado
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                produto.setCodproduto(rs.getInt(1));
            }

            return true; // sucesso

        } catch (SQLException e) {
            System.out.println("Erro ao inserir produto: " + e.getMessage());
            return false; // falha
        }
    }

    // ========================= LISTAR TODOS OS PRODUTOS =========================
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setCodproduto(rs.getInt("codproduto"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setEstoque(rs.getInt("estoque"));
                produtos.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    // ========================= EXCLUIR PRODUTO =========================
    public boolean excluir(int codproduto) {
        String sql = "DELETE FROM produto WHERE codproduto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codproduto);
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
            return false;
        }
    }

    // ========================= BUSCAR PRODUTO POR CÓDIGO =========================
    public Produto buscarPorCodigo(int codproduto) {
        String sql = "SELECT * FROM produto WHERE codproduto = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codproduto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Produto p = new Produto();
                p.setCodproduto(rs.getInt("codproduto"));
                p.setNome(rs.getString("nome"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setEstoque(rs.getInt("estoque"));
                return p;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

}
