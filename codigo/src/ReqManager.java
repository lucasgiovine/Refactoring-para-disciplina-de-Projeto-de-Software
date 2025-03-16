import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class ReqManager {
    
    private static ReqManager instancia;
    private ArrayList<Requisicao> requisicoes = new ArrayList<>();
    private static MesaManager mesaInstancia;
    private static FilaManager filaInstancia;
    private static Scanner scanner = new Scanner(System.in); // Scanner único

    // GET INSTANCIA (Singleton)
    public static ReqManager getInstance() {
        if (instancia == null) {
            instancia = new ReqManager();
            mesaInstancia = MesaManager.getInstance();
            filaInstancia = FilaManager.getInstance();
        }
        return instancia;
    }

    // PRINT REQUISIÇÃO
    public void imprimeRequisicao(Requisicao r) {
        System.out.println("Informações da requisição:\n");
        System.out.println("Requisição no nome de: " + r.getCliente().getNome());
        System.out.println("Identificador da requisição: " + r.getIdRequisicao() + "\n");
    }

    // FAZER REQUISIÇÃO
    public void fazerRequisicao(LocalTime entrada) {
        System.out.println("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        System.out.println("Digite a quantidade de pessoas no total: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida! Digite um número válido.");
            scanner.next();
        }
        int qntPessoas = scanner.nextInt();
        scanner.nextLine(); 

        Cliente cliente = new Cliente(nomeCliente, qntPessoas);
        Requisicao r = new Requisicao(cliente, entrada);
        System.out.println("Requisição feita!");
        requisicoes.add(r);
        imprimeRequisicao(r);

        r.setMesa(mesaInstancia.encontrarMesa(r));
        if (r.getMesa() == null) {
            if (r.getConvidados() > 8) {
                System.out.println("Não temos mesa para mais de 8 pessoas, desculpe.");
            } else {
                filaInstancia.colocaFilaDeEspera(r);
            }
        }
    }

    // VERIFICA SE EXISTE REQUISIÇÃO ATIVA
    public boolean verificaReqAtiva() {
        return requisicoes.stream().anyMatch(Requisicao::getStatus);
    }

    // IMPRIME REQUISIÇÕES ATIVAS
    public void imprimeReqsAtivas() {
        requisicoes.stream()
            .filter(Requisicao::getStatus)
            .forEach(req -> System.out.println("Mesa " + req.getMesa().getIdMesa() +
                    " - requisição no nome de " + req.getCliente().getNome() + " (" + req.getIdRequisicao() + ") "));
    }

    // RETORNA REQUISIÇÃO DA MESA
    public Requisicao reqDaMesa(Mesa m) {
        return requisicoes.stream()
                .filter(r -> r.getMesa().getIdMesa() == m.getIdMesa())
                .findFirst()
                .orElse(null);
    }

    // RETORNA POSIÇÃO DA REQUISIÇÃO PELO ID
    public int posReq(int id) {
        for (int i = 0; i < requisicoes.size(); i++) {
            if (requisicoes.get(i).getIdRequisicao() == id) {
                return i;
            }
        }
        return -1;
    }

    // OBTÉM REQUISIÇÃO PELO ID
    public Requisicao getReqById(int pos) {
        return (pos >= 0 && pos < requisicoes.size()) ? requisicoes.get(pos) : null;
    }

    // FINALIZA PEDIDO
    public void fecharPedido() {
        if (verificaReqAtiva()) {
            System.out.println("Informe o identificador da requisição para finalizar o pedido:");
            imprimeReqsAtivas();

            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Digite um número válido.");
                scanner.next();
            }
            int idReq = scanner.nextInt();
            scanner.nextLine(); 

            for (Requisicao requisicao : requisicoes) {
                if (idReq == requisicao.getIdRequisicao()) {
                    if (requisicao.getPedido().getStatus()) {
                        System.out.println("O valor a pagar é: " + requisicao.getPedido().calculaTotal());
                        System.out.println("Valor por pessoa: " + requisicao.getPedido().calculaDividido(requisicao));
                        requisicao.getPedido().finaliza();
                    } else {
                        System.out.println("Pedido não está ativo.");
                    }
                    return;
                }
            }
            System.out.println("Requisição não encontrada.");
        } else {
            System.out.println("Não existem requisições ativas no momento.");
        }
    }

    // CONSULTAR PEDIDO
    public void consultarPedido() {
        if (verificaReqAtiva()) {
            System.out.println("Informe o identificador da requisição para consultar o pedido:");
            imprimeReqsAtivas();

            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Digite um número válido.");
                scanner.next();
            }
            int idReq = scanner.nextInt();
            scanner.nextLine(); 
            int pos = posReq(idReq);
            if (pos != -1) {
                requisicoes.get(pos).imprimePedido();
            } else {
                System.out.println("Requisição não encontrada.");
            }
        } else {
            System.out.println("Não existem requisições ativas no momento.");
        }
    }

    // FECHA O SCANNER (SOMENTE NO FINAL DO PROGRAMA)
    public static void fecharScanner() {
        scanner.close();
    }
}
