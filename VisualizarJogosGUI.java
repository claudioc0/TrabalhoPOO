import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class VisualizarJogosGUI extends JFrame implements Observer {
    private List<Jogo> jogosAnunciados;
    private JPanel centerPanel;
    private CarrinhoCompras carrinhoCompras;
    private Cliente clienteLogado;
    private JogoFacade jogoFacade; // Referência à Facade

    public VisualizarJogosGUI(List<Jogo> jogosAnunciados, Cliente cliente, CarrinhoCompras carrinhoCompras) {
        this.jogosAnunciados = jogosAnunciados;
        this.carrinhoCompras = CarrinhoCompras.getInstance();
        this.clienteLogado = cliente;

        this.jogoFacade = JogoFacade.getInstance();
        this.jogoFacade.addObserver(this); // Registra-se como observador

        carregarJogosAnunciadosDoArquivo();

        setTitle("Jogos Anunciados");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        JLabel titleLabel = new JLabel("Jogos Anunciados", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        centerPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setBackground(Color.WHITE);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.addActionListener(e -> voltarParaHome());
        southPanel.add(voltarButton);

        JButton carrinhoButton = new JButton("Ver Carrinho");
        carrinhoButton.setBackground(new Color(25, 120, 165));
        carrinhoButton.setForeground(Color.WHITE);
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
            jogoPanel.setBackground(Color.WHITE);
            jogoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            JLabel nomeLabel = new JLabel("Nome: " + jogo.getNomeJogo());
            JLabel generoLabel = new JLabel("Gênero: " + jogo.getGeneroJogo());
            JLabel precoLabel = new JLabel("Preço: R$" + jogo.getPrecoJogo());

            JPanel labelsPanel = new JPanel(new GridLayout(3, 1));
            labelsPanel.setBackground(Color.WHITE);
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
            comprarButton.setBackground(new Color(25, 120, 165));
            comprarButton.setForeground(Color.WHITE);
            comprarButton.addActionListener(e -> abrirTelaDetalhesJogo(jogo));
            jogoPanel.add(comprarButton, BorderLayout.EAST);

            centerPanel.add(jogoPanel);
        }

        revalidate();
        repaint();
    }

    // ERRO QUANDO CHAMEI O SINGLETON AQUI.
    private void abrirTelaDetalhesJogo(Jogo jogo) {
        SwingUtilities.invokeLater(() -> new DetalhesJogoGUI(jogo, jogosAnunciados, this, CarrinhoCompras.getInstance()).setVisible(true));
    }

    private void abrirCarrinhoCompras() {
        SwingUtilities.invokeLater(() -> new CarrinhoComprasGUI(jogosAnunciados, this).setVisible(true));
    }

    public void atualizarJogosAnunciados() {
        carregarJogosAnunciadosDoArquivo();
        exibirJogosAnunciados();
    }

    public void removerJogoAnunciado(Jogo jogo) {
        jogosAnunciados.remove(jogo);
        atualizarArquivoJogosAnunciados();
        exibirJogosAnunciados();
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
            new HomeClienteGUI(clienteLogado).setVisible(true);
            dispose();
        });
    }

    // Implementação do método update da interface Observer
    @Override
    public void update(Jogo novoJogo) {
        jogosAnunciados.add(novoJogo);
        adicionarJogoAoPainel(novoJogo);
    }

    // Método para adicionar um novo jogo ao painel
    private void adicionarJogoAoPainel(Jogo jogo) {
        JPanel jogoPanel = new JPanel(new BorderLayout(5, 5));
        jogoPanel.setBackground(Color.WHITE);
        jogoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel nomeLabel = new JLabel("Nome: " + jogo.getNomeJogo());
        JLabel generoLabel = new JLabel("Gênero: " + jogo.getGeneroJogo());
        JLabel precoLabel = new JLabel("Preço: R$" + jogo.getPrecoJogo());

        JPanel labelsPanel = new JPanel(new GridLayout(3, 1));
        labelsPanel.setBackground(Color.WHITE);
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
        comprarButton.setBackground(new Color(25, 120, 165));
        comprarButton.setForeground(Color.WHITE);
        comprarButton.addActionListener(e -> abrirTelaDetalhesJogo(jogo));
        jogoPanel.add(comprarButton, BorderLayout.EAST);

        centerPanel.add(jogoPanel);
        centerPanel.revalidate();
        centerPanel.repaint();
    }
}
