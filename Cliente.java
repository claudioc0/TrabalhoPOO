import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente extends Pessoa {
    private List<Jogo> historicoCompras;

    public Cliente(String nome, String email, String senha, String cpf) {
        super(nome, email, senha, cpf);
        historicoCompras = new ArrayList<>();
        carregarHistoricoCompras();
    }

    public List<Jogo> getHistoricoCompras() {
        return historicoCompras;
    }

    public void adicionarJogoAoHistorico(Jogo jogo) {
        historicoCompras.add(jogo);
        salvarHistoricoCompras();
    }

    public void salvarHistoricoCompras() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getCpf() + "_compras.txt"))) {
            for (Jogo jogo : historicoCompras) {
                writer.write(jogo.toTexto());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void carregarHistoricoCompras() {
        File arquivoCompras = new File(getCpf() + "_compras.txt");
        if (!arquivoCompras.exists()) {
            return;
        }


        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoCompras))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Jogo jogo = Jogo.fromTexto(linha);
                if (jogo != null) {
                    historicoCompras.add(jogo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
