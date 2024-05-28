public class VendedorLogado {

    private static Vendedor vendedorLogado;

    public static Vendedor getVendedorLogado() {
        return vendedorLogado;
    }
    public static void setVendedorLogado(Vendedor vendedor) {
        vendedorLogado = vendedor;
    }
    public static void limparVendedorLogado() {
        vendedorLogado = null;
    }
}
