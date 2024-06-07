import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class VisualizarJogosGUI extends JFrame {
    private List<Jogo> jogosAnunciados;
    private JPanel centerPanel;
    private CarrinhoCompras carrinhoCompras;

    public VisualizarJogosGUI(List<Jogo> jogosAnunciados) {
        this.jogosAnunciados = jogosAnunciados;
        this.carrinhoCompras = new CarrinhoCompras(); // Inicializa o carrinho de compras

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

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> voltarParaHome());
        southPanel.add(voltarButton);

        JButton carrinhoButton = new JButton("Ver Carrinho");
        carrinhoButton.addActionListener(e -> abrirCarrinhoCompras());
        southPanel.add(carrinhoButton);

        panel.add(southPanel, BorderLayout.SOUTH);

        exibirJogosAnunciados();
    }



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

    private void abrirTelaDetalhesJogo(Jogo jogo) {
        SwingUtilities.invokeLater(() -> new DetalhesJogoGUI(jogo, jogosAnunciados, this, carrinhoCompras).setVisible(true));
    }

    private void abrirCarrinhoCompras() {
        SwingUtilities.invokeLater(() -> new CarrinhoComprasGUI(carrinhoCompras, jogosAnunciados, this).setVisible(true));
    }

    public void atualizarJogosAnunciados() {
        carregarJogosAnunciadosDoArquivo();
        exibirJogosAnunciados();
    }

    public void removerJogoAnunciado(Jogo jogo) {
        jogosAnunciados.remove(jogo);
        atualizarArquivoJogosAnunciados(); // Atualiza o arquivo após a remoção do jogo
        exibirJogosAnunciados(); // Atualiza a exibição após a remoção do jogo
    }

    private void atualizarArquivoJogosAnunciados() {
        File arquivo = new File("jogos_anunciados.txt");
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            for (Jogo jogo : jogosAnunciados) {
                writer.write(jogo.toTexto());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar o arquivo de jogos anunciados: " + e.getMessage());
        }
    }

    public List<Jogo> getJogosAnunciados() {
        return this.jogosAnunciados;
    }

    private void voltarParaHome() {

        SwingUtilities.invokeLater(() -> {
            new HomeClienteGUI().setVisible(true);
            dispose();
        });
}
}
