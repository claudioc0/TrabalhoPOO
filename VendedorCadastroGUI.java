import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class VendedorCadastroGUI extends JFrame {
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField cpfField;
    private JButton cadastrarButton;
    private JButton voltarButton;

    public VendedorCadastroGUI() {
        setTitle("Cadastro de Vendedor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JPanel centerPanel = new JPanel(new GridLayout(4, 2, 10, 10));

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

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS)); // Altera o layout para BoxLayout vertical

        cadastrarButton = new JButton("Cadastrar");
        southPanel.add(cadastrarButton);

        voltarButton = new JButton("Voltar");
        southPanel.add(voltarButton);

        panel.add(southPanel, BorderLayout.SOUTH);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarVendedor();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });
    }

    private void cadastrarVendedor() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        String cpf = cpfField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        Vendedor vendedor = new Vendedor(nome, email, senha, cpf);

        if (!vendedor.validaEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido. Por favor, insira novamente.");
            return;
        }

        if (!vendedor.validaCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("vendedores.txt", true))) {
            writer.write("Nome: " + nome + ", Email: " + email + ", Senha: " + senha + ", CPF: " + cpf);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Vendedor cadastrado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os dados do vendedor: " + e.getMessage());
        }
    }

    private void voltar() {
        dispose(); // Fecha a tela de cadastro
        new VendedorMainGUI().setVisible(true); // Abre a tela principal do cliente
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VendedorCadastroGUI().setVisible(true);
            }
        });
    }
}
