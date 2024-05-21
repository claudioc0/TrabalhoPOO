import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeClienteGUI extends JFrame {
    public HomeClienteGUI() {
        setTitle("Home do Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Painel principal
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Label de boas-vindas
        JLabel welcomeLabel = new JLabel("Bem-vindo, Cliente!");
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);

        // Botão para visualizar jogos anunciados
        JButton viewGamesButton = new JButton("Visualizar Jogos Anunciados");
        viewGamesButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        viewGamesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para visualizar jogos anunciados
                System.out.println("Ação: Visualizar Jogos Anunciados");
            }
        });
        panel.add(viewGamesButton);

        // Botão para acessar o perfil do cliente
        JButton profileButton = new JButton("Meu Perfil");
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para acessar o perfil do cliente
                System.out.println("Ação: Acessar Perfil do Cliente");
            }
        });
        panel.add(profileButton);

        // Botão para acessar o histórico de compras do cliente
        JButton purchaseHistoryButton = new JButton("Histórico de Compras");
        purchaseHistoryButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        purchaseHistoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lógica para acessar o histórico de compras do cliente
                System.out.println("Ação: Acessar Histórico de Compras do Cliente");
            }
        });
        panel.add(purchaseHistoryButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new HomeClienteGUI();
            }
        });
    }
}
