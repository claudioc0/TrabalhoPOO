import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PerfilClienteGUI extends JFrame {
    private JTextArea infoTextArea;
    private Cliente clienteLogado; // Variável para armazenar o cliente logado

    public PerfilClienteGUI(Cliente cliente) { // Passa o cliente logado como parâmetro
        clienteLogado = cliente; // Armazena o cliente logado
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
        voltarButton.addActionListener(e -> {
            // Implemente a lógica para voltar à tela anterior
            // Por exemplo, tela principal do cliente
        });
        panel.add(voltarButton, BorderLayout.SOUTH);

        carregarInformacoesCliente();
    }

    private void carregarInformacoesCliente() {
        // Carregar as informações apenas do cliente logado
        String infoCliente = "Nome: " + clienteLogado.getNome() + "\n" +
                "Email: " + clienteLogado.getEmail() + "\n" +
                "CPF: " + clienteLogado.getCPF(); // Adicione outros campos conforme necessário

        infoTextArea.setText(infoCliente);
    }

    public static void main(String[] args) {
        // Exemplo de uso
        Cliente clienteLogado = new Cliente("Nome do Cliente", "cliente@example.com", "senha", "12345678901");
        SwingUtilities.invokeLater(() -> new PerfilClienteGUI(clienteLogado).setVisible(true));
    }
}
