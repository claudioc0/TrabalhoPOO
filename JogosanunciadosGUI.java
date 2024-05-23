
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class JogosanunciadosGUI extends JFrame {
    private JPanel Panel;


    public JogosanunciadosGUI() {
        setTitle("Anúncios");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        Panel = new JPanel(new GridLayout(0, 2, 10, 10));
        JScrollPane scrollPane = new JScrollPane(Panel);
        add(scrollPane, BorderLayout.CENTER);

        adicionarJogo("Devil May Cry 5", "coverarts/dmc5 cover art xone.jpg");
        adicionarJogo("Tekken 8", "coverarts/tekken8coverart.jpg");
        adicionarJogo("Devil May Cry 5", "dmc5.jpg");
        adicionarJogo("Devil May Cry 5", "dmc5.jpg");
    }

    private void adicionarJogo(String nome, String Imagem) {
        ImageIcon icon = new ImageIcon(Imagem);
        JLabel labelnome = new JLabel(nome);
        JLabel labelimagem = new JLabel(icon);


        JPanel jogoPanel = new JPanel(new BorderLayout());
        jogoPanel.add(labelnome, BorderLayout.CENTER);
        jogoPanel.add(labelimagem, BorderLayout.SOUTH);


        Panel.add(jogoPanel);
        revalidate();

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JogosanunciadosGUI().setVisible(true);

        });
    }
}
