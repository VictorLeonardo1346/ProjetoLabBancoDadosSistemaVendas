package sistemavendas.beans;

public class ProdutoNota {
    private NotaFiscal nota;
    private Produto produto;
    private int quantidade;
    private double precoUnitario;

    // Getters e Setters
    public NotaFiscal getNota() {
        return nota;
    }

    public void setNota(NotaFiscal nota) {
        this.nota = nota;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
    
    // Método para mostrar o nome do produto, a quantidade e o preço unitário
    @Override
    public String toString() {
        String produtoStr = (produto != null) ? produto.getNome() : "Produto não definido";
        return produtoStr + " | Qtde: " + quantidade + " | Preço: " + precoUnitario;
    }
}


