import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeVendedorGUI extends JFrame {
    private JButton anunciarJogoButton;
    private JButton visualizarPerfilButton;
    private JButton visualizarVendasButton;

    public HomeVendedorGUI() {
        setTitle("Home Vendedor");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        anunciarJogoButton = new JButton("Anunciar Jogo");
        visualizarPerfilButton = new JButton("Visualizar Perfil");
        visualizarVendasButton = new JButton("Visualizar Vendas");

        panel.add(anunciarJogoButton);
        panel.add(visualizarPerfilButton);
        panel.add(visualizarVendasButton);

        anunciarJogoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abra a tela de anunciar jogo
                // Você pode implementar essa tela em uma nova classe
            }
        });

        visualizarPerfilButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abra a tela de visualizar perfil
                // Você pode implementar essa tela em uma nova classe
            }
        });

        visualizarVendasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abra a tela de visualizar vendas
                // Você pode implementar essa tela em uma nova classe
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeVendedorGUI().setVisible(true);
            }
        });
    }
}
