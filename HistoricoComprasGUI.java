import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistoricoComprasGUI extends JFrame {
    public HistoricoComprasGUI() {
        setTitle("Histórico de Compras");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        Cliente clienteLogado = ClienteLogado.getClienteLogado();

        if (clienteLogado == null) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente está logado.");
            dispose();
            return;
        }

        List<Jogo> historicoCompras = clienteLogado.getHistoricoCompras();

        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Histórico de Compras", JLabel.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel comprasPanel = new JPanel();
        comprasPanel.setLayout(new BoxLayout(comprasPanel, BoxLayout.Y_AXIS));

        if (historicoCompras.isEmpty()) {
            comprasPanel.add(new JLabel("Nenhuma compra realizada."));
        } else {
            for (Jogo jogo : historicoCompras) {
                comprasPanel.add(criarPainelJogo(jogo));
            }
        }

        JScrollPane scrollPane = new JScrollPane(comprasPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        setContentPane(panel);
    }

    private JPanel criarPainelJogo(Jogo jogo) {
        JPanel jogoPanel = new JPanel(new BorderLayout(10, 10));
        jogoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(jogo.getNomeJogo(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        jogoPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        detailsPanel.add(new JLabel("Vendedor: " + jogo.getVendedorNome()));
        detailsPanel.add(new JLabel("Descrição: " + jogo.getDescricao()));
        detailsPanel.add(new JLabel("Gênero: " + jogo.getGeneroJogo()));
        detailsPanel.add(new JLabel("Data de Lançamento: " + jogo.getDataLancamento()));
        detailsPanel.add(new JLabel("Preço: R$" + jogo.getPrecoJogo()));

        if (jogo instanceof JogoPC) {
            detailsPanel.add(new JLabel("Requisitos PC: " + ((JogoPC) jogo).getRequisitosPc()));
        } else if (jogo instanceof JogoConsole) {
            detailsPanel.add(new JLabel("Console: " + ((JogoConsole) jogo).getConsole()));
        }

        if (jogo.getImagem() != null && !jogo.getImagem().isEmpty()) {
            JLabel imagemLabel = new JLabel();
            ImageIcon imagemIcon = new ImageIcon(jogo.getImagem());
            Image imagemRedimensionada = imagemIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));
            detailsPanel.add(imagemLabel);
        }

        jogoPanel.add(detailsPanel, BorderLayout.CENTER);

        return jogoPanel;
    }
}
