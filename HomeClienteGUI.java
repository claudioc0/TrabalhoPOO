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
    private JButton voltarButton;
    private Cliente cliente;

    public HomeClienteGUI(Cliente cliente) {
        this.cliente = cliente;
        setTitle("Home Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        JLabel welcomeLabel = new JLabel("Bem-vindo, " + cliente.getNome() + "!", JLabel.CENTER);
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        visualizarJogosButton = new JButton("Visualizar Jogos Anunciados");
        visualizarJogosButton.setBackground(new Color(25, 120, 165));
        visualizarJogosButton.setForeground(Color.WHITE);
        centerPanel.add(visualizarJogosButton);

        visualizarPerfilButton = new JButton("Visualizar Perfil");
        visualizarPerfilButton.setBackground(new Color(25, 120, 165));
        visualizarPerfilButton.setForeground(Color.WHITE);
        centerPanel.add(visualizarPerfilButton);

        historicoComprasButton = new JButton("Visualizar Histórico de Compras");
        historicoComprasButton.setBackground(new Color(25, 120, 165));
        historicoComprasButton.setForeground(Color.WHITE);
        centerPanel.add(historicoComprasButton);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.WHITE);
        voltarButton = new JButton("Sair");
        voltarButton.setPreferredSize(new Dimension(100, 25));
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        southPanel.add(voltarButton);
        panel.add(southPanel, BorderLayout.SOUTH);

        visualizarJogosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cliente clienteLogado = ClienteLogado.getClienteLogado();
                if (clienteLogado != null) {
                    new VisualizarJogosGUI(jogosAnunciados, clienteLogado).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(HomeClienteGUI.this, "Nenhum cliente logado.");
                    new ClienteLoginGUI().setVisible(true);
                    dispose();
                }
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
                    new HistoricoComprasGUI(clienteLogado).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(HomeClienteGUI.this, "Nenhum cliente logado.");
                    new ClienteLoginGUI().setVisible(true);
                    dispose();
                }
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int escolha = JOptionPane.showConfirmDialog(HomeClienteGUI.this, "Deseja realmente sair?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (escolha == JOptionPane.YES_OPTION) {
                    new SelecaoUsuarioGUI().setVisible(true);
                    dispose();
                }
            }
        });
    }

    public static void main(String[] args) {
        Cliente cliente1 = new Cliente("Cliente teste", "cliente@gmail.com", "cliente123", "12345678901");
        ClienteLogado.setClienteLogado(cliente1);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeClienteGUI(cliente1).setVisible(true);
            }
        });
    }
}
