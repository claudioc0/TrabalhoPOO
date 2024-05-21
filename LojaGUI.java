import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LojaGUI extends JFrame {
    private JTextField nomeField;
    private JTextField descricaofield;

    private JTextField generoField;

    private JTextField dataField;

    private JTextField precoField;

    private JButton comprarButton;


    public LojaGUI() {
        setTitle("Loja");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6,2,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

    }



}
