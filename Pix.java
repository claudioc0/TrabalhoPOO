public class Pix extends MetodoPagamento {
    private String chavePix;
    private String banco;
    private String nomeTitular;

    public Pix(double valor, String chavePix, String banco, String nomeTitular) {
        super(valor);
        this.chavePix = chavePix;
        this.banco = banco;
        this.nomeTitular = nomeTitular;
    }

    @Override
    public void pagar() {
        System.out.println("Pagamento com Pix:");
        System.out.println("Valor: R$ " + getValor());
        System.out.println("Chave Pix: " + chavePix);
        System.out.println("Banco: " + banco);
        System.out.println("Nome do Titular: " + nomeTitular);
        System.out.println("Pagamento via Pix realizado com sucesso!");
    }
}
