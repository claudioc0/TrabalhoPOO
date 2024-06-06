import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

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

        // Configura o layout do painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Bem-vindo, " + vendedor.getNome() + "!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Painel central para os botões
        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10)); // Adicionei uma linha para o botão de avaliações
        centerPanel.setBackground(Color.WHITE);

        anunciarJogosButton = new JButton("Anunciar Jogos");
        estilizarBotao(anunciarJogosButton);
        centerPanel.add(anunciarJogosButton);

        visualizarPerfilButton = new JButton("Visualizar Perfil");
        estilizarBotao(visualizarPerfilButton);
        centerPanel.add(visualizarPerfilButton);

        visualizarVendasButton = new JButton("Visualizar Vendas");
        estilizarBotao(visualizarVendasButton);
        centerPanel.add(visualizarVendasButton);

        visualizarAvaliacoesButton = new JButton("Visualizar Avaliações"); // Botão para visualizar avaliações
        estilizarBotao(visualizarAvaliacoesButton);
        centerPanel.add(visualizarAvaliacoesButton); // Adicionando o botão de visualizar avaliações

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
                new VisualizarVendasGUI(vendedor).setVisible(true);
            }
        });

        visualizarAvaliacoesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir a tela de visualização de avaliações
                new VisualizarAvaliacoesGUI(vendedor).setVisible(true);
            }
        });
    }

    public void estilizarBotao(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(51, 153, 255));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));


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
