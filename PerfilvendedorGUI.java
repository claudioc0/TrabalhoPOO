import javax.swing.*;
import java.awt.*;

public class PerfilvendedorGUI extends JFrame {
    private JTextArea infoTextArea;
    private Vendedor vendedorLogado;

    public PerfilvendedorGUI(Vendedor vendedor) {
        vendedorLogado = vendedor;
        setTitle("Perfil do vendedor");
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

        });
        panel.add(voltarButton, BorderLayout.SOUTH);

        carregarInformacoesVendedor();




    }

    private void carregarInformacoesVendedor() {
        String infoVendedor = "Nome: " + vendedorLogado.getNome() + "\n" +
                "Email: " + vendedorLogado.getEmail() + "\n" +
                "CNPJ: " + vendedorLogado.getCPF();

        infoTextArea.setText(infoVendedor);

    }

    public static void main(String[] args) {
        Vendedor vendedorLogado = new Vendedor("Nome", "vendedor@example.com", "senha", "12345678901234");
        SwingUtilities.invokeLater(() -> new PerfilvendedorGUI(vendedorLogado).setVisible(true));
    }

}
