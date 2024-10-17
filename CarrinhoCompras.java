import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CarrinhoCompras {
    private List<Jogo> jogos;
    public static CarrinhoCompras instancia;
    private static final String ARQUIVO_JOGOS_ANUNCIADOS = "jogos_anunciados.txt";

    private CarrinhoCompras() {
        this.jogos = new ArrayList<>();
    }

    // GET PARA INSTANCIA UNICA - SINGLETON
    public static synchronized CarrinhoCompras getInstance() {
        if (instancia == null) {
            instancia = new CarrinhoCompras();
        }
        return instancia;
    }
    

    public void adicionaraoCarrinho(Jogo jogo) {
        jogos.add(jogo);
        System.out.println("O Jogo " + jogo.getNomeJogo() + " foi adicionado ao carrinho");
    }

    public void removerDoCarrinho(Jogo jogo) {
        jogos.remove(jogo);
        System.out.println("O Jogo " + jogo.getNomeJogo() + " foi removido do carrinho");
    }

    public void listarJogos() {
        if (jogos.isEmpty()) {
            System.out.println("Carrinho está vazio");
        } else {
            System.out.println("Jogos no carrinho: ");
            for (Jogo jogo : jogos) {
                System.out.println("- " + jogo.getNomeJogo() + " - R$" + jogo.getPrecoJogo());
            }
        }
    }

    public List<Jogo> getJogosNoCarrinho() {
        return jogos;
    }

    public double calcularTotal() {
        double total = 0;
        for (Jogo jogo : jogos) {
            total += jogo.getPrecoJogo();
        }
        return total;
    }

    public void limparCarrinho() {
        jogos.clear();
        System.out.println("Carrinho limpo");
    }

    public void removerJogosDoArquivo(List<Jogo> jogosParaRemover) {
        try {
            Path caminhoArquivo = Paths.get(ARQUIVO_JOGOS_ANUNCIADOS);
            List<String> linhas = Files.readAllLines(caminhoArquivo);

            // Filtra as linhas que não contêm os jogos a serem removidos
            List<String> linhasAtualizadas = linhas.stream()
                    .filter(linha -> jogosParaRemover.stream().noneMatch(jogo -> linha.contains(jogo.getNomeJogo())))
                    .collect(Collectors.toList());

            // Escreve de volta no arquivo apenas as linhas que não foram removidas
            Files.write(caminhoArquivo, linhasAtualizadas, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Os jogos foram removidos do arquivo " + ARQUIVO_JOGOS_ANUNCIADOS);
        } catch (IOException e) {
            System.err.println("Erro ao acessar o arquivo " + ARQUIVO_JOGOS_ANUNCIADOS + ": " + e.getMessage());
        }
    }
}
