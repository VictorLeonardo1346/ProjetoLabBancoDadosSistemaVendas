package sistemavendas.dao;

import sistemavendas.beans.ProdutoNota;
import java.sql.*;

public class ProdutoNotaDAO {
    private final Connection conn;

    public ProdutoNotaDAO(Connection conn) {
        this.conn = conn;
    }

    // Insere um item (produto) na nota
    public boolean inserir(ProdutoNota pn) {
        String sql = "INSERT INTO produto_nota (codnota, codproduto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pn.getNota().getCodnota());   // nota já deve ter codnota gerado
            stmt.setInt(2, pn.getProduto().getCodproduto());
            stmt.setInt(3, pn.getQuantidade());
            stmt.setDouble(4, pn.getPrecoUnitario());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao inserir produto_nota: " + e.getMessage());
            return false;
        }
    }

    // Exclui todos os itens de uma nota
    public int excluirPorNota(int codNota) {
        String sql = "DELETE FROM produto_nota WHERE codnota = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codNota);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao excluir itens da nota: " + e.getMessage());
            return 0;
        }
    }
}
