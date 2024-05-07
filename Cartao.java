public class Cartao extends MetodoPagamento {
    //ATRIBUTOS
    private String numeroCartao;
    private String senhaCartao;
    private int dataVencimento;

    //CONSTRUTOR
    public Cartao(double valor, String numeroCartao, String senhaCartao, int dataVencimento) {
        super(valor);
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
        this.dataVencimento = dataVencimento;
    }

    //MÉTODOS
    @Override
    public void pagar() {
        System.out.println("Implementar pagamento de cartao");
    }


    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getsenha() {
        return senhaCartao;
    }

    public int getDataVencimento() {
        return dataVencimento;
    }
}