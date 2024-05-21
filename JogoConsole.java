public class JogoConsole extends Jogo{
    private String console;

    public JogoConsole(String nome, String descricao, String genero, String dataLancamento, double preco, String console) {
        super(nome, descricao, genero, dataLancamento, preco);
        this.console = console;
    }

}
