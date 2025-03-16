import java.time.LocalTime;

public class Requisicao {
    
    private static int contadorRequisicao = 0;
    private int idRequisicao;
    private Cliente cliente;
    private Mesa mesa;
    private Pedido pedido;
    private boolean status;
    private LocalTime entrada;
    private LocalTime saida;

    public Requisicao(Cliente cliente, LocalTime entrada) {
        this.idRequisicao = ++contadorRequisicao;
        this.cliente = cliente;
        this.status = false;
        this.pedido = new Pedido();
        this.entrada = entrada;
    }

    public boolean getStatus() {
        return (this.status);
    }

    public void imprimePedido() {

        System.out.println("---------");
        System.out.println("Pedido de : " + this.getCliente().getNome() + " (ID da requisição - " + this.idRequisicao + " )");
        for (ItemProduto item : this.pedido.getItens()) {
            System.out.println(this.pedido.getItens().indexOf(item) + 1 + " - " + item.getProduto().getNome() + " R$"
                    + item.getProduto().getPreço() + " (" + item.getProduto().getIdProduto() + ")" + " quantidade - "
                    + item.getQnt() + " - valor total: " + item.getValorTotal()); 
        }
        System.out.println("---------");
    }

    public void reqAtiva() {
        this.status = true;
    }

    public int getConvidados() {
        int convidados = this.cliente.getQtdPessoas();
        return convidados;
    }

    public int getIdRequisicao() {
        return idRequisicao;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setIdRequisicao(int idRequisicao) {
        this.idRequisicao = idRequisicao;
    }

    public void reqInativa() {
        this.status = false;
    }

    public void setMesa(Mesa m) {
        this.mesa = m;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

    public LocalTime getEntrada() {
        return this.entrada;
    }
}