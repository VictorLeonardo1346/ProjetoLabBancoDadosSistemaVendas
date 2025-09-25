package sistemavendas.tela;

import sistemavendas.beans.Cliente;
import sistemavendas.beans.Produto;
import sistemavendas.beans.NotaFiscal;
import sistemavendas.dao.ClienteDAO;
import sistemavendas.dao.ProdutoDAO;
import sistemavendas.dao.NotaFiscalDAO;
import sistemavendas.conexao.Conexao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.List;

public class MenuPrincipal extends javax.swing.JFrame {

    private Connection conn;
    private ClienteDAO clienteDAO;
    private ProdutoDAO produtoDAO;
    private NotaFiscalDAO notaFiscalDAO;

    public MenuPrincipal() {
        initComponents();

        // Inicia a conexão e DAOs
        conn = Conexao.getConnection();
        clienteDAO = new ClienteDAO(conn);
        produtoDAO = new ProdutoDAO(conn);
        notaFiscalDAO = new NotaFiscalDAO(conn);

        // Configura menus
        menuSair.addActionListener((ActionEvent e) -> System.exit(0));

        menuCadastroCliente.addActionListener(e -> new CadastroCliente().setVisible(true));
        menuCadastroProduto.addActionListener(e -> new CadastroProduto().setVisible(true));
        menuCadastroNota.addActionListener(e -> new LancamentoNotaSaida().setVisible(true));

        menuRelatorioClientes.addActionListener(e -> mostrarRelatorioClientes());
        menuRelatorioProdutos.addActionListener(e -> mostrarRelatorioProdutos());
        menuRelatorioNotas.addActionListener(e -> mostrarRelatorioNotas());
    }

    private void mostrarRelatorioClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado!");
            return;
        }
        StringBuilder sb = new StringBuilder("Relatório de Clientes:\n");
        for (Cliente c : clientes) {
            sb.append("Código: ").append(c.getCodcliente())
              .append(" | Nome: ").append(c.getNome())
              .append(" | Email: ").append(c.getEmail()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void mostrarRelatorioProdutos() {
        List<Produto> produtos = produtoDAO.listarTodos();
        if (produtos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum produto encontrado!");
            return;
        }
        StringBuilder sb = new StringBuilder("Relatório de Produtos:\n");
        for (Produto p : produtos) {
            sb.append("Código: ").append(p.getCodproduto())
              .append(" | Nome: ").append(p.getNome())
              .append(" | Preço: ").append(p.getPreco())
              .append(" | Estoque: ").append(p.getEstoque()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void mostrarRelatorioNotas() {
        List<NotaFiscal> notas = notaFiscalDAO.listar();
        if (notas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhuma nota encontrada!");
            return;
        }
        StringBuilder sb = new StringBuilder("Relatório de Notas:\n");
        for (NotaFiscal n : notas) {
            sb.append("Código: ").append(n.getCodnota())
              .append(" | Data: ").append(n.getDatavenda()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString());
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JmenuBar1 = new javax.swing.JMenuBar();
        menuArquivo = new javax.swing.JMenu();
        menuSair = new javax.swing.JMenuItem();
        menuCadastro = new javax.swing.JMenu();
        menuCadastroCliente = new javax.swing.JMenuItem();
        menuCadastroProduto = new javax.swing.JMenuItem();
        menuCadastroNota = new javax.swing.JMenuItem();
        menuRelatorios = new javax.swing.JMenu();
        menuRelatorioClientes = new javax.swing.JMenuItem();
        menuRelatorioProdutos = new javax.swing.JMenuItem();
        menuRelatorioNotas = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        menuArquivo.setText("Arquivo");

        menuSair.setText("Sair");
        menuArquivo.add(menuSair);

        JmenuBar1.add(menuArquivo);

        menuCadastro.setText("Cadastro");

        menuCadastroCliente.setText("Cliente");
        menuCadastro.add(menuCadastroCliente);

        menuCadastroProduto.setText("Produto");
        menuCadastro.add(menuCadastroProduto);

        menuCadastroNota.setText("Nota de Saída");
        menuCadastroNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCadastroNotaActionPerformed(evt);
            }
        });
        menuCadastro.add(menuCadastroNota);

        JmenuBar1.add(menuCadastro);

        menuRelatorios.setText("Relatórios");

        menuRelatorioClientes.setText("Clientes");
        menuRelatorioClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuRelatorioClientesActionPerformed(evt);
            }
        });
        menuRelatorios.add(menuRelatorioClientes);

        menuRelatorioProdutos.setText("Produtos");
        menuRelatorios.add(menuRelatorioProdutos);

        menuRelatorioNotas.setText("Notas");
        menuRelatorios.add(menuRelatorioNotas);

        JmenuBar1.add(menuRelatorios);

        setJMenuBar(JmenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCadastroNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCadastroNotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuCadastroNotaActionPerformed

    private void menuRelatorioClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuRelatorioClientesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_menuRelatorioClientesActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar JmenuBar1;
    private javax.swing.JMenu menuArquivo;
    private javax.swing.JMenu menuCadastro;
    private javax.swing.JMenuItem menuCadastroCliente;
    private javax.swing.JMenuItem menuCadastroNota;
    private javax.swing.JMenuItem menuCadastroProduto;
    private javax.swing.JMenuItem menuRelatorioClientes;
    private javax.swing.JMenuItem menuRelatorioNotas;
    private javax.swing.JMenuItem menuRelatorioProdutos;
    private javax.swing.JMenu menuRelatorios;
    private javax.swing.JMenuItem menuSair;
    // End of variables declaration//GEN-END:variables
}
