import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PagamentoBoletoGUI extends JFrame {
    private List<Jogo> jogos;
    private Cliente cliente;
    private List<Jogo> jogosAnunciados;
    private VisualizarJogosGUI visualizarJogosGUI;
    private JTextField cpfField;

    public PagamentoBoletoGUI(List<Jogo> jogos, Cliente cliente, List<Jogo> jogosAnunciados, VisualizarJogosGUI visualizarJogosGUI) {
        this.jogos = jogos;
        this.cliente = cliente;
        this.jogosAnunciados = jogosAnunciados;
        this.visualizarJogosGUI = visualizarJogosGUI;

        setTitle("Cadastro de Boleto Bancário");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        panel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        panel.add(cpfField);

        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarCompra();
            }
        });
        finalizarCompraButton.setBackground(new Color(25, 120, 165));
        finalizarCompraButton.setForeground(Color.WHITE);
        panel.add(finalizarCompraButton);

        setVisible(true);
    }

    private void finalizarCompra() {
        String cpf = cpfField.getText();

        if (!validarCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido.");
            return;
        }

        for (Jogo jogo : jogos) {
            cliente.adicionarJogoAoHistorico(jogo);
            jogosAnunciados.remove(jogo);
            registrarVenda(cliente, "Boleto Bancário", jogo);
        }

        salvarJogosAnunciados();
        visualizarJogosGUI.atualizarJogosAnunciados();
        JOptionPane.showMessageDialog(this, "Compra efetuada com sucesso usando Boleto Bancário!");
        dispose();
    }

    private boolean validarCPF(String cpf) {
        // Verifica se o CPF possui 11 dígitos numéricos
        return cpf.matches("\\d{11}");
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
                    ", CPF: " + cpfField.getText();
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
