import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class DetalhesJogoGUI extends JFrame {
    private Jogo jogo;
    private List<Jogo> jogosAnunciados;
    private VisualizarJogosGUI visualizarJogosGUI;

    public DetalhesJogoGUI(Jogo jogo, List<Jogo> jogosAnunciados, VisualizarJogosGUI visualizarJogosGUI) {
        this.jogo = jogo;
        this.jogosAnunciados = jogosAnunciados;
        this.visualizarJogosGUI = visualizarJogosGUI;

        setTitle("Detalhes do Jogo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel titleLabel = new JLabel(jogo.getNomeJogo(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        centerPanel.add(new JLabel("Vendedor: " + jogo.getVendedorNome()));
        centerPanel.add(new JLabel("Descrição: " + jogo.getDescricao()));
        centerPanel.add(new JLabel("Gênero: " + jogo.getGeneroJogo()));
        centerPanel.add(new JLabel("Data de Lançamento: " + jogo.getDataLancamento()));
        centerPanel.add(new JLabel("Preço: R$" + jogo.getPrecoJogo()));

        if (jogo instanceof JogoPC) {
            centerPanel.add(new JLabel("Requisitos PC: " + ((JogoPC) jogo).getRequisitosPc()));
        } else if (jogo instanceof JogoConsole) {
            centerPanel.add(new JLabel("Console: " + ((JogoConsole) jogo).getConsole()));
        }

        if (jogo.getImagem() != null && !jogo.getImagem().isEmpty()) {
            JLabel imagemLabel = new JLabel();
            ImageIcon imagemIcon = new ImageIcon(jogo.getImagem());
            Image imagemRedimensionada = imagemIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));
            centerPanel.add(imagemLabel);
        }

        panel.add(centerPanel, BorderLayout.CENTER);

        JButton comprarButton = new JButton("Comprar");
        comprarButton.addActionListener(e -> comprarJogo());
        panel.add(comprarButton, BorderLayout.SOUTH);
    }

    private void comprarJogo() {
        jogosAnunciados.remove(jogo);
        atualizarArquivoJogosAnunciados();
        visualizarJogosGUI.atualizarJogosAnunciados();
        JOptionPane.showMessageDialog(this, "Compra efetuada com sucesso!");
        dispose();
    }

    private void atualizarArquivoJogosAnunciados() {
        File arquivoJogosAnunciados = new File("jogos_anunciados.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoJogosAnunciados))) {
            for (Jogo jogo : jogosAnunciados) {
                writer.write(jogo.toTexto());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
