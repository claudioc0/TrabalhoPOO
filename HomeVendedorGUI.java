import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeVendedorGUI extends JFrame {
    private JButton anunciarJogosButton;
    private JButton visualizarPerfilButton;
    private JButton visualizarVendasButton;
    private JButton visualizarAvaliacoesButton;
    private JButton voltarButton;
    private Vendedor vendedor;

    public HomeVendedorGUI(Vendedor vendedor) {
        this.vendedor = vendedor;
        setTitle("Home Vendedor");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Bem-vindo, " + vendedor.getNome() + "!", JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 1, 10, 10));

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

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.WHITE);
        voltarButton = new JButton("Sair");
        voltarButton.setPreferredSize(new Dimension(100, 25));
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        southPanel.add(voltarButton);
        panel.add(southPanel, BorderLayout.SOUTH);

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

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirmacao = JOptionPane.showConfirmDialog(HomeVendedorGUI.this,
                        "Tem certeza de que deseja sair?", "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {

                    new SelecaoUsuarioGUI().setVisible(true);
                    dispose();
                }
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
