import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteMainGUI extends JFrame {
    private JButton loginButton;
    private JButton cadastroButton;
    private JButton voltarButton;

    public ClienteMainGUI() {
        setTitle("Bem-vindo Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configura o layout do painel principal
        JPanel panel = new JPanel(new BorderLayout());
        //panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        Font font = new Font("Arial", Font.PLAIN, 16);

        JLabel welcomeLabel = new JLabel("Escolha uma opção:", JLabel.CENTER);
        welcomeLabel.setFont(font);
        panel.add(welcomeLabel, BorderLayout.NORTH);
        //panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Painel central para os botões
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 10, 10));

        // Botão de Login
        loginButton = new JButton("Login");
        loginButton.setFont(font);
        loginButton.setBackground(new Color(70, 130, 180));
        loginButton.setForeground(Color.WHITE);
        centerPanel.add(loginButton);



        // Botão de Cadastro
        cadastroButton = new JButton("Cadastro");
        cadastroButton.setFont(font);
        cadastroButton.setBackground(new Color(70, 130, 180));
        cadastroButton.setForeground(Color.WHITE);
        centerPanel.add(cadastroButton);


        panel.add(centerPanel, BorderLayout.CENTER);
       // panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botão de Voltar
        voltarButton = new JButton("Voltar");
        voltarButton.setFont(font);
        voltarButton.setBackground(new Color(70, 130, 180));
        voltarButton.setForeground(Color.WHITE);
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.add(voltarButton);
        //voltarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(southPanel, BorderLayout.SOUTH);






        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir a tela de login
                new ClienteLoginGUI().setVisible(true);
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

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SelecaoUsuarioGUI().setVisible(true);
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
