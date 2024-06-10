import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.Collections;

public class DetalhesJogoGUI extends JFrame {
    private Jogo jogo;
    private List<Jogo> jogosAnunciados;
    private VisualizarJogosGUI visualizarJogosGUI;
    private CarrinhoCompras carrinhoCompras;

    public DetalhesJogoGUI(Jogo jogo, List<Jogo> jogosAnunciados, VisualizarJogosGUI visualizarJogosGUI, CarrinhoCompras carrinhoCompras) {
        this.jogo = jogo;
        this.jogosAnunciados = jogosAnunciados;
        this.visualizarJogosGUI = visualizarJogosGUI;
        this.carrinhoCompras = carrinhoCompras;

        setTitle("Detalhes do Jogo");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        JLabel titleLabel = new JLabel(jogo.getNomeJogo(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.add(new JLabel("Vendedor: " + jogo.getVendedorNome()));
        infoPanel.add(new JLabel("Descrição: " + jogo.getDescricao()));
        infoPanel.add(new JLabel("Gênero: " + jogo.getGeneroJogo()));
        infoPanel.add(new JLabel("Ano de Lançamento: " + jogo.getDataLancamento()));
        infoPanel.add(new JLabel("Preço: R$" + jogo.getPrecoJogo()));

        if (jogo instanceof JogoPC) {
            infoPanel.add(new JLabel("Requisitos PC: " + ((JogoPC) jogo).getRequisitosPc()));
        } else if (jogo instanceof JogoConsole) {
            infoPanel.add(new JLabel("Console: " + ((JogoConsole) jogo).getConsole()));
        }

        centerPanel.add(infoPanel, BorderLayout.CENTER);

        if (jogo.getImagem() != null && !jogo.getImagem().isEmpty()) {
            JLabel imagemLabel = new JLabel();
            ImageIcon imagemIcon = new ImageIcon(jogo.getImagem());
            Image imagemRedimensionada = imagemIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));
            centerPanel.add(imagemLabel, BorderLayout.EAST); // Posiciona a imagem à direita
        }

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton comprarButton = new JButton("Comprar");
        comprarButton.setBackground(new Color(25, 120, 165));
        comprarButton.setForeground(Color.WHITE);
        comprarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente clienteLogado = ClienteLogado.getClienteLogado();
                if (clienteLogado != null) {
                    selecionarMetodoPagamento(clienteLogado);
                } else {
                    JOptionPane.showMessageDialog(DetalhesJogoGUI.this, "Nenhum cliente logado.");
                }
            }
        });
        buttonPanel.add(comprarButton);

        JButton adicionarCarrinhoButton = new JButton("Adicionar ao Carrinho");
        adicionarCarrinhoButton.setBackground(new Color(25, 120, 165));
        adicionarCarrinhoButton.setForeground(Color.WHITE);
        adicionarCarrinhoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarAoCarrinho();
            }
        });
        buttonPanel.add(adicionarCarrinhoButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void selecionarMetodoPagamento(Cliente cliente) {
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
            List<Jogo> jogos = Collections.singletonList(jogo);
            if (metodoSelecionado.equals("Cartão de Crédito")) {
                new CadastroCartaoGUI(jogos, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
            } else if (metodoSelecionado.equals("Boleto")) {
                new PagamentoBoletoGUI(jogos, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
            } else if (metodoSelecionado.equals("PIX")) {
                new PagamentoPixGUI(jogos, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
            } else {
                processarCompra(cliente, metodoSelecionado);
            }
        }
    }

    private void adicionarAoCarrinho() {
        if (!carrinhoCompras.getJogosNoCarrinho().contains(jogo)) {
            carrinhoCompras.adicionaraoCarrinho(jogo);
            JOptionPane.showMessageDialog(this, "Jogo adicionado ao carrinho!");
        } else {
            JOptionPane.showMessageDialog(this, "O jogo já está no carrinho.");
        }
    }

    private void processarCompra(Cliente cliente, String metodoPagamento) {
        cliente.adicionarJogoAoHistorico(jogo);
        jogosAnunciados.remove(jogo);
        salvarJogosAnunciados();
        registrarVenda(cliente, metodoPagamento);
        visualizarJogosGUI.atualizarJogosAnunciados();
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

    private void registrarVenda(Cliente cliente, String metodoPagamento) {
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
