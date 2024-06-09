import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PagamentoPixGUI extends JFrame {
    private List<Jogo> jogos;
    private Cliente cliente;
    private List<Jogo> jogosAnunciados;
    private VisualizarJogosGUI visualizarJogosGUI;
    private JTextField chavePixField;

    public PagamentoPixGUI(List<Jogo> jogos, Cliente cliente, List<Jogo> jogosAnunciados, VisualizarJogosGUI visualizarJogosGUI) {
        this.jogos = jogos;
        this.cliente = cliente;
        this.jogosAnunciados = jogosAnunciados;
        this.visualizarJogosGUI = visualizarJogosGUI;

        setTitle("Pagamento PIX");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        panel.add(new JLabel("Chave PIX:"));
        chavePixField = new JTextField();
        panel.add(chavePixField);

        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarCompra();
            }
        });
        finalizarCompraButton.setBackground(new Color(25, 120, 165)); // Defina a cor de fundo do botão
        finalizarCompraButton.setForeground(Color.WHITE); // Defina a cor do texto do botão
        panel.add(finalizarCompraButton);

        setVisible(true);
    }

    private void finalizarCompra() {
        String chavePix = chavePixField.getText();

        if (chavePix.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira a chave PIX.");
            return;
        }

        if (!validarChavePix(chavePix)) {
            JOptionPane.showMessageDialog(this, "Chave PIX inválida. Deve conter exatamente 10 dígitos.");
            return;
        }

        for (Jogo jogo : jogos) {
            cliente.adicionarJogoAoHistorico(jogo);
            jogosAnunciados.remove(jogo);
            registrarVenda(cliente, "PIX", jogo);
        }

        salvarJogosAnunciados();
        visualizarJogosGUI.atualizarJogosAnunciados();
        JOptionPane.showMessageDialog(this, "Compra efetuada com sucesso usando PIX!");
        dispose();
    }

    private boolean validarChavePix(String chavePix) {
        return chavePix.matches("\\d{10}");
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
                    ", Método de Pagamento: " + metodoPagamento +
                    ", Chave PIX: " + chavePixField.getText();
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
