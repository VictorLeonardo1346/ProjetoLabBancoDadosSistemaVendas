package sistemavendas.dao;

import sistemavendas.beans.Cliente;
import sistemavendas.beans.NotaFiscal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaFiscalDAO {
    private Connection conn;

    public NotaFiscalDAO(Connection conn) {
        this.conn = conn;
    }

    // Inserir nota fiscal
    public int inserir(NotaFiscal nota) {
        String sql = "INSERT INTO nota_fiscal (codcliente, datavenda) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, nota.getCliente().getCodcliente());
            stmt.setTimestamp(2, new Timestamp(nota.getDatavenda().getTime()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int codnota = rs.getInt(1);
                nota.setCodnota(codnota);
                return codnota;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao inserir nota fiscal: " + e.getMessage());
        }
        return 0;
    }

    // Listar todas as notas fiscais com o cliente associado
    public List<NotaFiscal> listar() {
        List<NotaFiscal> lista = new ArrayList<>();
        String sql = 
            "SELECT nf.codnota, nf.datavenda, " +
            "c.codcliente, c.nome " +
            "FROM nota_fiscal nf " +
            "INNER JOIN cliente c ON nf.codcliente = c.codcliente";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                NotaFiscal nf = new NotaFiscal();
                nf.setCodnota(rs.getInt("codnota"));
                nf.setDatavenda(rs.getTimestamp("datavenda"));

                Cliente c = new Cliente();
                c.setCodcliente(rs.getInt("codcliente"));
                c.setNome(rs.getString("nome"));

                nf.setCliente(c);
                lista.add(nf);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar notas fiscais: " + e.getMessage());
        }
        return lista;
    }

    // Buscar nota fiscal por código
    public NotaFiscal buscarPorId(int codnota) {
        String sql = 
            "SELECT nf.codnota, nf.datavenda, " +
            "c.codcliente, c.nome " +
            "FROM nota_fiscal nf " +
            "INNER JOIN cliente c ON nf.codcliente = c.codcliente " +
            "WHERE nf.codnota = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codnota);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                NotaFiscal nf = new NotaFiscal();
                nf.setCodnota(rs.getInt("codnota"));
                nf.setDatavenda(rs.getTimestamp("datavenda"));

                Cliente c = new Cliente();
                c.setCodcliente(rs.getInt("codcliente"));
                c.setNome(rs.getString("nome"));

                nf.setCliente(c);
                return nf;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar nota fiscal: " + e.getMessage());
        }
        return null;
    }

    // Excluir nota fiscal
    public boolean excluir(int codnota) {
        String sql = "DELETE FROM nota_fiscal WHERE codnota = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codnota);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir nota fiscal: " + e.getMessage());
            return false;
        }
    }
}
