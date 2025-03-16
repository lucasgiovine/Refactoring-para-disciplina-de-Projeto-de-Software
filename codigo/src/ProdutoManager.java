import java.util.ArrayList;
import java.util.Scanner;

public class ProdutoManager {
    
    private static ProdutoManager instancia;
    private ArrayList<Produto> cardapio = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in); // Scanner único

    // CONSTRUTOR
    private ProdutoManager() {
        cardapio.add(new Produto("Moqueca de Tilápia", 25.0));
        cardapio.add(new Produto("Falafel Assado", 15.0));
        cardapio.add(new Produto("Salada Primavera com Macarrão Konjac", 18.0));
        cardapio.add(new Produto("Escondidinho de Frango", 20.0));
        cardapio.add(new Produto("Strogonoff", 22.0));
        cardapio.add(new Produto("Caçarola de carne com legumes", 23.0));
        cardapio.add(new Produto("Água", 3.0));
        cardapio.add(new Produto("Suco", 5.0));
        cardapio.add(new Produto("Refrigerante", 4.0));
        cardapio.add(new Produto("Cerveja", 6.0));
        cardapio.add(new Produto("Taça de vinho", 8.0));
    }

    // GET INSTANCIA (Singleton)
    public static ProdutoManager getInstance() {
        if (instancia == null) {
            instancia = new ProdutoManager();
        }
        return instancia;
    }
    
    // IMPRIME CARDÁPIO
    public void imprimeCardapio() {
        System.out.println("---------");
        System.out.println("Cardápio:");
        for (Produto produto : cardapio) {
            System.out.println(produto.getIdProduto() + " - " + produto.getNome() + " - R$" + produto.getPreço());
        }
        System.out.println("---------");
    }

    // OBTÉM POSIÇÃO DO PRODUTO NO ARRAYLIST
    public int posProd(int id) {
        for (Produto p : cardapio) {
            if (id == p.getIdProduto()) {
                return cardapio.indexOf(p);
            }
        }
        return -1;
    }

    // IMPRIME INFORMAÇÕES DE UM PRODUTO ESPECÍFICO
    public void imprimeProd(int id) {
        int posicao = posProd(id);
        if (posicao != -1) {
            Produto p = cardapio.get(posicao);
            System.out.println(p.getIdProduto() + " - " + p.getNome() + " - R$" + p.getPreço());
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    // OBTÉM PRODUTO PELO ID
    public Produto getProdById(int id) {
        int posicao = posProd(id);
        if (posicao != -1) {
            return cardapio.get(posicao);
        }
        return null; 
    }

    // FECHA O SCANNER (SOMENTE NO FINAL DO PROGRAMA)
    public static void fecharScanner() {
        scanner.close();
    }
}
