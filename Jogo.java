public class Jogo {
    //ATRIBUTOS
    private String nome;
    private String descricao;
    private String genero;
    private String dataLancamento;
    private double preco;


    //CONSTRUTOR
    public Jogo(String nome, String descricao, String genero, String dataLancamento, double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.genero = genero;
        this.dataLancamento = dataLancamento;
        this.preco = preco;
    }


    //GETTERS
    public String getNomejogo() {
        return nome;
    }

    public String getgenerojogo() {
        return genero;
    }
    public double getPrecojogo() {
        return preco;
    }
    public String getDataLancamento() {
        return dataLancamento;
    }
    public String getDescricao() {
        return descricao;
    }


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


    public void setDataLancamento(String dataLancamento) {
        this.nome = nome;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }













}
