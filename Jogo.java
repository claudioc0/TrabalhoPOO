import java.io.Serializable;

public abstract class Jogo implements Serializable {
    //ATRIBUTOS
    private String nome;
    private String descricao;
    private String genero;
    private String dataLancamento;
    private double preco;
    private String imagem;


    //CONSTRUTOR
    public Jogo(String nome, String descricao, String genero, String dataLancamento, double preco, String imagem) {
        this.nome = nome;
        this.descricao = descricao;
        this.genero = genero;
        this.dataLancamento = dataLancamento;
        this.preco = preco;
        this.imagem = imagem;
    }


    //GETTERS
    public String getNomeJogo() {
        return nome;
    }

    public String getGeneroJogo() {
        return genero;
    }
    public double getPrecoJogo() {
        return preco;
    }
    public String getDataLancamento() {
        return dataLancamento;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getImagem(){return imagem;}

    //SETTERS
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setGenero(String genero) {
        this.nome = genero;
    }
    public void setImagem(String imagem){this.imagem = imagem;}


    public void setDataLancamento(String dataLancamento) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    //método abstrato
    public abstract String toTexto();

    public static Jogo fromTexto(String linha) {
        String[] partes = linha.split(",");
        if (partes.length < 7) { // Verifica se a linha contém informações suficientes para criar um jogo
            return null;
        }

        String nome = partes[0];
        String descricao = partes[1];
        String genero = partes[2];
        String dataLancamento = partes[3];
        double preco = Double.parseDouble(partes[4]);
        String imagem = partes[5];

        // Verifica o tipo de jogo com base na informação disponível (requisitos do PC ou console)
        if (partes.length == 7) {
            // Se houver 7 partes, é um JogoPC
            String requisitosPc = partes[6];
            return new JogoPC(nome, descricao, genero, dataLancamento, preco, imagem, requisitosPc);
        } else {
            // Caso contrário, é um JogoConsole
            String console = partes[6];
            return new JogoConsole(nome, descricao, genero, dataLancamento, preco, imagem, console);
        }}
}
