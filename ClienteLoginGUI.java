import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ClienteLoginGUI extends JFrame {
    private JTextField emailField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JButton voltarButton;

    public ClienteLoginGUI() {
        setTitle("Login Cliente");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configura o layout do painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        // Painel central
        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Adiciona campos ao painel central
        centerPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        centerPanel.add(emailField);

        centerPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        centerPanel.add(senhaField);

        // Adicionar o painel central
        panel.add(centerPanel, BorderLayout.CENTER);

        // Painel sul para os botões
        JPanel southPanel = new JPanel();
        loginButton = new JButton("Login");
        southPanel.add(loginButton);

        voltarButton = new JButton("Voltar");
        southPanel.add(voltarButton);

        panel.add(southPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Voltar para a tela anterior
                // Por exemplo, para a tela de escolha entre cliente e vendedor
                new ClienteMainGUI().setVisible(true);
                dispose(); // Fecha a tela atual
            }
        });
    }

    private void realizarLogin() {
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        // Verifica se todos os campos foram preenchidos
        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        // Valida o login verificando o arquivo clientes.txt
        boolean loginValido = false;
        try (BufferedReader reader = new BufferedReader(new FileReader("clientes.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Formato: Nome: {nome}, Email: {email}, Senha: {senha}, CPF: {cpf}
                String[] dados = linha.split(", ");
                String emailCadastrado = dados[1].split(": ")[1];
                String senhaCadastrada = dados[2].split(": ")[1];

                if (email.equals(emailCadastrado) && senha.equals(senhaCadastrada)) {
                    loginValido = true;
                    break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de clientes: " + e.getMessage());
            return;
        }

        if (loginValido) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            // Redirecionar para a tela principal do sistema, por exemplo:
            // new TelaPrincipalGUI().setVisible(true);
            dispose(); // Fecha a tela de login
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha inválidos.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClienteLoginGUI().setVisible(true);
            }
        });
    }
}
