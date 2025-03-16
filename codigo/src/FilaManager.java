import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FilaManager {
    
    private static FilaManager instancia;
    private ArrayList<Requisicao> filaEspera = new ArrayList<>();
    private MesaManager mesaInstancia;
    private static Scanner scanner = new Scanner(System.in); 

    // GET INSTANCIA
    public static FilaManager getInstance(){
        if (instancia == null) {
            instancia = new FilaManager();
        }
        return instancia;
    }

    // COLOCA FILA DE ESPERA
    public void colocaFilaDeEspera(Requisicao r) {
        filaEspera.add(r);
        System.out.println("Não há mesas disponíveis, você entrou na fila de espera.");
    }

    // TIRA FILA DE ESPERA
    public void tiraFilaDeEspera(Requisicao r) {
        filaEspera.remove(r);
    }

    // VERIFICAR FILA DE ESPERA
    public void verificaFilaDeEspera() {
        for (Requisicao r : filaEspera) {
            if (r.getMesa() == null) {
                r.setMesa(mesaInstancia.encontrarMesa(r));
                if (r.getMesa() != null) {
                    filaEspera.remove(r);
                }
            }
        }
    }

    // VERIFICAR FILA
    public void verificarFila(Mesa m) {
        Iterator<Requisicao> iterator = filaEspera.iterator();
        while (iterator.hasNext()) {
            Requisicao r = iterator.next();
            if (m.getCapacidade() >= r.getConvidados() && !m.getStatus()) {
                m.ocuparMesa();
                iterator.remove();
                System.out.println("Mesa ocupada pela fila de espera, Cliente: " + r.getCliente().getNome());
            }
        }
    }

    // MOSTRA ESTATÍSTICAS DA FILA
    public void statsFila() {
        if (filaEspera.isEmpty()) {
            System.out.println("A fila de espera está vazia!");
            return;
        }
        System.out.println("A fila de espera possui " + filaEspera.size() + " clientes: ");
        int posicao = 1;
        for (Requisicao r : filaEspera) {
            System.out.println(posicao + ". " + r.getCliente().getNome() + " (Id-" + r.getIdRequisicao() + ")");
            posicao++;
        }
    }

    // CANCELAR REQUISIÇÃO DA FILA DE ESPERA
    public void cancelarRequisicao() {
        if (filaEspera.isEmpty()) {
            System.out.println("A fila de espera está vazia!");
            return;
        }

        System.out.println("Informe o identificador da requisição que deseja cancelar: ");
        if (scanner.hasNextInt()) {
            int idCancela = scanner.nextInt();
            System.out.println("Verificando...\n");

            Iterator<Requisicao> iterator = filaEspera.iterator();
            boolean cancelado = false;
            while (iterator.hasNext()) {
                Requisicao r = iterator.next();
                if (r.getIdRequisicao() == idCancela) {
                    iterator.remove();
                    cancelado = true;
                    System.out.println("Requisição de id " + r.getIdRequisicao() + " cancelada.");
                    break;
                }
            }
            if (!cancelado) {
                System.out.println("Requisição de id " + idCancela + " não está na fila de espera.");
            }
        } else {
            System.out.println("Opção inválida. Tente novamente.");
            scanner.next(); 
        }
    }

    public static void fecharScanner() {
        scanner.close();
    }
}
