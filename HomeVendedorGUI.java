import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeVendedorGUI extends JFrame {
    private JButton anunciarJogosButton;
    private JButton visualizarPerfilButton;
    private JButton visualizarVendasButton;
    private JButton visualizarAvaliacoesButton; // Botão para visualizar avaliações
    private Vendedor vendedor;

    public HomeVendedorGUI(Vendedor vendedor) {
        this.vendedor = vendedor;
        setTitle("Home Vendedor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Bem-vindo, " + vendedor.getNome() + "!", JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Adicionei uma linha para o botão de avaliações

        anunciarJogosButton = new JButton("Anunciar Jogos");
        anunciarJogosButton.setBackground(new Color(25, 120, 165));
        anunciarJogosButton.setForeground(Color.WHITE);
        centerPanel.add(anunciarJogosButton);

        visualizarPerfilButton = new JButton("Visualizar Perfil");
        visualizarPerfilButton.setBackground(new Color(25, 120, 165));
        visualizarPerfilButton.setForeground(Color.WHITE);
        centerPanel.add(visualizarPerfilButton);

        visualizarVendasButton = new JButton("Visualizar Vendas");
        visualizarVendasButton.setBackground(new Color(25, 120, 165));
        visualizarVendasButton.setForeground(Color.WHITE);
        centerPanel.add(visualizarVendasButton);

        visualizarAvaliacoesButton = new JButton("Visualizar Avaliações");
        visualizarAvaliacoesButton.setBackground(new Color(25, 120, 165));
        visualizarAvaliacoesButton.setForeground(Color.WHITE);
        centerPanel.add(visualizarAvaliacoesButton);

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
                new PerfilVendedorGUI(vendedor).setVisible(true);
            }
        });

        visualizarVendasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizarVendasGUI(vendedor).setVisible(true);
            }
        });

        visualizarAvaliacoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VisualizarAvaliacoesGUI(vendedor).setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        Vendedor vendedor = new Vendedor("Vendedor Teste", "vendedor@teste.com", "senha123", "12345678901");

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeVendedorGUI(vendedor).setVisible(true);
            }
        });
    }
}
