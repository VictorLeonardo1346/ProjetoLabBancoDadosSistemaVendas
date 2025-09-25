package sistemavendas.beans;

public class Produto {

    private int codproduto;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;


    // GETTERS E SETTERS 

    // ========================= GETTERS E SETTERS =========================

    public int getCodproduto() {
        return codproduto;
    }

    public void setCodproduto(int codproduto) {
        this.codproduto = codproduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    
    
    // Método para mostar o codigo do produto e o nome do produto
    @Override
    public String toString() {
        return codproduto + " - " + nome;
    }
}
