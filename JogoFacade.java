import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JogoFacade {
    private static final String ARQUIVO_JOGOS_ANUNCIADOS = "jogos_anunciados.txt";
    private static final String ARQUIVO_HISTORICO_VENDAS = "historico_vendas.txt";
    private static final String ARQUIVO_HISTORICO_COMPRAS = "_historico_compras.txt";

    // Singleton Instance
    private static JogoFacade instance;

    private JogoFacade() {
        // Construtor privado para implementar Singleton
    }

    public static JogoFacade getInstance() {
        if (instance == null) {
            instance = new JogoFacade();
        }
        return instance;
    }

    // Carregar Jogos Anunciados
    public List<Jogo> carregarJogosAnunciados() {
        List<Jogo> jogos = new ArrayList<>();
        File arquivo = new File(ARQUIVO_JOGOS_ANUNCIADOS);
        if (!arquivo.exists()) {
            return jogos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Jogo jogo = Jogo.fromTexto(linha);
                if (jogo != null) {
                    jogos.add(jogo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jogos;
    }

    // Salvar Jogos Anunciados
    public void salvarJogosAnunciados(List<Jogo> jogosAnunciados) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_JOGOS_ANUNCIADOS))) {
            for (Jogo jogo : jogosAnunciados) {
                writer.write(jogo.toTexto());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Registrar Venda
    public void registrarVenda(Cliente cliente, String metodoPagamento, Jogo jogo, String infoPagamento) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_HISTORICO_VENDAS, true))) { // Modo append
            String registro = "Cliente: " + cliente.getNome() +
                    ", Jogo: " + jogo.getNomeJogo() +
                    ", Data: " + java.time.LocalDate.now() +
                    ", Preço: R$" + jogo.getPrecoJogo() +
                    ", Vendedor: " + jogo.getVendedorNome() +
                    ", Método de Pagamento: " + metodoPagamento +
                    ", Info Pagamento: " + infoPagamento;
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Registrar Compra no Histórico do Cliente
    public void registrarCompra(Cliente cliente, Jogo jogo, String metodoPagamento) {
        String arquivoHistoricoCliente = cliente.getNome().replaceAll("\\s+", "_") + ARQUIVO_HISTORICO_COMPRAS;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoHistoricoCliente, true))) { // Modo append
            String registro = "Jogo: " + jogo.getNomeJogo() +
                    ", Data: " + java.time.LocalDate.now() +
                    ", Preço: R$" + jogo.getPrecoJogo() +
                    ", Método de Pagamento: " + metodoPagamento;
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
