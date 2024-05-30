import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class VisualizarJogosGUI extends JFrame {
    private ArrayList<Jogo> jogosAnunciados; // Lista de jogos anunciados
    private JPanel centerPanel; // Painel central para exibir os jogos

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
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        centerPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton refreshButton = new JButton("Atualizar");
        refreshButton.addActionListener(e -> atualizarJogosAnunciados());

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(refreshButton);
        panel.add(southPanel, BorderLayout.SOUTH);

        exibirJogosAnunciados();
    }

    // Método para carregar jogos anunciados do arquivo de texto
    private void carregarJogosAnunciadosDoArquivo() {
        File arquivo = new File("jogos_anunciados.txt");
        jogosAnunciados.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Jogo jogo = Jogo.fromTexto(linha);
                if (jogo != null) {
                    jogosAnunciados.add(jogo);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os jogos anunciados: " + e.getMessage());
        }
    }

    // Método para exibir os jogos anunciados no painel central
    private void exibirJogosAnunciados() {
        centerPanel.removeAll();

        for (Jogo jogo : jogosAnunciados) {
            JPanel jogoPanel = new JPanel(new BorderLayout(5, 5));
            jogoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel nomeLabel = new JLabel("Nome: " + jogo.getNomeJogo());
            JLabel generoLabel = new JLabel("Gênero: " + jogo.getGeneroJogo());
            JLabel precoLabel = new JLabel("Preço: R$" + jogo.getPrecoJogo());

            JPanel labelsPanel = new JPanel(new GridLayout(3, 1));
            labelsPanel.add(nomeLabel);
            labelsPanel.add(generoLabel);
            labelsPanel.add(precoLabel);

            if (jogo instanceof JogoPC) {
                JLabel requisitosPcLabel = new JLabel("Requisitos PC: " + ((JogoPC) jogo).getRequisitosPc());
                labelsPanel.add(requisitosPcLabel);
            } else if (jogo instanceof JogoConsole) {
                JLabel consoleLabel = new JLabel("Console: " + ((JogoConsole) jogo).getConsole());
                labelsPanel.add(consoleLabel);
            }

            jogoPanel.add(labelsPanel, BorderLayout.CENTER);

            if (jogo.getImagem() != null && !jogo.getImagem().isEmpty()) {
                JLabel imagemLabel = new JLabel();
                ImageIcon imagemIcon = new ImageIcon(jogo.getImagem());
                Image imagemRedimensionada = imagemIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));
                jogoPanel.add(imagemLabel, BorderLayout.WEST);
            }

            JButton comprarButton = new JButton("Comprar");
            comprarButton.addActionListener(e -> abrirTelaDetalhesJogo(jogo));
            jogoPanel.add(comprarButton, BorderLayout.EAST);

            centerPanel.add(jogoPanel);
        }

        revalidate();
        repaint();
    }

    // Método para abrir a tela de detalhes do jogo
    private void abrirTelaDetalhesJogo(Jogo jogo) {
        SwingUtilities.invokeLater(() -> new DetalhesJogoGUI(jogo).setVisible(true));
    }

    // Método para atualizar a lista de jogos anunciados
    private void atualizarJogosAnunciados() {
        carregarJogosAnunciadosDoArquivo();
        exibirJogosAnunciados();
    }
}
