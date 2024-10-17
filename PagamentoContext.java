public class PagamentoContext {
    private MetodoPagamento metodoPagamento;

    public PagamentoContext(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void realizarPagamento() {
        if (metodoPagamento != null) {
            metodoPagamento.pagar();  // Chama o método pagar da instância fornecida
        } else {
            System.out.println("Nenhum método de pagamento foi definido.");
        }
    }
}
