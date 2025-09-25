import sistemavendas.beans.Cliente;
import sistemavendas.beans.Produto;
import sistemavendas.beans.NotaFiscal;
import sistemavendas.beans.ProdutoNota;

import sistemavendas.dao.ClienteDAO;
import sistemavendas.dao.ProdutoDAO;
import sistemavendas.dao.NotaFiscalDAO;
import sistemavendas.dao.ProdutoNotaDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class sistemavendas {

    public static void main(String[] args) {

        // -------- CONEXÃO --------
        String url = "jdbc:mysql://localhost:3306/sistemavendas?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "1234"; // coloque a senha do MySQL que você configurou

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Conexão realizada com sucesso!");

            // -------- DAOs --------
            ClienteDAO clienteDAO = new ClienteDAO(conn);
            ProdutoDAO produtoDAO = new ProdutoDAO(conn);
            NotaFiscalDAO notaDAO = new NotaFiscalDAO(conn);
            ProdutoNotaDAO produtoNotaDAO = new ProdutoNotaDAO(conn);

            // -------- CLIENTE --------
            int codCliente = 1; // exemplo: cliente já cadastrado
            Cliente cliente = clienteDAO.buscarPorCodigo(codCliente);
            if (cliente == null) {
                System.out.println("Cliente com código " + codCliente + " não encontrado!");
                return;
            }
            System.out.println("Cliente encontrado: " + cliente.getNome());

            // -------- PRODUTO --------
            int codProduto = 1; // exemplo: produto já cadastrado
            Produto produto = produtoDAO.buscarPorCodigo(codProduto);
            if (produto == null) {
                System.out.println("Produto com código " + codProduto + " não encontrado!");
                return;
            }
            System.out.println("Produto encontrado: " + produto.getNome());

            // -------- NOTA FISCAL --------
            NotaFiscal nota = new NotaFiscal();
            nota.setCliente(cliente);    // associa cliente
            nota.setDatavenda(new Date());

            int codNotaGerada = notaDAO.inserir(nota); // método inserir deve retornar o ID gerado
            if (codNotaGerada == 0) {
                System.out.println("Erro ao criar nota fiscal!");
                return;
            }
            nota.setCodnota(codNotaGerada);
            System.out.println("Nota fiscal criada com sucesso. Codnota: " + codNotaGerada);

            // -------- PRODUTO NA NOTA --------
            ProdutoNota pn = new ProdutoNota();
            pn.setNota(nota);
            pn.setProduto(produto);
            pn.setQuantidade(2); // exemplo: 2 unidades
            pn.setPrecoUnitario(produto.getPreco()); // ⚠️ importante para o banco

            boolean inserido = produtoNotaDAO.inserir(pn);
            if (inserido) {
                System.out.println("Produto inserido na nota com sucesso!");
            } else {
                System.out.println("Erro ao inserir produto na nota.");
            }

        } catch (SQLException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }
}
