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
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        JLabel titleLabel = new JLabel("Histórico de Vendas", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea vendasTextArea = new JTextArea();
        vendasTextArea.setEditable(false);
        vendasTextArea.setBackground(Color.WHITE); // Fundo branco
        vendasTextArea.setForeground(Color.BLACK); // Texto preto
        JScrollPane scrollPane = new JScrollPane(vendasTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.setBackground(new Color(25, 120, 165)); // Cor azul
        voltarButton.setForeground(Color.WHITE); // Texto branco
        voltarButton.addActionListener(e -> voltarParaMenuPrincipal());
        panel.add(voltarButton, BorderLayout.SOUTH);

        carregarHistoricoVendas(vendasTextArea);
    }

    private void carregarHistoricoVendas(JTextArea vendasTextArea) {
        boolean vendasEncontradas = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("historico_vendas.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Verifica se a linha contém o nome do vendedor logado
                if (linha.contains("Vendedor: " + vendedor.getNome())) {
                    vendasTextArea.append(linha + "\n");
                    vendasEncontradas = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar o histórico de vendas.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        if (!vendasEncontradas) {
            vendasTextArea.setText("Nenhuma venda encontrada para este vendedor.");
        }
    }

    private void voltarParaMenuPrincipal() {
        HomeVendedorGUI homeVendedorGUI = new HomeVendedorGUI(vendedor);
        homeVendedorGUI.setVisible(true);
        dispose();
    }
}
