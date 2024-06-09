public class Boleto extends MetodoPagamento{
    //ATRIBUTOS
    private String beneficiario;
    private String dataVencimento;

    //CONTRUTOR
    public Boleto(double valor, String beneficiario, String dataVencimento) {
        super(valor);
        this.beneficiario = beneficiario;
        this.dataVencimento = dataVencimento;
    }
    //MÉTODOS
    @Override
    public void pagar() {
        // Simulação de pagamento com boleto
        System.out.println("Pagamento com Boleto:");
        System.out.println("Valor: R$ " + getValor());
        System.out.println("Beneficiário: " + beneficiario);
        System.out.println("Data de Vencimento: " + dataVencimento);
        System.out.println("Gerando boleto...");
        System.out.println("Boleto gerado com sucesso!");
    }
}
