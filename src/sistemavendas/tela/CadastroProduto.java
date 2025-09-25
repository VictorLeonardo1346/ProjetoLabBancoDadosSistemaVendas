package sistemavendas.tela;

import sistemavendas.beans.Produto;
import sistemavendas.dao.ProdutoDAO;
import sistemavendas.conexao.Conexao;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class CadastroProduto extends javax.swing.JFrame {

    private final Connection conn;
    private final ProdutoDAO produtoDAO;

    public CadastroProduto() {
        initComponents(); 

        // Inicializa conexão e DAO
        conn = Conexao.getConnection();
        produtoDAO = new ProdutoDAO(conn);

        // Configura listeners
        btnSalvar.addActionListener(evt -> salvarProduto());
        btnListar.addActionListener(evt -> listarProdutos());
        btnLimpar.addActionListener(evt -> excluirProduto());
    }

        // Métodos
    private void salvarProduto() {
        String nome = txtNome.getText().trim();
        String descricao = txtDescricao.getText().trim();
        String precoStr = txtPreco.getText().trim();
        String estoqueStr = txtEstoque.getText().trim();

        // Validações
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome do produto é obrigatório!");
            return;
        }
        if (precoStr.isEmpty() || estoqueStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preço e Estoque são obrigatórios!");
            return;
        }

        double preco;
        int estoque;
        try {
            preco = Double.parseDouble(precoStr);
            estoque = Integer.parseInt(estoqueStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço e Estoque devem ser números válidos!");
            return;
        }

        Produto p = new Produto();
        p.setNome(nome);
        p.setDescricao(descricao);
        p.setPreco(preco);
        p.setEstoque(estoque);

        produtoDAO.inserir(p);
        JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso! Código: " + p.getCodproduto());
        listarProdutos(); // Atualiza a lista
    }

    private void listarProdutos() {
        List<Produto> produtos = produtoDAO.listarTodos();
        textArea.setText("");
        for (Produto p : produtos) {
            textArea.append("Código: " + p.getCodproduto() +
                    " | Nome: " + p.getNome() +
                    " | Descrição: " + p.getDescricao() +
                    " | Preço: " + p.getPreco() +
                    " | Estoque: " + p.getEstoque() + "\n");
        }
    }

    private void excluirProduto() {
        String codigoStr = JOptionPane.showInputDialog(this, "Digite o código do produto a ser excluído:");
        if (codigoStr == null || codigoStr.isEmpty()) return;

        try {
            int codigo = Integer.parseInt(codigoStr);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o produto de código " + codigo + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (produtoDAO.excluir(codigo)) {
                    JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                    listarProdutos();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir produto!");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código inválido!");
        }
    }


    //Botão Limpar (exclui os produtos) 
    private void btnLimparActionPerformed() {
        String codigoStr = JOptionPane.showInputDialog(this, "Digite o código do produto a ser excluído:");
        if (codigoStr == null || codigoStr.isEmpty()) return;

        try {
            int codigo = Integer.parseInt(codigoStr);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o produto de código " + codigo + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (produtoDAO.excluir(codigo)) {
                    JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!");
                    btnListar.doClick();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir produto!");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código inválido!");
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        TitleText = new javax.swing.JLabel();
        labelDescricao = new javax.swing.JLabel();
        txtDescricao = new javax.swing.JTextField();
        labelpreco = new javax.swing.JLabel();
        txtPreco = new javax.swing.JTextField();
        labelEstoque = new javax.swing.JLabel();
        txtEstoque = new javax.swing.JTextField();
        jScrollPane = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        btnSalvar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelNome.setText("Nome");

        TitleText.setText("Cadastro de Produtos");

        labelDescricao.setText("Descrição");

        labelpreco.setText("Preço");

        labelEstoque.setText("Quantidade");

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane.setViewportView(textArea);

        btnSalvar.setText("Salvar");

        btnListar.setText("Listar");

        btnLimpar.setText("Limpar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelDescricao)
                    .addComponent(labelNome)
                    .addComponent(labelpreco)
                    .addComponent(labelEstoque))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnListar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimpar))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addComponent(txtDescricao)
                        .addComponent(txtPreco)
                        .addComponent(txtEstoque)))
                .addContainerGap(103, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TitleText)
                .addGap(156, 156, 156))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TitleText, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDescricao)
                    .addComponent(txtDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelpreco)
                    .addComponent(txtPreco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnListar)
                    .addComponent(btnLimpar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */

        
        //</editor-fold>java.awt.EventQueue.invokeLater(() -> new CadastroProduto().setVisible(true));     
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TitleText;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel labelDescricao;
    private javax.swing.JLabel labelEstoque;
    private javax.swing.JLabel labelNome;
    private javax.swing.JLabel labelpreco;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField txtDescricao;
    private javax.swing.JTextField txtEstoque;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPreco;
    // End of variables declaration//GEN-END:variables
}
