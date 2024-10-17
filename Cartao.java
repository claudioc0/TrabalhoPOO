public class Cartao extends MetodoPagamento {
    private String nomeProprietario;
    private int numeroCartao;
    private int senhaCartao;
    private String dataVencimento;
    private double saldo;

    public Cartao(double valor, int numeroCartao, int senhaCartao, String dataVencimento, double saldo) {
        super(valor);
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
        this.dataVencimento = dataVencimento;
        this.saldo = saldo;
    }

    @Override
    public void pagar() {
        if (saldo >= getValor()) {
            saldo -= getValor();
            System.out.println("Pagamento efetuado com sucesso via Cartão!");
        } else {
            System.out.println("Saldo insuficiente. Pagamento não realizado.");
        }
    }
}
