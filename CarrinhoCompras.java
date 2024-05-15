import java.util.ArrayList;
import java.util.List;

public class CarrinhoCompras {
    private List<Jogo> jogos;
    private double total;

    public CarrinhoCompras() {
        this.jogos = new ArrayList<>();
        this.total = 0.0;


    }

    public void adicionaraoCarrinho(Jogo jogo) {
        this.jogos.add(jogo);
        this.total += jogo.getPrecojogo();
       // jogos.add(jogo);
        //System.out.println("O Jogo "  + jogo.getNomejogo() +  " foi adicionado ao carrinho" );
    }

    public void removerJogo(int index) {
        if (index >= 0 && index < jogos.size()) {
            Jogo jogoRemovido = jogos.remove(index);
            this.total -= jogoRemovido.getPrecojogo();
        }
     }

     public void limparCarrinho() {
         this.jogos.clear();
         this.total = 0.0;

     }

     public void mostrarCarrinho() {
         System.out.println("Carrinho de compras:");
         for (int i = 0; i < jogos.size(); i++) {
             System.out.println((i+1) + ". " + jogos.get(i).getNomejogo() + " - R$" + jogos.get(i). getPrecojogo());
         }
         System.out.println("Total: R$" + total);


    //public void listarJogos() {
      //  if (jogos.isEmpty()) {
        //    System.out.println("Carrinho está vazio");
        //} else {
          //  System.out.println("Jogos no carrinho: ");
            //for (Jogo jogo : jogos) {
              //  System.out.println("- " + jogo.getNomejogo() + " - R$" + jogo.getPrecojogo());
            //}
        //}

    }
}
