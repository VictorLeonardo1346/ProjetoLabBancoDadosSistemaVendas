package sistemavendas.dao;

import sistemavendas.beans.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private final Connection conn;

    public ClienteDAO(Connection conn) {
        this.conn = conn;
    }

    // Inserir cliente (retorna true se inserido com sucesso)
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, endereco, email, telefone) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getEndereco());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());

            int affected = stmt.executeUpdate();

            if (affected == 1) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    cliente.setCodcliente(rs.getInt(1)); // Atualiza ID do objeto
                }
                return true;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir cliente: " + e.getMessage());
        }
        return false;
    }

    // Buscar cliente por código
    public Cliente buscarPorCodigo(int codcliente) {
        String sql = "SELECT * FROM cliente WHERE codcliente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codcliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setCodcliente(rs.getInt("codcliente"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                return c;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage());
        }
        return null;
    }

    // Listar todos os clientes
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCodcliente(rs.getInt("codcliente"));
                c.setNome(rs.getString("nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setEmail(rs.getString("email"));
                c.setTelefone(rs.getString("telefone"));
                clientes.add(c);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar clientes: " + e.getMessage());
        }
        return clientes;
    }

    // Excluir cliente pelo código (retorna true se excluído com sucesso)
    public boolean excluir(int codcliente) {
        String sql = "DELETE FROM cliente WHERE codcliente = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, codcliente);
            int affected = stmt.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
            return false;
        }
    }
}

