import javax.swing.*;
import java.awt.*;

public class PerfilVendedorGUI extends JFrame {
    private JTextArea infoTextArea;
    private Vendedor vendedorLogado; // Variável para armazenar o vendedor logado

    public PerfilVendedorGUI(Vendedor vendedor) { // Passa o vendedor logado como parâmetro
        vendedorLogado = vendedor; // Armazena o vendedor logado
        setTitle("Perfil do Vendedor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        infoTextArea = new JTextArea();
        infoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            new HomeVendedorGUI(vendedorLogado).setVisible(true);
            dispose();
        });
        panel.add(voltarButton, BorderLayout.SOUTH);

        carregarInformacoesVendedor();
    }

    private void carregarInformacoesVendedor() {
        // Carregar as informações apenas do vendedor logado
        String infoVendedor = "Nome: " + vendedorLogado.getNome() + "\n" +
                "Email: " + vendedorLogado.getEmail() + "\n" +
                "CPF: " + vendedorLogado.getCPF(); // Adicione outros campos conforme necessário

        infoTextArea.setText(infoVendedor);
    }

    public static void main(String[] args) {
        // Exemplo de uso
        Vendedor vendedorLogado = new Vendedor("Nome do Vendedor", "vendedor@example.com", "senha", "12345678901");
        SwingUtilities.invokeLater(() -> new PerfilVendedorGUI(vendedorLogado).setVisible(true));
    }
}
