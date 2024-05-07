public class Pix extends MetodoPagamento{
    //ATRIBUTOS
    private String chavePix;
    private String banco;
    private String nomeTitular;

    //CONSTRUTOR
    public Pix(double valor, String chavePix, String banco, String nomeTitular){
        super(valor);
        this.chavePix = chavePix;
        this.banco = banco;
        this.nomeTitular = nomeTitular;
    }

    //MÉTODOS
    @Override
    public void pagar() {
        System.out.println("Implementar pagamento com pix");
    }
}
