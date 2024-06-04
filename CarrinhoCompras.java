import java.util.ArrayList;
import java.util.List;

public class CarrinhoCompras {
    private List<Jogo> jogos;

    public CarrinhoCompras() {
        this.jogos = new ArrayList<>();
    }

    public void adicionaraoCarrinho(Jogo jogo) {
        jogos.add(jogo);
        System.out.println("O Jogo " + jogo.getNomeJogo() + " foi adicionado ao carrinho");
    }

    public void removerDoCarrinho(Jogo jogo) {
        jogos.remove(jogo);
        System.out.println("O Jogo " + jogo.getNomeJogo() + " foi removido do carrinho");
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

    public List<Jogo> getJogosNoCarrinho() {
        return jogos;
    }

    public double calcularTotal() {
        double total = 0;
        for (Jogo jogo : jogos) {
            total += jogo.getPrecoJogo();
        }
        return total;
    }

    public void limparCarrinho() {
        jogos.clear();
        System.out.println("Carrinho limpo");
    }
}
