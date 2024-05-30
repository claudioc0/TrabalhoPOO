import javax.swing.*;
import java.awt.*;

public class DetalhesJogoGUI extends JFrame {
    public DetalhesJogoGUI(Jogo jogo) {
        setTitle("Detalhes do Jogo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JLabel titleLabel = new JLabel(jogo.getNomeJogo(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(0, 1, 10, 10));

        centerPanel.add(new JLabel("Vendedor: " + jogo.getVendedorNome()));
        centerPanel.add(new JLabel("Descrição: " + jogo.getDescricao()));
        centerPanel.add(new JLabel("Gênero: " + jogo.getGeneroJogo()));
        centerPanel.add(new JLabel("Data de Lançamento: " + jogo.getDataLancamento()));
        centerPanel.add(new JLabel("Preço: R$" + jogo.getPrecoJogo()));

        if (jogo instanceof JogoPC) {
            centerPanel.add(new JLabel("Requisitos PC: " + ((JogoPC) jogo).getRequisitosPc()));
        } else if (jogo instanceof JogoConsole) {
            centerPanel.add(new JLabel("Console: " + ((JogoConsole) jogo).getConsole()));
        }

        if (jogo.getImagem() != null && !jogo.getImagem().isEmpty()) {
            JLabel imagemLabel = new JLabel();
            ImageIcon imagemIcon = new ImageIcon(jogo.getImagem());
            Image imagemRedimensionada = imagemIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));
            centerPanel.add(imagemLabel);
        }

        panel.add(centerPanel, BorderLayout.CENTER);

        JButton comprarButton = new JButton("Comprar");
        comprarButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Compra efetuada com sucesso!"));
        panel.add(comprarButton, BorderLayout.SOUTH);
    }
}
