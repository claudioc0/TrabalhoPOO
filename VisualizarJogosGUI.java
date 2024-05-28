import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class VisualizarJogosGUI extends JFrame {
    private ArrayList<Jogo> jogosAnunciados; // Lista de jogos anunciados

    public VisualizarJogosGUI(ArrayList<Jogo> jogosAnunciados) {
        this.jogosAnunciados = jogosAnunciados;

        carregarJogosAnunciadosDoArquivo();

        setTitle("Jogos Anunciados");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel titleLabel = new JLabel("Jogos Anunciados", JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(jogosAnunciados.size(), 1, 10, 10));

        // Adiciona cada jogo anunciado ao painel central
        for (Jogo jogo : jogosAnunciados) {
            JPanel jogoPanel = new JPanel(new BorderLayout());
            jogoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel nomeLabel = new JLabel("Nome: " + jogo.getNomeJogo());
            JLabel generoLabel = new JLabel("Gênero: " + jogo.getGeneroJogo());
            JLabel precoLabel = new JLabel("Preço: " + jogo.getPrecoJogo());

            jogoPanel.add(nomeLabel, BorderLayout.NORTH);
            jogoPanel.add(generoLabel, BorderLayout.CENTER);
            jogoPanel.add(precoLabel, BorderLayout.SOUTH);

            centerPanel.add(jogoPanel);
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
    }

    // Método para carregar jogos anunciados do arquivo de texto
    private void carregarJogosAnunciadosDoArquivo() {
        File arquivo = new File("jogos_anunciados.txt");

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Jogo jogo = Jogo.fromTexto(linha);
                if (jogo != null) {
                    jogosAnunciados.add(jogo);
                }
            }
            // Atualizar a tela com os jogos carregados
            atualizarJogosAnunciados(jogosAnunciados);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os jogos anunciados: " + e.getMessage());
        }
    }

    // Método para atualizar a lista de jogos anunciados
    public void atualizarJogosAnunciados(ArrayList<Jogo> jogosAnunciados) {
        this.jogosAnunciados = jogosAnunciados;
        // Remove todos os componentes do painel central
        getContentPane().removeAll();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel titleLabel = new JLabel("Jogos Anunciados", JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(jogosAnunciados.size(), 1, 10, 10));

        // Adiciona cada jogo anunciado ao painel central
        for (Jogo jogo : jogosAnunciados) {
            JPanel jogoPanel = new JPanel(new BorderLayout());
            jogoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel nomeLabel = new JLabel("Nome: " + jogo.getNomeJogo());
            JLabel generoLabel = new JLabel("Gênero: " + jogo.getGeneroJogo());
            JLabel precoLabel = new JLabel("Preço: " + jogo.getPrecoJogo());

            jogoPanel.add(nomeLabel, BorderLayout.NORTH);
            jogoPanel.add(generoLabel, BorderLayout.CENTER);
            jogoPanel.add(precoLabel, BorderLayout.SOUTH);

            centerPanel.add(jogoPanel);
        }

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Revalida e redesenha a tela
        revalidate();
        repaint();
    }
}
