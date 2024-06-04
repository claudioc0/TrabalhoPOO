import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VisualizarVendasGUI extends JFrame {
    private Vendedor vendedor;

    public VisualizarVendasGUI(Vendedor vendedor) {
        this.vendedor = vendedor;

        setTitle("Histórico de Vendas");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configura o layout do painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel titleLabel = new JLabel("Histórico de Vendas", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea vendasTextArea = new JTextArea();
        vendasTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(vendasTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        carregarHistoricoVendas(vendasTextArea);
    }

    private void carregarHistoricoVendas(JTextArea vendasTextArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader("historico_vendas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Verifica se a linha contém o nome do vendedor logado
                if (linha.contains("Vendedor: " + vendedor.getNome())) {
                    vendasTextArea.append(linha + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar o histórico de vendas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Crie uma instância de Vendedor para teste
        Vendedor vendedor = new Vendedor("Vendedor Teste", "vendedor@teste.com", "senha123", "12345678901");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VisualizarVendasGUI(vendedor).setVisible(true);
            }
        });
    }
}
