import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeVendedorGUI extends JFrame {
    private JButton anunciarJogosButton;
    private JButton visualizarPerfilButton;
    private JButton visualizarVendasButton;
    private Vendedor vendedor;

    public HomeVendedorGUI(Vendedor vendedor) {
        this.vendedor = vendedor;
        setTitle("Home Vendedor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configura o layout do painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Bem-vindo, Vendedor!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(60, 63, 65));
        welcomeLabel.setForeground(Color.BLACK);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Painel central para os botões
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        anunciarJogosButton = createButton("Anunciar Jogos");
        centerPanel.add(anunciarJogosButton);


        visualizarPerfilButton = createButton("Visualizar Perfil");
        centerPanel.add(visualizarPerfilButton);

        visualizarVendasButton = createButton("Visualizar Vendas");
        centerPanel.add(visualizarVendasButton);

        // Adiciona o painel central
        panel.add(centerPanel, BorderLayout.CENTER);

        anunciarJogosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AnunciarJogoGUI(vendedor).setVisible(true);
            }
        });

        visualizarPerfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir a tela de visualização do perfil
                new PerfilVendedorGUI(vendedor).setVisible(true);
            }
        });

        visualizarVendasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir a tela de visualização de vendas
                new VisualizarVendasGUI().setVisible(true);
            }
        });
    }

    //private JButton


    private JButton createButton(String text) {
        JButton button = new JButton(text) {
            protected void paintComponent(Graphics g) {
                if (getModel().isArmed()) {
                    g.setColor(new Color(100, 149, 237));
                } else {
                    g.setColor(new Color(70, 130, 180));
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }


            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setContentAreaFilled(false);
                setFocusPainted(false);
                setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            }
        };
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        // Crie uma instância de Vendedor para teste
        Vendedor vendedor = new Vendedor("Vendedor Teste", "vendedor@teste.com", "senha123", "12345678901");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeVendedorGUI(vendedor).setVisible(true);
            }
        });
    }
}


class VisualizarVendasGUI extends JFrame {
    public VisualizarVendasGUI() {
        setTitle("Visualizar Vendas");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Histórico de Vendas", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        add(label);
    }
}
