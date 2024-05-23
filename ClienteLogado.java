public class ClienteLogado {
    private static Cliente clienteLogado;

    public static Cliente getClienteLogado() {
        return clienteLogado;
    }

    public static void setClienteLogado(Cliente cliente) {
        clienteLogado = cliente;
    }

    public static void limparClienteLogado() {
        clienteLogado = null;
    }
}
