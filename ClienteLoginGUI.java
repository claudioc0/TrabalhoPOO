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
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configura o layout do painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        Font font = new Font("Arial", Font.PLAIN, 16);

        // Título
        JLabel titleLabel = new JLabel("Login Cliente", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Painel central para os campos de entrada
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 2, 10, 10));
        //centerPanel.setBorder(BorderFactory.createEmptyBorder(0,50, 0, 50));

        // Adiciona campos ao painel central
        centerPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        centerPanel.add(emailField);

        centerPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        centerPanel.add(senhaField);

        // Adicionar o painel central
        panel.add(centerPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botões
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        loginButton = new JButton("Login");
        loginButton.setFont(font);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        buttonsPanel.add(loginButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setFont(font);
        voltarButton.setBackground(new Color(70, 130, 180));
        voltarButton.setForeground(Color.WHITE);
        buttonsPanel.add(voltarButton);

        panel.add(buttonsPanel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

            // Carregar as informações do cliente logado
            Cliente cliente = carregarInformacoesCliente(email);

            // Definir o cliente logado
            ClienteLogado.setClienteLogado(cliente);

            // Redirecionar para a tela principal do sistema
            new HomeClienteGUI().setVisible(true);
            dispose(); // Fecha a tela de login
        } else {
            JOptionPane.showMessageDialog(this, "Email ou senha inválidos.");
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
