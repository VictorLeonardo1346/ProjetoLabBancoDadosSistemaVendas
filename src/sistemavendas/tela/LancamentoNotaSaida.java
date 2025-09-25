package sistemavendas.tela;

import sistemavendas.beans.Cliente;
import sistemavendas.beans.Produto;
import sistemavendas.beans.NotaFiscal;
import sistemavendas.beans.ProdutoNota;
import sistemavendas.dao.ClienteDAO;
import sistemavendas.dao.ProdutoDAO;
import sistemavendas.dao.NotaFiscalDAO;
import sistemavendas.dao.ProdutoNotaDAO;
import sistemavendas.conexao.Conexao;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LancamentoNotaSaida extends javax.swing.JFrame {

    private Connection conn;
    private ClienteDAO clienteDAO;
    private ProdutoDAO produtoDAO;
    private NotaFiscalDAO notaFiscalDAO;
    private ProdutoNotaDAO produtoNotaDAO;
    private List<ProdutoNota> itensNota;

    public LancamentoNotaSaida() {
        initComponents();

        conn = Conexao.getConnection();
        clienteDAO = new ClienteDAO(conn);
        produtoDAO = new ProdutoDAO(conn);
        notaFiscalDAO = new NotaFiscalDAO(conn);
        produtoNotaDAO = new ProdutoNotaDAO(conn);
        itensNota = new ArrayList<>();

        carregarClientes();
        carregarProdutos();

        btnAdicionar.addActionListener(evt -> adicionarProduto());
        btnSalvar.addActionListener(evt -> salvarNota());
        btnLimpar.addActionListener(evt -> excluirNota());
        btnListar.addActionListener(evt -> listarNotas());
    }

    private void carregarClientes() {
        comboCliente.removeAllItems();
        List<Cliente> clientes = clienteDAO.listarTodos();
        if (clientes != null) {
            for (Cliente c : clientes) {
                comboCliente.addItem(c);
            }
        }
    }

    private void carregarProdutos() {
        comboProduto.removeAllItems();
        List<Produto> produtos = produtoDAO.listarTodos();
        if (produtos != null) {
            for (Produto p : produtos) {
                comboProduto.addItem(p);
            }
        }
    }

    private void adicionarProduto() {
        Produto p = (Produto) comboProduto.getSelectedItem();
        if (p == null) return;

        String quantidadeStr = txtEstoque.getText();
        if (quantidadeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a quantidade!");
            return;
        }

        int quantidade = Integer.parseInt(quantidadeStr);

        if (quantidade > p.getEstoque()) {
            JOptionPane.showMessageDialog(this, "Estoque insuficiente para o produto: " + p.getNome());
            return;
        }

        ProdutoNota pn = new ProdutoNota();
        pn.setProduto(p);
        pn.setQuantidade(quantidade);
        pn.setPrecoUnitario(p.getPreco());

        itensNota.add(pn);

        jTextArea1.append("Produto: " + p.getNome() +
                " | Quantidade: " + quantidade +
                " | Preço: " + p.getPreco() + "\n");
        txtEstoque.setText("");
    }

    private void salvarNota() {
        try {
            Cliente cliente = (Cliente) comboCliente.getSelectedItem();
            if (cliente == null) {
                JOptionPane.showMessageDialog(this, "Selecione um cliente!");
                return;
            }

            if (itensNota.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Adicione pelo menos um produto!");
                return;
            }

            // Validação de estoque antes de salvar
            for (ProdutoNota pn : itensNota) {
                Produto produto = pn.getProduto();
                if (pn.getQuantidade() > produto.getEstoque()) {
                    JOptionPane.showMessageDialog(this, "Estoque insuficiente para o produto: " + produto.getNome());
                    return; // cancela o salvamento
                }
            }

            NotaFiscal nota = new NotaFiscal();
            nota.setCliente(cliente);
            nota.setDatavenda(new Date());

            int codNota = notaFiscalDAO.inserir(nota); // insere a nota

            for (ProdutoNota pn : itensNota) {
                pn.setNota(nota); // vincula a nota
                produtoNotaDAO.inserir(pn); // registra item da nota -> trigger atualiza estoque automaticamente
            }

            JOptionPane.showMessageDialog(this, "Nota salva com sucesso!");
            jTextArea1.setText("");
            itensNota.clear();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    private void excluirNota() {
        String codNotaStr = JOptionPane.showInputDialog(this, "Digite o código da nota a excluir:");
        if (codNotaStr == null || codNotaStr.trim().isEmpty()) return;

        try {
            int codNota = Integer.parseInt(codNotaStr);
            produtoNotaDAO.excluirPorNota(codNota);
            notaFiscalDAO.excluir(codNota);

            jTextArea1.setText("");
            txtEstoque.setText("");
            itensNota.clear();

            JOptionPane.showMessageDialog(this, "Nota excluída com sucesso!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir: " + ex.getMessage());
        }
    }

    private void listarNotas() {
        List<NotaFiscal> notas = notaFiscalDAO.listar();
        if (notas == null || notas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há notas cadastradas!");
            return;
        }

        StringBuilder sb = new StringBuilder("Notas cadastradas:\n");

        for (NotaFiscal n : notas) {
            if (n == null) continue;
            String nomeCliente = (n.getCliente() != null) ? n.getCliente().getNome() : "Cliente não informado";

            sb.append("Código: ").append(n.getCodnota())
              .append(" | Cliente: ").append(nomeCliente)
              .append(" | Data: ").append(n.getDatavenda())
              .append("\n");

            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = conn.createStatement();
                String sqlItens = "SELECT p.nome, pn.quantidade, pn.preco_unitario " +
                        "FROM produto_nota pn " +
                        "INNER JOIN produto p ON pn.codproduto = p.codproduto " +
                        "WHERE pn.codnota = " + n.getCodnota();

                rs = stmt.executeQuery(sqlItens);
                while (rs.next()) {
                    sb.append("   Produto: ").append(rs.getString("nome"))
                      .append(" | Quantidade: ").append(rs.getInt("quantidade"))
                      .append(" | Preço Unitário: ").append(rs.getDouble("preco_unitario"))
                      .append("\n");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao listar produtos da nota: " + e.getMessage());
            } finally {
                try { if (rs != null) rs.close(); } catch (SQLException ex) { ex.printStackTrace(); }
                try { if (stmt != null) stmt.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }

        JOptionPane.showMessageDialog(this, sb.toString());
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox<>();
        TitleText = new javax.swing.JLabel();
        labelCliente = new javax.swing.JLabel();
        labelProduto = new javax.swing.JLabel();
        labelEstoque = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        txtEstoque = new javax.swing.JTextField();
        comboCliente = new javax.swing.JComboBox();
        comboProduto = new javax.swing.JComboBox();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TitleText.setText("Lançamento de Nota e Saída");

        labelCliente.setText("Cliente:");

        labelProduto.setText("Produto:");

        labelEstoque.setText("Quantidade em estoque:");

        btnAdicionar.setText("Adicionar Produto");

        btnSalvar.setText("Salvar Nota");

        btnLimpar.setText("Limpar");

        btnListar.setText("Listar Notas");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        comboCliente.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClienteActionPerformed(evt);
            }
        });

        comboProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(labelProduto)
                                    .addComponent(labelCliente))
                                .addGap(51, 51, 51)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TitleText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(comboProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSalvar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnLimpar)
                                .addGap(18, 18, 18)
                                .addComponent(btnListar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelEstoque)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAdicionar))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCliente)
                    .addComponent(comboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelProduto)
                    .addComponent(comboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEstoque)
                    .addComponent(btnAdicionar)
                    .addComponent(txtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnLimpar)
                    .addComponent(btnListar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void comboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboClienteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TitleText;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox comboCliente;
    private javax.swing.JComboBox comboProduto;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel labelCliente;
    private javax.swing.JLabel labelEstoque;
    private javax.swing.JLabel labelProduto;
    private javax.swing.JTextField txtEstoque;
    // End of variables declaration//GEN-END:variables
}
