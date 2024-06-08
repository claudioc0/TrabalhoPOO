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
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Escolha uma opção:", JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(25, 120, 165));
        loginButton.setForeground(Color.WHITE);
        centerPanel.add(loginButton);

        cadastroButton = new JButton("Cadastro");
        cadastroButton.setBackground(new Color(25, 120, 165));
        cadastroButton.setForeground(Color.WHITE);
        centerPanel.add(cadastroButton);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        southPanel.setBackground(Color.WHITE);
        voltarButton = new JButton("Voltar");
        voltarButton.setPreferredSize(new Dimension(100, 25));
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        southPanel.add(voltarButton);

        panel.add(southPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteLoginGUI().setVisible(true);
                dispose();
            }
        });

        cadastroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroClienteGUI().setVisible(true);
                dispose();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SelecaoUsuarioGUI().setVisible(true);
                dispose();
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
