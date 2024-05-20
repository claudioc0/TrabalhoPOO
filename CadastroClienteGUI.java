import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CadastroClienteGUI extends JFrame {
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField cpfField;
    private JButton cadastrarButton;

    public CadastroClienteGUI() {
        setTitle("Cadastro de Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configura o layout do painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        // Painel central para os campos de entrada
        JPanel centerPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        // Adiciona campos
        centerPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        centerPanel.add(nomeField);

        centerPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        centerPanel.add(emailField);

        centerPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        centerPanel.add(senhaField);

        centerPanel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        centerPanel.add(cpfField);

        // Adiciona o painel central
        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        cadastrarButton = new JButton("Cadastrar");
        southPanel.add(cadastrarButton);
        panel.add(southPanel, BorderLayout.SOUTH);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });
    }

    private void cadastrarCliente() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        String cpf = cpfField.getText();

        // Verifica se todos os campos foram preenchidos
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        Cliente cliente = new Cliente(nome, email, senha, cpf);

        // Validação do email
        if (!cliente.validaEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido. Por favor, insira novamente.");
            return;
        }

        // Validação do CPF
        if (!cliente.validaCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos.");
            return;
        }

        try {
            // Salvar o cliente no arquivo de texto
            BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt", true));
            writer.write("Nome: " + nome + ", Email: " + email + ", Senha: " + senha + ", CPF: " + cpf);
            writer.newLine();
            writer.close();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o cliente: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroClienteGUI().setVisible(true);
            }
        });
    }
}
