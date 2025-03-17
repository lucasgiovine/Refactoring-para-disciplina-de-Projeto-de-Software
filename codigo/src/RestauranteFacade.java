import java.time.LocalTime;

//Refactoring the project to follow the Facade architectural pattern.

public class RestauranteFacade {

    private final MesaManager mesaManager;
    private final ReqManager reqManager;
    private final FilaManager filaManager;
    private final ProdutoManager cardapio;
    private LocalTime entrada;
    private LocalTime saida;

    public RestauranteFacade() {
        this.mesaManager = MesaManager.getInstance();
        this.reqManager = ReqManager.getInstance();
        this.filaManager = FilaManager.getInstance();
        this.cardapio = ProdutoManager.getInstance();
    }

    // FAZER REQUISIÇÃO
    public void fazerRequisicao() {
        reqManager.fazerRequisicao(LocalTime.now());
    }

    // ADICIONA NA FILA DE ESPERA
    public void colocaFilaDeEspera(Requisicao requisicao) {
        filaManager.colocaFilaDeEspera(requisicao);
    }

    // REMOVE DA FILA DE ESPERA  
    public void tirarFilaDeEspera(Requisicao requisicao) {
        filaManager.tiraFilaDeEspera(requisicao);
    }

    // SAIR DA MESA
    public void sairDaMesa() {
        this.saida = LocalTime.now();
        mesaManager.sairDaMesa(this.saida);
    }

    // VERIFICAR FILA PARA UMA MESA
    public void verificarFila(Mesa mesa) {
        filaManager.verificarFila(mesa);
    }

    // EXIBIR ESTATÍSTICAS DA FILA
    public void statsFila() {
        filaManager.statsFila();
    }

    // CANCELA UMA REQUISIÇÃO DA FILA DE ESPERA
    public void cancelarRequisicao() {
        filaManager.cancelarRequisicao();
    }

    // IMPRIME REQUISIÇÃO
    public void imprimeRequisicao(Requisicao requisicao) {
        reqManager.imprimeRequisicao(requisicao);
    }

    // IMPRIME O CARDÁPIO
    public void imprimeCardapio() {
        cardapio.imprimeCardapio();
    }

    // OBTÉM REQUISIÇÃO DA MESA
    public Requisicao reqDaMesa(Mesa mesa) {
        return reqManager.reqDaMesa(mesa);
    }

    // FAZ UM PEDIDO PARA UMA MESA
    public void fazerPedido() {
        mesaManager.fazerPedido();
    }

    // RETORNA A POSIÇÃO DE UMA REQUISIÇÃO PELO ID
    public int posReq(int id) {
        return reqManager.posReq(id);
    }

    // RETORNA A POSIÇÃO DE UM PRODUTO PELO ID
    public int posProd(int id) {
        return cardapio.posProd(id);
    }

    // IMPRIME UM PRODUTO ESPECÍFICO PELO ID
    public void imprimeProd(int id) {
        cardapio.imprimeProd(id);
    }

    // IMPRIME TODAS AS REQUISIÇÕES ATIVAS
    public void imprimeReqsAtivas() {
        reqManager.imprimeReqsAtivas();
    }

    // VERIFICA SE EXISTE ALGUMA REQUISIÇÃO ATIVA
    public boolean verificaReqAtiva() {
        return reqManager.verificaReqAtiva();
    }

    // FINALIZA UM PEDIDO
    public void fecharPedido() {
        reqManager.fecharPedido();
    }

    // CONSULTA O PEDIDO DE UMA REQUISIÇÃO
    public void consultarPedido() {
        reqManager.consultarPedido();
    }

    public void fecharScanner() {
        ProdutoManager.fecharScanner();
        MesaManager.fecharScanner();
        ReqManager.fecharScanner();
        FilaManager.fecharScanner();
    }
}
