import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CarrinhoComprasGUI extends JFrame {
    private CarrinhoCompras carrinhoCompras;
    private List<Jogo> jogosAnunciados;
    private VisualizarJogosGUI visualizarJogosGUI;

    public CarrinhoComprasGUI(CarrinhoCompras carrinhoCompras, List<Jogo> jogosAnunciados, VisualizarJogosGUI visualizarJogosGUI) {
        this.carrinhoCompras = carrinhoCompras;
        this.jogosAnunciados = jogosAnunciados;
        this.visualizarJogosGUI = visualizarJogosGUI;

        setTitle("Carrinho de Compras");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel titleLabel = new JLabel("Jogos no Carrinho", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton comprarTodosButton = new JButton("Comprar Todos");
        comprarTodosButton.addActionListener(e -> comprarTodosDoCarrinho());
        panel.add(comprarTodosButton, BorderLayout.SOUTH);

        exibirJogosNoCarrinho(centerPanel);
    }

    private void exibirJogosNoCarrinho(JPanel centerPanel) {
        List<Jogo> jogos = carrinhoCompras.getJogosNoCarrinho();
        centerPanel.removeAll();  // Clear existing content

        for (Jogo jogo : jogos) {
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

            JButton removerButton = new JButton("Remover");
            removerButton.addActionListener(e -> removerJogoDoCarrinho(jogo));
            jogoPanel.add(removerButton, BorderLayout.EAST);

            centerPanel.add(jogoPanel);
        }

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void removerJogoDoCarrinho(Jogo jogo) {
        carrinhoCompras.removerDoCarrinho(jogo);
        exibirJogosNoCarrinho((JPanel) ((JScrollPane) getContentPane().getComponent(1)).getViewport().getView());
        JOptionPane.showMessageDialog(this, "Jogo removido do carrinho!");
    }

    private void comprarTodosDoCarrinho() {
        Cliente cliente = ClienteLogado.getClienteLogado();
        if (cliente != null) {
            double total = carrinhoCompras.calcularTotal();
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja comprar todos os jogos do carrinho por R$" + total + "?", "Confirmar Compra", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                List<Jogo> jogos = carrinhoCompras.getJogosNoCarrinho();
                for (Jogo jogo : jogos) {
                    selecionarMetodoPagamento(cliente, jogo);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum cliente logado.");
        }
    }

    private void selecionarMetodoPagamento(Cliente cliente, Jogo jogo) {
        String[] opcoes = {"Cartão de Crédito", "Boleto", "Pix"};
        String metodoSelecionado = (String) JOptionPane.showInputDialog(
                this,
                "Selecione o método de pagamento:",
                "Método de Pagamento",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (metodoSelecionado != null) {
            switch (metodoSelecionado) {
                case "Cartão de Crédito":
                    processarCompraCartaoCredito(cliente, jogo);
                    break;
                case "Boleto":
                    processarCompraBoleto(cliente, jogo);
                    break;
                case "Pix":
                    processarCompraPix(cliente, jogo);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Método de pagamento inválido.");
            }
        }
    }

    private void processarCompraCartaoCredito(Cliente cliente, Jogo jogo) {
        new CadastroCartaoGUI(jogo, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
    }

    private void processarCompraBoleto(Cliente cliente, Jogo jogo) {
        new PagamentoBoletoGUI(jogo, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
    }

    private void processarCompraPix(Cliente cliente, Jogo jogo) {
        new PagamentoPixGUI(jogo, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
    }
}
