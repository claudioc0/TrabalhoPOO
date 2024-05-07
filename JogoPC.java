public class JogoPC extends Jogo{
    //ATRiBUTOS
    private String requisitosPc;

    //CONSTRUTOR
    public JogoPC(String nome, String descricao, String genero, String dataLancamento, double preco, String requisitosPc){
        super(nome, descricao, genero, dataLancamento, preco);
        this.requisitosPc = requisitosPc;
    }


}
