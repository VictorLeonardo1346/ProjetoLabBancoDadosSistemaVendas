package sistemavendas.tela;

import sistemavendas.beans.Cliente;
import sistemavendas.dao.ClienteDAO;
import sistemavendas.conexao.Conexao;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class CadastroCliente extends javax.swing.JFrame {

    private final Connection conn;
    private final ClienteDAO clienteDAO;

   
    public CadastroCliente() {
        initComponents();

        // Inicializa conexão e DAO
        conn = Conexao.getConnection();
        clienteDAO = new ClienteDAO(conn);

        // Configura listeners (somente aqui, sem duplicidade)
        btnSalvar.addActionListener(evt -> salvarCliente());
        btnListar.addActionListener(evt -> listarClientes());
        btnLimpar.addActionListener(evt -> excluirCliente());
    }

        // Métodos
    private void salvarCliente() {
        String nome = txtNome.getText();
        String endereco = txtEndereco.getText();
        String email = txtEmail.getText();
        String telefone = txtTelefone.getText();

        if (nome.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e Email são obrigatórios!");
            return;
        }

        Cliente c = new Cliente();
        c.setNome(nome);
        c.setEndereco(endereco);
        c.setEmail(email);
        c.setTelefone(telefone);

        if (clienteDAO.inserir(c)) {
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso! Código: " + c.getCodcliente());
            listarClientes(); // Atualiza a lista automaticamente
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente!");
        }
    }

    private void listarClientes() {
        List<Cliente> clientes = clienteDAO.listarTodos();
        textArea.setText("");
        for (Cliente c : clientes) {
            textArea.append("Código: " + c.getCodcliente() +
                    " | Nome: " + c.getNome() +
                    " | Endereço: " + c.getEndereco() +
                    " | Email: " + c.getEmail() +
                    " | Telefone: " + c.getTelefone() + "\n");
        }
    }

    private void excluirCliente() {
        String codigoStr = JOptionPane.showInputDialog(this, "Digite o código do cliente a ser excluído:");
        if (codigoStr == null || codigoStr.isEmpty()) return;

        try {
            int codigo = Integer.parseInt(codigoStr);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Deseja realmente excluir o cliente de código " + codigo + "?",
                    "Confirmação", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (clienteDAO.excluir(codigo)) {
                    JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
                    listarClientes();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir cliente!");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código inválido!");
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblEndereco = new javax.swing.JLabel();
        txtEndereco = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        btnLimpar = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        lblTitle = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblNome.setText("Nome");

        txtNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeActionPerformed(evt);
            }
        });

        lblEmail.setText("Email");

        lblEndereco.setText("Endereço");

        lblTelefone.setText("Telefone");

        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        btnLimpar.setText("Limpar");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnListar.setText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        lblTitle.setText("Cadastro de Clientes");

        btnSalvar.setText("Salvar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTelefone)
                                .addGap(24, 24, 24))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEndereco)
                                    .addComponent(lblNome)
                                    .addComponent(lblEmail))
                                .addGap(21, 21, 21)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNome)
                            .addComponent(txtEndereco)
                            .addComponent(txtTelefone)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 109, 109))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(btnSalvar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnLimpar)
                                    .addGap(28, 28, 28)
                                    .addComponent(btnListar))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEndereco))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefone)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLimpar)
                    .addComponent(btnSalvar)
                    .addComponent(btnListar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        List<Cliente> clientes = clienteDAO.listarTodos();
        textArea.setText("");
        for (Cliente c : clientes) {
            textArea.append("Código: " + c.getCodcliente() +
                            " | Nome: " + c.getNome() +
                            " | Endereço: " + c.getEndereco() +
                            " | Email: " + c.getEmail() +
                            " | Telefone: " + c.getTelefone() + "\n");
        }
    }//GEN-LAST:event_btnListarActionPerformed

    private void txtNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeActionPerformed
        txtEmail.requestFocus();
    }//GEN-LAST:event_txtNomeActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // Abre uma janela pedindo o código do cliente para fazer a exclusão
    String codigoStr = JOptionPane.showInputDialog(this, "Digite o código do cliente a ser excluído:");

    if (codigoStr == null || codigoStr.isEmpty()) {
        return; // usuário cancelou ou não digitou nada
    }

    try {
        int codigo = Integer.parseInt(codigoStr);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Deseja realmente excluir o cliente de código " + codigo + "?",
            "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            clienteDAO.excluir(codigo); // chama método para remover do banco
            JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
            btnListar.doClick(); // atualiza a lista
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Código inválido!");
    }
    }//GEN-LAST:event_btnLimparActionPerformed

    
    
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblEndereco;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextArea textArea;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEndereco;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
