import java.io.Serializable;

public abstract class Jogo implements Serializable {
    private String nome;
    private String descricao;
    private String genero;
    private String dataLancamento;
    private double preco;
    private String imagem;
    private String vendedorNome;

    public Jogo(String nome, String descricao, String genero, String dataLancamento, double preco, String imagem, String vendedorNome) {
        this.nome = nome;
        this.descricao = descricao;
        this.genero = genero;
        this.dataLancamento = dataLancamento;
        this.preco = preco;
        this.imagem = imagem;
        this.vendedorNome = vendedorNome;
    }

    public String getNomeJogo() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getGeneroJogo() {
        return genero;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public double getPrecoJogo() {
        return preco;
    }

    public String getImagem() {
        return imagem;
    }

    public String getVendedorNome() {
        return vendedorNome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setVendedorNome(String vendedorNome) {
        this.vendedorNome = vendedorNome;
    }

    public abstract String toTexto();

    public static Jogo fromTexto(String linha) {
        String[] partes = linha.split(",");
        if (partes.length < 8) {
            return null;
        }

        String tipo = partes[0];
        String nome = partes[1];
        String descricao = partes[2];
        String genero = partes[3];
        String dataLancamento = partes[4];
        double preco = Double.parseDouble(partes[5]);
        String imagem = partes[6];
        String vendedorNome = partes[7];

        if (tipo.equals("PC")) {
            String requisitosPc = partes[8];
            return new JogoPC(nome, descricao, genero, dataLancamento, preco, imagem, requisitosPc, vendedorNome);
        } else if (tipo.equals("Console")) {
            String console = partes[8];
            return new JogoConsole(nome, descricao, genero, dataLancamento, preco, imagem, console, vendedorNome);
        } else {
            return null;
        }
    }
}
