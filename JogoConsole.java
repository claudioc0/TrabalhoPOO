import java.io.Serializable;

public class JogoConsole extends Jogo implements Serializable {
    private String console;

    public JogoConsole(String nome, String descricao, String genero, String dataLancamento, double preco, String imagem, String console, String vendedorNome) {
        super(nome, descricao, genero, dataLancamento, preco, imagem, vendedorNome);
        this.console = console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public String getConsole() {
        return console;
    }

    @Override
    public String toTexto() {
        return String.join(",", "Console", getNomeJogo(), getDescricao(), getGeneroJogo(), getDataLancamento(), String.valueOf(getPrecoJogo()), getImagem(), getVendedorNome(), console);
    }

    public static JogoConsole fromTexto(String linha) {
        String[] partes = linha.split(",");
        if (partes.length < 9) {
            return null;
        }

        String nome = partes[1];
        String descricao = partes[2];
        String genero = partes[3];
        String dataLancamento = partes[4];
        double preco = Double.parseDouble(partes[5]);
        String imagem = partes[6];
        String vendedorNome = partes[7];
        String console = partes[8];

        return new JogoConsole(nome, descricao, genero, dataLancamento, preco, imagem, console, vendedorNome);
    }
}
