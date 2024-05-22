import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class VendedorLoginGUI extends JFrame {
    private JTextField emailField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JButton voltarButton;

    public VendedorLoginGUI() {
        setTitle("Login Vendedor");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        centerPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        centerPanel.add(emailField);

        centerPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        centerPanel.add(senhaField);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        loginButton = new JButton("Login");
        southPanel.add(loginButton);
        panel.add(southPanel, BorderLayout.SOUTH);

        voltarButton = new JButton("Voltar");
        panel.add(voltarButton, BorderLayout.NORTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VendedorMainGUI().setVisible(true);
                dispose(); // Fecha a tela atual
            }
        });
    }

    private void realizarLogin() {
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        boolean loginValido = false;
        String nomeVendedor = null;
        String cpfVendedor = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("vendedores.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(", ");
                String emailCadastrado = dados[1].split(": ")[1];
                String senhaCadastrada = dados[2].split(": ")[1];
                nomeVendedor = dados[0].split(": ")[1]; // Obtém o nome do vendedor
                cpfVendedor = dados[3].split(": ")[1]; // Obtém o CPF do vendedor

                if (email.equals(emailCadastrado) && senha.equals(senhaCadastrada)) {
                    loginValido = true;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de vendedores: " + e.getMessage());
            return;
        }

        if (loginValido) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            // Redirecionar para a tela principal do sistema
            Vendedor vendedor = new Vendedor(nomeVendedor, email, senha, cpfVendedor);
            new HomeVendedorGUI(vendedor).setVisible(true);
            dispose(); // Fecha a tela de login

        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha inválidos.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VendedorLoginGUI().setVisible(true);
            }
        });
    }
}
