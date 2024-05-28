
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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        Panel = new JPanel(new GridLayout(0, 2, 10, 10));
        Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Panel.setBackground(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(Panel);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Jogos Anunciados"));
        add(scrollPane, BorderLayout.CENTER);

        adicionarJogo("Devil May Cry 5", "coverarts1/dmc5 cover art xone.jpg", 99.99);
        adicionarJogo("TEKKEN 8", "coverarts1/tekken8coverart.jpg", 299.99);
        adicionarJogo("Danganronpa Trigger Happy Havoc", "coverarts1/danganronpa_psvita.jpeg", 34.99);
        adicionarJogo("Yakuza 0", "coverarts1/Yakuza0_ps4.jpeg", 83.99);
        adicionarJogo("OMORI", "coverarts1/OMORI_PC.png", 36.99);
        adicionarJogo("Persona 3 Reload", "coverarts1/PERSONA3_RELOAD_XSERIESX.jpeg", 349.99);
        adicionarJogo("Devil May Cry 4", "coverarts1/dmc4_x360.jpeg", 83.99);
        adicionarJogo("Persona 5 Royal", "coverarts1/P5ROYAL_ps4.jpeg", 299.99);


        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(voltarButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void adicionarJogo(String nome, String Imagem, double preco) {
        ImageIcon icon = new ImageIcon(Imagem);
        JLabel labelnome = new JLabel(nome);
        JLabel labelimagem = new JLabel(icon);

        if (icon.getIconWidth() == -1) {
           labelimagem.setText("Imagem não encontrada");
           labelimagem.setHorizontalAlignment(JLabel.CENTER);
        } else {
            labelimagem.setIcon(icon);
        }


        JLabel labelPreco = new JLabel("Preço: R$" + preco, JLabel.CENTER);
        labelPreco.setFont(new Font("Arial", Font.BOLD, 12));

        JButton comprarButton = new JButton("Comprar");
        comprarButton.addActionListener(e -> JOptionPane.showMessageDialog(this,"compra realizada para: " + nome));


        JPanel jogoPanel = new JPanel(new BorderLayout());
        jogoPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
        jogoPanel.setBackground(Color.WHITE);

        labelnome.setFont(new Font("Arial", Font.BOLD, 14));
        labelnome.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(labelPreco, BorderLayout.NORTH);
        bottomPanel.add(comprarButton, BorderLayout.SOUTH);

        jogoPanel.add(labelnome, BorderLayout.NORTH);
        jogoPanel.add(labelimagem, BorderLayout.CENTER);
        jogoPanel.add(bottomPanel, BorderLayout.SOUTH);



        Panel.add(jogoPanel);
        revalidate();
        repaint();

    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JogosanunciadosGUI().setVisible(true);

        });
    }
}
