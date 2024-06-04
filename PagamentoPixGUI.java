import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class PagamentoPixGUI extends JFrame {
    private Jogo jogo;
    private Cliente cliente;
    private List<Jogo> jogosAnunciados;
    private VisualizarJogosGUI visualizarJogosGUI;
    private JTextField chavePixField;

    public PagamentoPixGUI(Jogo jogo, Cliente cliente, List<Jogo> jogosAnunciados, VisualizarJogosGUI visualizarJogosGUI) {
        this.jogo = jogo;
        this.cliente = cliente;
        this.jogosAnunciados = jogosAnunciados;
        this.visualizarJogosGUI = visualizarJogosGUI;

        setTitle("Pagamento PIX");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        panel.add(finalizarCompraButton);

        setVisible(true); // Mostrar a janela após a criação
    }

    private void finalizarCompra() {
        String chavePix = chavePixField.getText();

        if (chavePix.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira a chave PIX.");
            return;
        }

        cliente.adicionarJogoAoHistorico(jogo);
        jogosAnunciados.remove(jogo);
        salvarJogosAnunciados();
        registrarVenda(cliente, "PIX");
        visualizarJogosGUI.atualizarJogosAnunciados();
        JOptionPane.showMessageDialog(this, "Compra efetuada com sucesso usando PIX!");
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
                    ", Método de Pagamento: " + metodoPagamento +
                    ", Chave PIX: " + chavePixField.getText();
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
