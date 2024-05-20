import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteMainGUI extends JFrame {
    private JButton loginButton;
    private JButton cadastroButton;

    public ClienteMainGUI() {
        setTitle("Bem-vindo");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configura o layout do painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Escolha uma opção:", JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Painel central para os botões
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Botão de Login
        loginButton = new JButton("Login");
        centerPanel.add(loginButton);

        // Botão de Cadastro
        cadastroButton = new JButton("Cadastro");
        centerPanel.add(cadastroButton);

        // Adiciona o painel central
        panel.add(centerPanel, BorderLayout.CENTER);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir a tela de login
                new LoginGUI().setVisible(true);
                dispose(); // Fecha a tela atual
            }
        });

        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir a tela de cadastro
                new CadastroClienteGUI().setVisible(true);
                dispose(); // Fecha a tela atual
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClienteMainGUI().setVisible(true);
            }
        });
    }
}
