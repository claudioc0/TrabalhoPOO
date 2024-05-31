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

        // Configura o layout do painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Bem-vindo, Cliente!", JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        // Painel central para os botões
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Botão para visualizar jogos anunciados
        visualizarJogosButton = new JButton("Visualizar Jogos Anunciados");
        centerPanel.add(visualizarJogosButton);

        // Botão para visualizar perfil
        visualizarPerfilButton = new JButton("Visualizar Perfil");
        centerPanel.add(visualizarPerfilButton);

        // Botão para visualizar histórico de compras
        historicoComprasButton = new JButton("Visualizar Histórico de Compras");
        centerPanel.add(historicoComprasButton);

        // Adiciona o painel central
        panel.add(centerPanel, BorderLayout.CENTER);

        // Adiciona ações aos botões
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
                    // Se nenhum cliente estiver logado, talvez você queira redirecionar para a tela de login
                    JOptionPane.showMessageDialog(HomeClienteGUI.this, "Nenhum cliente logado.");
                    // Redirecionar para a tela de login, por exemplo:
                    new ClienteLoginGUI().setVisible(true);
                    // Fechar a tela atual
                    dispose();
                }
            }
        });

        historicoComprasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente clienteLogado = ClienteLogado.getClienteLogado();
                if (clienteLogado != null) {
                    // new HistoricoComprasGUI(clienteLogado).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(HomeClienteGUI.this, "Nenhum cliente logado.");
                    // Redirecionar para a tela de login, por exemplo:
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
