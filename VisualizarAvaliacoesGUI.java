import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VisualizarAvaliacoesGUI extends JFrame {
    private JTextArea avaliacoesTextArea;

    public VisualizarAvaliacoesGUI(Vendedor vendedor) {
        setTitle("Visualizar Avaliações");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        // Rótulo de título
        JLabel titleLabel = new JLabel("Avaliações Recebidas", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Área de texto para exibir as avaliações
        avaliacoesTextArea = new JTextArea();
        avaliacoesTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(avaliacoesTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Carregar as avaliações do arquivo
        carregarAvaliacoes(vendedor);
    }

    private void carregarAvaliacoes(Vendedor vendedor) {
        File file = new File(vendedor.getNome() + "_avaliacoes.txt");
        if (!file.exists()) {
            avaliacoesTextArea.setText("Nenhuma avaliação recebida.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                avaliacoesTextArea.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar as avaliações.");
        }
    }

    public static void main(String[] args) {
        // Teste da interface
        Vendedor vendedor = new Vendedor("Vendedor Teste", "vendedor@teste.com", "senha123", "12345678901");
        SwingUtilities.invokeLater(() -> new VisualizarAvaliacoesGUI(vendedor).setVisible(true));
    }
}
