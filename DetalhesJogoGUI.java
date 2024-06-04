import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        panel.add(comprarButton, BorderLayout.SOUTH);
    }

    private void selecionarMetodoPagamento(Cliente cliente) {
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
            if (metodoSelecionado.equals("Cartão de Crédito")) {
                new CadastroCartaoGUI(jogo, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
            } else if (metodoSelecionado.equals("Boleto")) {
                new PagamentoBoletoGUI(jogo, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
            } else if (metodoSelecionado.equals("PIX")) {
                new PagamentoPixGUI(jogo, cliente, jogosAnunciados, visualizarJogosGUI).setVisible(true);
            } else {
                processarCompra(cliente, metodoSelecionado);
            }
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
