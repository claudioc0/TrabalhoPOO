import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VisualizarAvaliacoesGUI extends JFrame {
    private JTextArea avaliacoesTextArea;
    private Vendedor vendedor;

    public VisualizarAvaliacoesGUI(Vendedor vendedor) {
        this.vendedor = vendedor;

        setTitle("Visualizar Avaliações");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        JLabel titleLabel = new JLabel("Avaliações Recebidas", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        avaliacoesTextArea = new JTextArea();
        avaliacoesTextArea.setEditable(false);
        avaliacoesTextArea.setBackground(Color.WHITE);
        avaliacoesTextArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(avaliacoesTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.addActionListener(e -> voltarParaMenuPrincipal());
        panel.add(voltarButton, BorderLayout.SOUTH);

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

    private void voltarParaMenuPrincipal() {
        HomeVendedorGUI homeVendedorGUI = new HomeVendedorGUI(vendedor);
        homeVendedorGUI.setVisible(true);
        dispose();
    }
}
