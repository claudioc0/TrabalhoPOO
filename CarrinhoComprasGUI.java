import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Collections;

public class CarrinhoComprasGUI extends JFrame {
    private CarrinhoCompras carrinhoCompras;
    private List<Jogo> jogosAnunciados;
    private VisualizarJogosGUI visualizarJogosGUI;
    private JButton comprarTodosButton; // Declarando o botão como atributo para poder modificá-lo

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

        comprarTodosButton = new JButton("Comprar Todos");
        comprarTodosButton.addActionListener(e -> comprarTodosDoCarrinho());
        comprarTodosButton.setEnabled(!carrinhoCompras.getJogosNoCarrinho().isEmpty()); // Desabilita o botão se o carrinho estiver vazio
        panel.add(comprarTodosButton, BorderLayout.SOUTH);

        exibirJogosNoCarrinho(centerPanel);
    }

    private void exibirJogosNoCarrinho(JPanel centerPanel) {
        List<Jogo> jogos = carrinhoCompras.getJogosNoCarrinho();
        centerPanel.removeAll();


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
                selecionarMetodoPagamento(cliente, carrinhoCompras.getJogosNoCarrinho());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Nenhum cliente logado.");
        }
    }

    private void selecionarMetodoPagamento(Cliente cliente, List<Jogo> jogos) {
        String[] opcoes = {"Cartão de Crédito", "Boleto", "PIX"};
        String metodoSelecionado = (String) JOptionPane.showInputDialog(
                this,
                "Selecione o método de pagamento:",
                "Método de Pagamento",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]);

        if (metodoSelecionado != null) {
            if (metodoSelecionado.equals("Cartão de Crédito")) {
                new CadastroCartaoGUI(jogos, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
            } else if (metodoSelecionado.equals("Boleto")) {
                new PagamentoBoletoGUI(jogos, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
            } else if (metodoSelecionado.equals("PIX")) {
                new PagamentoPixGUI(jogos, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
            } else {
                processarCompra(cliente, metodoSelecionado, jogos);
            }
        }
    }

    private void processarCompra(Cliente cliente, String metodoPagamento, List<Jogo> jogos) {
        for (Jogo jogo : jogos) {
            cliente.adicionarJogoAoHistorico(jogo);
            jogosAnunciados.remove(jogo);
            registrarVenda(cliente, metodoPagamento, jogo);
        }
        salvarJogosAnunciados();
        visualizarJogosGUI.atualizarJogosAnunciados();
        carrinhoCompras.limparCarrinho();
        JOptionPane.showMessageDialog(this, "Compra efetuada com sucesso usando " + metodoPagamento + "!");
        dispose();
    }

    private void salvarJogosAnunciados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("jogos_anunciados.txt"))) {
            for (Jogo jogo : jogosAnunciados) {
                writer.write(jogo.toTexto());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registrarVenda(Cliente cliente, String metodoPagamento, Jogo jogo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historico_vendas.txt", true))) { // Append mode
            String registro = "Cliente: " + cliente.getNome() +
                    ", Jogo: " + jogo.getNomeJogo() +
                    ", Data: " + java.time.LocalDate.now() +
                    ", Preço: R$" + jogo.getPrecoJogo() +
                    ", Vendedor: " + jogo.getVendedorNome() +
                    ", Método de Pagamento: " + metodoPagamento;
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
