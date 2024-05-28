import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AnunciarJogoGUI extends JFrame {
    private List<Jogo> jogosAnunciados; // Lista de jogos anunciados
    private File arquivoJogosAnunciados; // Arquivo para armazenar os jogos anunciados

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
        setSize(400, 500); // Increased height to accommodate the image field
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicializa o arquivo para armazenar os jogos anunciados
        arquivoJogosAnunciados = new File("jogos_anunciados.txt");

        // Carrega os jogos anunciados do arquivo, se existir
        jogosAnunciados = carregarJogosAnunciados();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JPanel centerPanel = new JPanel(new GridLayout(9, 2, 10, 10));

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

        centerPanel.add(new JLabel("Data de Lançamento:"));
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

        // Image Selection
        centerPanel.add(new JLabel("Imagem:"));
        JPanel imagemPanel = new JPanel(new BorderLayout());
        imagemLabel = new JLabel();
        imagemPanel.add(imagemLabel, BorderLayout.CENTER);
        escolherImagemButton = new JButton("Escolher Imagem");
        imagemPanel.add(escolherImagemButton, BorderLayout.SOUTH);
        centerPanel.add(imagemPanel);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel();
        anunciarButton = new JButton("Anunciar");
        southPanel.add(anunciarButton);

        voltarButton = new JButton("Voltar");
        southPanel.add(voltarButton);

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
        ImageIcon imagemIcon = new ImageIcon(imagemSelecionada.getPath());
        // Redimensiona a imagem para caber no JLabel
        Image imagemRedimensionada = imagemIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));
    }

    private void anunciarJogo() {
        String tipoJogo = (String) tipoJogoComboBox.getSelectedItem();
        String nome = nomeField.getText();
        String descricao = descricaoField.getText();
        String genero = generoField.getText();
        String dataLancamento = dataLancamentoField.getText();
        String precoText = precoField.getText();
        String imagem = imagemSelecionada != null ? imagemSelecionada.getPath() : null; // Caminho da imagem selecionada

        if (nome.isEmpty() || descricao.isEmpty() || genero.isEmpty() || dataLancamento.isEmpty() || precoText.isEmpty() ||
                (tipoJogo.equals("PC") && requisitosPcField.getText().isEmpty()) ||
                (tipoJogo.equals("Console") && consoleField.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        double preco;
        try {
            preco = Double.parseDouble(precoText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Por favor, insira um número válido.");
            return;
        }

        Jogo jogo;
        if (tipoJogo.equals("PC")) {
            String requisitosPc = requisitosPcField.getText();
            jogo = new JogoPC(nome, descricao, genero, dataLancamento, preco, imagem, requisitosPc);
        } else {
            String console = consoleField.getText();
            jogo = new JogoConsole(nome, descricao, genero, dataLancamento, preco, imagem, console);
        }

        // Adiciona o jogo à lista de jogos anunciados
        if (jogosAnunciados == null) {
            jogosAnunciados = new ArrayList<>();
        }
        jogosAnunciados.add(jogo);

        // Persiste os jogos anunciados no arquivo
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
        // Limpa também a imagem exibida
        imagemLabel.setIcon(null);
        // Reseta a variável de imagem selecionada
        imagemSelecionada = null;
    }

    private void salvarJogosAnunciados(List<Jogo> jogos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(arquivoJogosAnunciados))) {
            for (Jogo jogo : jogos) {
                writer.println(jogo.toTexto()); // Supondo que você tenha um método toTexto() na classe Jogo
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os jogos anunciados: " + e.getMessage());
        }
    }


    private List<Jogo> carregarJogosAnunciados() {
        List<Jogo> jogos = new ArrayList<>();
        if (arquivoJogosAnunciados.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(arquivoJogosAnunciados))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Jogo jogo = Jogo.fromTexto(line); // Supondo que você tenha um método estático fromTexto() na classe Jogo
                    if (jogo != null) {
                        jogos.add(jogo);
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao carregar os jogos anunciados: " + e.getMessage());
            }
        }
        return jogos;
    }


}
