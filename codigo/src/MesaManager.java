import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class MesaManager {

    private static MesaManager instancia;
    private static ReqManager reqInstancia;
    private static FilaManager filaInstancia;
    private static ProdutoManager cardapioInstancia;
    private ArrayList<Mesa> mesas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in); // Scanner único

    // CONSTRUTOR
    private MesaManager() {
        // Inicializando as mesas
        mesas.add(new Mesa(4)); // Mesa de 4 lugares
        mesas.add(new Mesa(4));
        mesas.add(new Mesa(6)); // Mesa de 6 lugares
        mesas.add(new Mesa(6));
        mesas.add(new Mesa(8)); // Mesa de 8 lugares
        mesas.add(new Mesa(8));
    }

    // MÉTODO PARA OBTER A INSTÂNCIA
    public static MesaManager getInstance() {
        if (instancia == null) {
            instancia = new MesaManager();
            reqInstancia = ReqManager.getInstance();
            filaInstancia = FilaManager.getInstance();
            cardapioInstancia = ProdutoManager.getInstance();
        }
        return instancia;
    }

    // ENCONTRAR MESA
    public Mesa encontrarMesa(Requisicao r) {
        int convidados = r.getConvidados();
        for (Mesa mesa : mesas) {
            if (convidados <= mesa.getCapacidade() && !mesa.getStatus()) {
                mesa.ocuparMesa();
                r.reqAtiva();
                return mesa;
            } else if (convidados > 8) {
                return null;
            }
        }
        return null;
    }

    // SAIR DA MESA
    public void sairDaMesa(LocalTime saida) {
        if (!reqInstancia.verificaReqAtiva()) {
            System.out.println("Não existem mesas ocupadas no momento.");
            return;
        }

        System.out.println("Informe o número da mesa que deseja sair:");
        reqInstancia.imprimeReqsAtivas();

        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            boolean mesaEncontrada = false;

            for (Mesa mesa : mesas) {
                if (mesa.getIdMesa() == id && mesa.getStatus()) {
                    if (!reqInstancia.reqDaMesa(mesa).getPedido().getStatus()) {
                        mesa.desocuparMesa();
                        reqInstancia.reqDaMesa(mesa).reqInativa();
                        LocalTime diftime = saida.minusNanos(reqInstancia.reqDaMesa(mesa).getEntrada().toNanoOfDay());
                        System.out.println("Tempo de permanência: " + diftime);
                        filaInstancia.verificarFila(mesa);
                        mesaEncontrada = true;
                    } else {
                        System.out.println("Não é possível sair da mesa sem pagar.");
                        mesaEncontrada = true;
                    }
                    break;
                }
            }

            if (!mesaEncontrada) {
                System.out.println("Mesa não encontrada ou não ocupada.");
            }
        } else {
            System.out.println("Opção inválida.");
            scanner.next(); 
        }
    }

    // FAZER PEDIDO
    public void fazerPedido() {
        if (!reqInstancia.verificaReqAtiva()) {
            System.out.println("Não existem requisições ativas no momento.");
            return;
        }

        System.out.println("Informe o identificador da requisição que deseja realizar um pedido:");
        reqInstancia.imprimeReqsAtivas();

        if (scanner.hasNextInt()) {
            int idReq = scanner.nextInt();
            if (reqInstancia.posReq(idReq) == -1 || !reqInstancia.getReqById(reqInstancia.posReq(idReq)).getStatus()) {
                System.out.println("Requisição não encontrada.");
                return;
            }

            System.out.println("Requisição de id " + idReq + " selecionada.");
            System.out.println("Informe o id do prato/produto que deseja pedir:");
            cardapioInstancia.imprimeCardapio();

            if (scanner.hasNextInt()) {
                int idProd = scanner.nextInt();
                if (cardapioInstancia.posProd(idProd) == -1) {
                    System.out.println("Produto não encontrado.");
                    return;
                }

                System.out.println("Produto de id " + idProd + " selecionado.");
                System.out.println("Informe quantos pratos/produtos deseja pedir:");

                if (scanner.hasNextInt()) {
                    int qntProd = scanner.nextInt();
                    if (qntProd > 0) {
                        System.out.println("Produto de id " + idProd + " selecionado " + qntProd + " vezes.");
                        cardapioInstancia.imprimeProd(idProd);
                        ItemProduto itemprod = new ItemProduto(cardapioInstancia.getProdById(cardapioInstancia.posProd(idProd)), qntProd);
                        reqInstancia.getReqById(reqInstancia.posReq(idReq)).getPedido().addItem(itemprod);
                        reqInstancia.getReqById(reqInstancia.posReq(idReq)).imprimePedido();
                        reqInstancia.getReqById(reqInstancia.posReq(idReq)).getPedido().ativaPedido();
                    } else {
                        System.out.println("Quantidade não é válida.");
                    }
                } else {
                    System.out.println("Opção inválida.");
                    scanner.next(); 
                }
            } else {
                System.out.println("Produto não encontrado.");
                scanner.next(); 
            }
        } else {
            System.out.println("Opção inválida.");
            scanner.next(); 
        }
    }

    public static void fecharScanner() {
        scanner.close();
    }
}
