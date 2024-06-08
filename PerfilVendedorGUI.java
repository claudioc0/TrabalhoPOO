import javax.swing.*;
import java.awt.*;

public class PerfilVendedorGUI extends JFrame {
    private JTextArea infoTextArea;
    private Vendedor vendedorLogado;

    public PerfilVendedorGUI(Vendedor vendedor) {
        vendedorLogado = vendedor;
        setTitle("Perfil do Vendedor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        infoTextArea.setBackground(Color.WHITE);
        infoTextArea.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.addActionListener(e -> {
            new HomeVendedorGUI(vendedorLogado).setVisible(true);
            dispose();
        });
        panel.add(voltarButton, BorderLayout.SOUTH);

        carregarInformacoesVendedor();
    }

    private void carregarInformacoesVendedor() {
        String infoVendedor = "Nome: " + vendedorLogado.getNome() + "\n" +
                "Email: " + vendedorLogado.getEmail() + "\n" +
                "CPF: " + vendedorLogado.getCPF();

        infoTextArea.setText(infoVendedor);
    }

    public static void main(String[] args) {
        Vendedor vendedorLogado = new Vendedor("Nome do Vendedor", "vendedor@example.com", "senha", "12345678901");
        SwingUtilities.invokeLater(() -> new PerfilVendedorGUI(vendedorLogado).setVisible(true));
    }
}
