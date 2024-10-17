public class Boleto extends MetodoPagamento {
    private String beneficiario;
    private String dataVencimento;

    public Boleto(double valor, String beneficiario, String dataVencimento) {
        super(valor);
        this.beneficiario = beneficiario;
        this.dataVencimento = dataVencimento;
    }

    @Override
    public void pagar() {
        System.out.println("Pagamento com Boleto:");
        System.out.println("Valor: R$ " + getValor());
        System.out.println("Beneficiário: " + beneficiario);
        System.out.println("Data de Vencimento: " + dataVencimento);
        System.out.println("Boleto gerado com sucesso!");
    }
}
