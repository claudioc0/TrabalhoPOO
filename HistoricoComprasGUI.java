import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;

public class HistoricoComprasGUI extends JFrame {
    private Cliente cliente;

    public HistoricoComprasGUI(Cliente cliente) {
        this.cliente = cliente;
        setTitle("Histórico de Compras");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente está logado.");
            dispose();
            return;
        }

        List<Jogo> historicoCompras = cliente.getHistoricoCompras();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Histórico de Compras", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel comprasPanel = new JPanel();
        comprasPanel.setLayout(new BoxLayout(comprasPanel, BoxLayout.Y_AXIS));
        comprasPanel.setBackground(Color.WHITE);

        if (historicoCompras.isEmpty()) {
            JLabel noComprasLabel = new JLabel("Nenhuma compra realizada.");
            noComprasLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            comprasPanel.add(noComprasLabel);
        } else {
            for (Jogo jogo : historicoCompras) {
                comprasPanel.add(criarPainelJogo(jogo));
            }
        }

        JScrollPane scrollPane = new JScrollPane(comprasPanel);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarParaHomeCliente();
            }
        });
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        voltarButton.setFont(new Font("Arial", Font.BOLD, 16));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(voltarButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

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
        jogoPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel(jogo.getNomeJogo(), JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        jogoPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel(new GridLayout(0, 1, 5, 5)); // Reduzindo o espaçamento aqui
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.add(new JLabel("Vendedor: " + jogo.getVendedorNome()));
        detailsPanel.add(new JLabel("Descrição: " + jogo.getDescricao()));
        detailsPanel.add(new JLabel("Gênero: " + jogo.getGeneroJogo()));
        detailsPanel.add(new JLabel("Ano de Lançamento: " + jogo.getDataLancamento()));
        detailsPanel.add(new JLabel("Preço: R$" + jogo.getPrecoJogo()));

        if (jogo instanceof JogoPC) {
            detailsPanel.add(new JLabel("Requisitos PC: " + ((JogoPC) jogo).getRequisitosPc()));
        } else if (jogo instanceof JogoConsole) {
            detailsPanel.add(new JLabel("Console: " + ((JogoConsole) jogo).getConsole()));
        }

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        if (jogo.getImagem() != null && !jogo.getImagem().isEmpty()) {
            JLabel imagemLabel = new JLabel();
            ImageIcon imagemIcon = new ImageIcon(jogo.getImagem());
            Image imagemRedimensionada = imagemIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));
            rightPanel.add(imagemLabel, BorderLayout.NORTH);
        }

        JPanel avaliacaoPanel = new JPanel();
        avaliacaoPanel.setLayout(new BoxLayout(avaliacaoPanel, BoxLayout.Y_AXIS));
        avaliacaoPanel.setBackground(Color.WHITE);

        JPanel estrelasPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        estrelasPanel.setBackground(Color.WHITE);
        ButtonGroup grupoEstrelas = new ButtonGroup();
        for (int i = 1; i <= 5; i++) {
            JRadioButton radioButton = new JRadioButton(i + " estrela" + (i == 1 ? "" : "s"));
            radioButton.setActionCommand(String.valueOf(i));
            grupoEstrelas.add(radioButton);
            radioButton.setBackground(Color.WHITE);
            estrelasPanel.add(radioButton);
        }

        JButton avaliarButton = new JButton("Avaliar Vendedor");
        avaliarButton.setBackground(new Color(25, 120, 165));
        avaliarButton.setForeground(Color.WHITE);
        avaliarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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

        avaliacaoPanel.add(estrelasPanel);
        avaliacaoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        avaliacaoPanel.add(avaliarButton);

        detailsPanel.add(avaliacaoPanel);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Reduzindo o espaçamento aqui
        mainPanel.setBackground(Color.WHITE);
        mainPanel.add(detailsPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        jogoPanel.add(mainPanel, BorderLayout.CENTER);

        return jogoPanel;
    }


    private void voltarParaHomeCliente() {
        HomeClienteGUI homeClienteGUI = new HomeClienteGUI(cliente);
        homeClienteGUI.setVisible(true);
        dispose();
    }
}
