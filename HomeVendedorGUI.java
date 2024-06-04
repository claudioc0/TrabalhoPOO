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
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Bem-vindo, " + vendedor.getNome() + "!", JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Painel central para os botões
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        anunciarJogosButton = new JButton("Anunciar Jogos");
        centerPanel.add(anunciarJogosButton);

        visualizarPerfilButton = new JButton("Visualizar Perfil");
        centerPanel.add(visualizarPerfilButton);

        visualizarVendasButton = new JButton("Visualizar Vendas");
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
                new VisualizarVendasGUI(vendedor).setVisible(true);
            }
        });
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
