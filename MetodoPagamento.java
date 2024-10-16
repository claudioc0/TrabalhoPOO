public abstract class MetodoPagamento {
    //ATRIBUTOS
    private double valor;

    public MetodoPagamento(double valor) {
        this.valor = valor;
    }

    //MÉTODO ABSTRATO
    public double getValor() {
        return valor;
    }
}
