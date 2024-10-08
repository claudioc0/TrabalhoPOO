import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnunciarJogoGUI extends JFrame {
    private List<Jogo> jogosAnunciados;
    private File arquivoJogosAnunciados;

    private JTextField nomeField;
    private JTextField descricaoField;
    private JTextField generoField;
    private JTextField dataLancamentoField;
    private JTextField precoField;
    private JTextField requisitosPcField;
    private JTextField consoleField;
    private JButton anunciarButton;
    private JButton voltarButton;
    private JComboBox<String> tipoJogoComboBox;
    private JLabel imagemLabel;
    private JButton escolherImagemButton;
    private File imagemSelecionada;

    private Vendedor vendedor;

    public AnunciarJogoGUI(Vendedor vendedor) {
        this.vendedor = vendedor;
        setTitle("Anunciar Jogo");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        arquivoJogosAnunciados = new File("jogos_anunciados.txt");
        jogosAnunciados = carregarJogosAnunciados();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JPanel centerPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(new JLabel("Tipo de Jogo:"));
        tipoJogoComboBox = new JComboBox<>(new String[]{"PC", "Console"});
        centerPanel.add(tipoJogoComboBox);

        centerPanel.add(new JLabel("Nome do Jogo:"));
        nomeField = new JTextField();
        centerPanel.add(nomeField);

        centerPanel.add(new JLabel("Descrição:"));
        descricaoField = new JTextField();
        centerPanel.add(descricaoField);

        centerPanel.add(new JLabel("Gênero:"));
        generoField = new JTextField();
        centerPanel.add(generoField);

        centerPanel.add(new JLabel("Ano de Lançamento:"));
        dataLancamentoField = new JTextField();
        centerPanel.add(dataLancamentoField);

        centerPanel.add(new JLabel("Preço:"));
        precoField = new JTextField();
        centerPanel.add(precoField);

        centerPanel.add(new JLabel("Requisitos do PC:"));
        requisitosPcField = new JTextField();
        centerPanel.add(requisitosPcField);

        centerPanel.add(new JLabel("Console:"));
        consoleField = new JTextField();
        centerPanel.add(consoleField);

        centerPanel.add(new JLabel("Imagem:"));
        JPanel imagemPanel = new JPanel(new BorderLayout());
        imagemPanel.setBackground(Color.WHITE);
        imagemLabel = new JLabel();
        imagemLabel.setHorizontalAlignment(JLabel.CENTER);
        imagemLabel.setVerticalAlignment(JLabel.CENTER);
        imagemLabel.setPreferredSize(new Dimension(150, 150));
        imagemPanel.add(imagemLabel, BorderLayout.CENTER);
        escolherImagemButton = new JButton("Escolher Imagem");

        escolherImagemButton.setForeground(Color.WHITE);
        escolherImagemButton.setBackground(new Color(25, 120, 165));
        imagemPanel.add(escolherImagemButton, BorderLayout.SOUTH);
        centerPanel.add(imagemPanel);


        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        anunciarButton = new JButton("Anunciar");
        anunciarButton.setBackground(new Color(25, 120, 165));
        anunciarButton.setForeground(Color.WHITE);
        southPanel.add(anunciarButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        southPanel.add(voltarButton);

        southPanel.setBackground(Color.WHITE);

        panel.add(southPanel, BorderLayout.SOUTH);

        tipoJogoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFieldsVisibility();
            }
        });

        escolherImagemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selecionarImagem();
            }
        });

        anunciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                anunciarJogo();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        updateFieldsVisibility();
    }


    private void updateFieldsVisibility() {
        boolean isPc = tipoJogoComboBox.getSelectedItem().equals("PC");
        requisitosPcField.setVisible(isPc);
        consoleField.setVisible(!isPc);
    }

    private void selecionarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagemSelecionada = fileChooser.getSelectedFile();
            exibirImagem();
        }
    }

    private void exibirImagem() {
        if (imagemSelecionada != null) {
            ImageIcon imagemIcon = new ImageIcon(imagemSelecionada.getPath());
            Image imagemRedimensionada = imagemIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));
            imagemLabel.revalidate();
            imagemLabel.repaint();
        }
    }

    private void anunciarJogo() {
        String tipoJogo = (String) tipoJogoComboBox.getSelectedItem();
        String nome = nomeField.getText();
        String descricao = descricaoField.getText();
        String genero = generoField.getText();
        String dataLancamento = dataLancamentoField.getText();
        String precoText = precoField.getText();
        String imagem = imagemSelecionada != null ? imagemSelecionada.getPath() : null;

        if (nome.isEmpty() || descricao.isEmpty() || genero.isEmpty() || dataLancamento.isEmpty() || precoText.isEmpty() ||
                (tipoJogo.equals("PC") && requisitosPcField.getText().isEmpty()) ||
                (tipoJogo.equals("Console") && consoleField.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        int anoAtual = LocalDate.now().getYear();
        int anoLancamento;
        try {
            anoLancamento = Integer.parseInt(dataLancamento.split("-")[0]);
            if (anoLancamento > anoAtual || anoLancamento < 1971) {
                JOptionPane.showMessageDialog(this, "O ano de lançamento deve ser entre 1971 e o ano atual.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Data de lançamento inválida.");
            return;
        }

        double preco;
        try {
            preco = Double.parseDouble(precoText);
            if (preco <= 0) {
                JOptionPane.showMessageDialog(this, "O preço deve ser maior que zero.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Por favor, insira um número válido.");
            return;
        }

        Jogo jogo;
        if (tipoJogo.equals("PC")) {
            String requisitosPc = requisitosPcField.getText();
            jogo = new JogoPC(nome, descricao, genero, dataLancamento, preco, imagem, requisitosPc, vendedor.getNome());
        } else {
            String console = consoleField.getText();
            jogo = new JogoConsole(nome, descricao, genero, dataLancamento, preco, imagem, console, vendedor.getNome());
        }

        jogosAnunciados.add(jogo);
        salvarJogosAnunciados(jogosAnunciados);

        JOptionPane.showMessageDialog(this, "Jogo anunciado com sucesso!");
        clearFields();
    }


    private void clearFields() {
        nomeField.setText("");
        descricaoField.setText("");
        generoField.setText("");
        dataLancamentoField.setText("");
        precoField.setText("");
        requisitosPcField.setText("");
        consoleField.setText("");
        imagemLabel.setIcon(null);
        imagemSelecionada = null;
    }

    private void salvarJogosAnunciados(List<Jogo> jogos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoJogosAnunciados))) {
            for (Jogo jogo : jogos) {
                writer.write(jogo.toTexto());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Jogo> carregarJogosAnunciados() {
        List<Jogo> jogos = new ArrayList<>();
        if (!arquivoJogosAnunciados.exists()) {
            return jogos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoJogosAnunciados))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Jogo jogo = Jogo.fromTexto(linha);
                if (jogo != null) {
                    jogos.add(jogo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jogos;
    }
}
