import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Pessoa {
    private List<Jogo> jogosAnunciados;
    private List<Jogo> historicoVendas;

    public Vendedor(String nome, String email, String senha, String cpf) {
        super(nome, email, senha, cpf);
        jogosAnunciados = new ArrayList<>();
        historicoVendas = new ArrayList<>();
    }

    public static List<Vendedor> lerVendedoresDoArquivo(String arquivo) {
        List<Vendedor> vendedores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Supondo que cada linha do arquivo tenha os dados do vendedor separados por vírgula
                if (parts.length == 4) { // Supondo que cada linha tenha nome, email, senha e CPF do vendedor
                    String nome = parts[0].trim();
                    String email = parts[1].trim();
                    String senha = parts[2].trim();
                    String cpf = parts[3].trim();
                    Vendedor vendedor = new Vendedor(nome, email, senha, cpf);
                    vendedores.add(vendedor);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vendedores;
    }


    public void anunciarJogo(Jogo jogo) {
        jogosAnunciados.add(jogo);
        System.out.println("Jogo anunciado com sucesso!");
    }

    // Método para adicionar jogo ao histórico de vendas
    public void adicionarJogoAoHistoricoVendas(Jogo jogo) {
        historicoVendas.add(jogo);
    }

    public void exibirJogosAnunciados() {
        System.out.println("Jogos anunciados:");
        for (Jogo jogo : jogosAnunciados) {
            System.out.println(jogo.getNomeJogo());
        }
    }

    public List<Jogo> getJogosAnunciados() {
        return jogosAnunciados;
    }

    public List<Jogo> getHistoricoVendas() {
        return historicoVendas;
    }
}
