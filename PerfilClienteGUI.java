import javax.swing.*;
import java.awt.*;

public class PerfilClienteGUI extends JFrame {
    private JTextArea infoTextArea;
    private Cliente clienteLogado;
    private Cliente cliente;

    public PerfilClienteGUI(Cliente cliente) {
        clienteLogado = cliente;
        setTitle("Perfil do Cliente");
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
        voltarButton.addActionListener(e -> voltarParaHome());
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        panel.add(voltarButton, BorderLayout.SOUTH);

        carregarInformacoesCliente();
    }

    private void carregarInformacoesCliente() {
        String infoCliente = "Nome: " + clienteLogado.getNome() + "\n" +
                "Email: " + clienteLogado.getEmail() + "\n" +
                "CPF: " + clienteLogado.getCPF();

        infoTextArea.setText(infoCliente);
    }

    private void voltarParaHome() {
        new HomeClienteGUI(clienteLogado).setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        Cliente clienteLogado = new Cliente("Nome do Cliente", "cliente@example.com", "senha", "12345678901");
        SwingUtilities.invokeLater(() -> new PerfilClienteGUI(clienteLogado).setVisible(true));
    }
}
