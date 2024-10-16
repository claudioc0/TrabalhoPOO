public class PagamentoContext {
    private MetodoPagamentoStrategy metodoPagamento;

    public void setMetodoPagamento(MetodoPagamentoStrategy metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void realizarPagamento() {
        if(metodoPagamento != null){
            metodoPagamento.pagar();
        } else {
            System.out.println("Nenhum método de pagamento foi definido.");
        }
    }
    
}
