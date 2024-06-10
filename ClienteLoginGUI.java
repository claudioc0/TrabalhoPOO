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
    private JFrame homeFrame;

    public ClienteLoginGUI(JFrame homeFrame) {
        this.homeFrame = homeFrame;
        initialize();
    }

    public ClienteLoginGUI() {
        initialize();
    }

    private void initialize() {
        setTitle("Login Cliente");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        JPanel centerPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        centerPanel.add(emailField);

        centerPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        centerPanel.add(senhaField);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        southPanel.setBackground(Color.WHITE);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(25, 120, 165));
        loginButton.setForeground(Color.WHITE);
        southPanel.add(loginButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setPreferredSize(new Dimension(100, 25));
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
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
                if (homeFrame != null) {
                    homeFrame.dispose();
                }
                new ClienteMainGUI().setVisible(true);
                dispose();
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
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!", "Login", JOptionPane.INFORMATION_MESSAGE);

            // Carregar as informações do cliente logado
            Cliente cliente = carregarInformacoesCliente(email);

            ClienteLogado.setClienteLogado(cliente);

            new HomeClienteGUI(cliente).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Login", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Cliente carregarInformacoesCliente(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("clientes.txt"))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Formato: Nome: {nome}, Email: {email}, Senha: {senha}, CPF: {cpf}
                String[] dados = linha.split(", ");
                String emailCadastrado = dados[1].split(": ")[1];
                if (email.equals(emailCadastrado)) {
                    String nome = dados[0].split(": ")[1];
                    String senha = dados[2].split(": ")[1];
                    String cpf = dados[3].split(": ")[1];
                    return new Cliente(nome, email, senha, cpf);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao ler o arquivo de clientes: " + e.getMessage());
        }
        return null;
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
