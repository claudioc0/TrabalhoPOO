import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomeClienteGUI extends JFrame {
    ArrayList<Jogo> jogosAnunciados = new ArrayList<>();

    private JButton visualizarJogosButton;
    private JButton visualizarPerfilButton;
    private JButton historicoComprasButton;

    public HomeClienteGUI() {
        setTitle("Home Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Bem-vindo, Cliente!", JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        visualizarJogosButton = new JButton("Visualizar Jogos Anunciados");
        visualizarJogosButton.setBackground(new Color(25, 120, 165)); // Azul
        visualizarJogosButton.setForeground(Color.WHITE); // Texto branco
        centerPanel.add(visualizarJogosButton);

        visualizarPerfilButton = new JButton("Visualizar Perfil");
        visualizarPerfilButton.setBackground(new Color(25, 120, 165)); // Azul
        visualizarPerfilButton.setForeground(Color.WHITE); // Texto branco
        centerPanel.add(visualizarPerfilButton);

        historicoComprasButton = new JButton("Visualizar Histórico de Compras");
        historicoComprasButton.setBackground(new Color(25, 120, 165)); // Azul
        historicoComprasButton.setForeground(Color.WHITE); // Texto branco
        centerPanel.add(historicoComprasButton);

        panel.add(centerPanel, BorderLayout.CENTER);

        visualizarJogosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abrir a tela de visualização de jogos anunciados
                new VisualizarJogosGUI(jogosAnunciados).setVisible(true);
            }
        });

        visualizarPerfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente clienteLogado = ClienteLogado.getClienteLogado();
                if (clienteLogado != null) {
                    new PerfilClienteGUI(clienteLogado).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(HomeClienteGUI.this, "Nenhum cliente logado.");
                    new ClienteLoginGUI().setVisible(true);
                    dispose();
                }
            }
        });

        historicoComprasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente clienteLogado = ClienteLogado.getClienteLogado();
                if (clienteLogado != null) {
                    new HistoricoComprasGUI().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(HomeClienteGUI.this, "Nenhum cliente logado.");
                    new ClienteLoginGUI().setVisible(true);
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeClienteGUI().setVisible(true);
            }
        });
    }
}
