import java.util.ArrayList;
import java.util.List;

public class CarrinhoCompras {
    private List<Jogo> jogos;

    public CarrinhoCompras() {
        this.jogos = new ArrayList<>();


    }

    public void adicionaraoCarrinho(Jogo jogo) {
        jogos.add(jogo);
        System.out.println("O Jogo "  + jogo.getNomeJogo() +  " foi adicionado ao carrinho" );
    }

    public void listarJogos() {
        if (jogos.isEmpty()) {
            System.out.println("Carrinho está vazio");
        } else {
            System.out.println("Jogos no carrinho: ");
            for (Jogo jogo : jogos) {
                System.out.println("- " + jogo.getNomeJogo() + " - R$" + jogo.getPrecoJogo());
            }
        }

    }
}
