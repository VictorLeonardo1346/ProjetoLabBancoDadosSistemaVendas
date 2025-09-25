package sistemavendas.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotaFiscal {
    private int codnota;
    private Cliente cliente;
    private Date datavenda;

    // Getters e Setters
    public int getCodnota() { 
        return codnota; 
    }
    
    public void setCodnota(int codnota) { 
        this.codnota = codnota; 
    }

    public Cliente getCliente() { 
        return cliente; 
    }
    
    public void setCliente(Cliente cliente) { 
        this.cliente = cliente;
    }

    public Date getDatavenda() { 
        return datavenda; 
    }
    
    public void setDatavenda(Date datavenda) { 
        this.datavenda = datavenda; 
    }

    public int getCodcliente() {
        return (cliente != null) ? cliente.getCodcliente() : 0;
    }

    // Método override para mostrar na lista o numero da nota, nome do cliente e data formatada
    @Override
    public String toString() {
        String nomeCliente = (cliente != null) ? cliente.getNome() : "Cliente não informado";
        String dataFormatada = (datavenda != null) ? 
                new SimpleDateFormat("dd/MM/yyyy HH:mm").format(datavenda) : "Data não informada";
        return "Nota " + codnota + " | Cliente: " + nomeCliente + " | Data: " + dataFormatada;
    }
}

