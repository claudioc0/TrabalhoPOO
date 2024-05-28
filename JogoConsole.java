public class JogoConsole extends Jogo{
    private String console;

    public JogoConsole(String nome, String descricao, String genero, String dataLancamento, double preco, String imagem, String console) {
        super(nome, descricao, genero, dataLancamento, preco, imagem);
        this.console = console;
    }

    public void setConsole(String console){
        this.console = console;
    }

    public String getConsole(){
        return console;
    }

    public String toTexto() {
        // Concatenando todos os atributos em uma única string, separados por vírgula
        return String.join(",", getNomeJogo(), getDescricao(), getGeneroJogo(), getDataLancamento(), String.valueOf(getPrecoJogo()), getImagem(), console);
    }

    public static JogoPC fromTexto(String linha) {
        String[] partes = linha.split(",");
        if (partes.length < 7) {
            return null;
        }

        String nome = partes[0];
        String descricao = partes[1];
        String genero = partes[2];
        String dataLancamento = partes[3];
        double preco = Double.parseDouble(partes[4]);
        String imagem = partes[5];
        String requisitosPc = partes[6];

        return new JogoPC(nome, descricao, genero, dataLancamento, preco, imagem, requisitosPc);
    }
}
