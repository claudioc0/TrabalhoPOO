import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelecaoUsuarioGUI extends JFrame {
    public SelecaoUsuarioGUI() {
        setTitle("Seleção de Tipo de Usuário");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JButton clienteButton = new JButton("Cliente");
        JButton vendedorButton = new JButton("Vendedor");

        panel.add(clienteButton);
        panel.add(vendedorButton);

        clienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteMainGUI().setVisible(true);
                dispose();
            }
        });

        vendedorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VendedorMainGUI().setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SelecaoUsuarioGUI().setVisible(true);
            }
        });
    }
}
