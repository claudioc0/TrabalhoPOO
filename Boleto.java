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
        System.out.println("Implementar pagamento com boleto");
    }
}
