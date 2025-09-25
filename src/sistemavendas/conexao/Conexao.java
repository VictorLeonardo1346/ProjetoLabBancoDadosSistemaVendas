package sistemavendas.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/sistemavendas";
    private static final String USER = "root";
    private static final String PASS = "";
 

    // Método estático para facilitar o uso nos DAOs
    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexão realizada com sucesso!");
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro na conexão: " + e.getMessage());
            return null;
        }
    }

    public Connection getConexao() {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}

