import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;

public class HistoricoComprasGUI extends JFrame {
    public HistoricoComprasGUI() {
        setTitle("Histórico de Compras");
        setSize(600, 400);
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

    private boolean jogoJaAvaliado(String nomeVendedor, String nomeJogo) {
        File file = new File(nomeVendedor + "_avaliacoes.txt");
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(nomeJogo)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
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

        JPanel avaliacaoPanel = new JPanel();
        ButtonGroup grupoEstrelas = new ButtonGroup();
        for (int i = 1; i <= 5; i++) {
            JRadioButton radioButton = new JRadioButton(i + " estrela" + (i == 1 ? "" : "s"));
            radioButton.setActionCommand(String.valueOf(i));
            grupoEstrelas.add(radioButton);
            avaliacaoPanel.add(radioButton);
        }

        JButton avaliarButton = new JButton("Avaliar Vendedor");
        avaliarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String estrelasSelecionadas = grupoEstrelas.getSelection().getActionCommand();
                int estrelas = Integer.parseInt(estrelasSelecionadas);
                String nomeVendedor = jogo.getVendedorNome();
                String nomeJogo = jogo.getNomeJogo();
                if (jogoJaAvaliado(nomeVendedor, nomeJogo)) {
                    JOptionPane.showMessageDialog(HistoricoComprasGUI.this, "Você já avaliou este jogo.");
                    return;
                }
                try {
                    FileWriter writer = new FileWriter(nomeVendedor + "_avaliacoes.txt", true);
                    writer.write(nomeJogo + ": " + estrelas + " estrelas\n");
                    writer.close();
                    JOptionPane.showMessageDialog(HistoricoComprasGUI.this, "Avaliação do vendedor: " + estrelas + " estrelas. Avaliação salva.");
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(HistoricoComprasGUI.this, "Erro ao salvar a avaliação.");
                }
            }
        });

        detailsPanel.add(avaliacaoPanel);
        detailsPanel.add(avaliarButton);

        jogoPanel.add(detailsPanel, BorderLayout.CENTER);

        return jogoPanel;
    }
}
