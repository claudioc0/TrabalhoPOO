import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AnunciarJogoGUI extends JFrame {
    private List<Jogo> jogosAnunciados;

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

    private JogoFacade jogoFacade; // Referência à Facade

    public AnunciarJogoGUI(Vendedor vendedor) {
        this.vendedor = vendedor;
        this.jogoFacade = JogoFacade.getInstance(); // Inicializa a Facade

        setTitle("Anunciar Jogo");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Carregar jogos anunciados usando a Facade
        jogosAnunciados = jogoFacade.carregarJogosAnunciados();

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JPanel centerPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        // Tipo de Jogo
        centerPanel.add(new JLabel("Tipo de Jogo:"));
        tipoJogoComboBox = new JComboBox<>(new String[]{"PC", "Console"});
        centerPanel.add(tipoJogoComboBox);

        // Nome do Jogo
        centerPanel.add(new JLabel("Nome do Jogo:"));
        nomeField = new JTextField();
        centerPanel.add(nomeField);

        // Descrição
        centerPanel.add(new JLabel("Descrição:"));
        descricaoField = new JTextField();
        centerPanel.add(descricaoField);

        // Gênero
        centerPanel.add(new JLabel("Gênero:"));
        generoField = new JTextField();
        centerPanel.add(generoField);

        // Ano de Lançamento
        centerPanel.add(new JLabel("Ano de Lançamento:"));
        dataLancamentoField = new JTextField();
        centerPanel.add(dataLancamentoField);

        // Preço
        centerPanel.add(new JLabel("Preço:"));
        precoField = new JTextField();
        centerPanel.add(precoField);

        // Requisitos do PC
        centerPanel.add(new JLabel("Requisitos do PC:"));
        requisitosPcField = new JTextField();
        centerPanel.add(requisitosPcField);

        // Console
        centerPanel.add(new JLabel("Console:"));
        consoleField = new JTextField();
        centerPanel.add(consoleField);

        // Imagem
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

        // Botões de Anunciar e Voltar
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

        // Adicionar ActionListeners
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

    /**
     * Atualiza a visibilidade dos campos com base no tipo de jogo selecionado.
     * Exibe Requisitos PC se for PC, ou Console se for Console.
     */
    private void updateFieldsVisibility() {
        boolean isPc = tipoJogoComboBox.getSelectedItem().equals("PC");
        requisitosPcField.setVisible(isPc);
        consoleField.setVisible(!isPc);
        // Revalidar e repaint para atualizar a interface
        requisitosPcField.getParent().revalidate();
        requisitosPcField.getParent().repaint();
    }

    /**
     * Abre um JFileChooser para selecionar uma imagem e exibe a imagem selecionada.
     */
    private void selecionarImagem() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagemSelecionada = fileChooser.getSelectedFile();
            exibirImagem();
        }
    }

    /**
     * Exibe a imagem selecionada no JLabel correspondente.
     */
    private void exibirImagem() {
        if (imagemSelecionada != null) {
            ImageIcon imagemIcon = new ImageIcon(imagemSelecionada.getPath());
            Image imagemRedimensionada = imagemIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            imagemLabel.setIcon(new ImageIcon(imagemRedimensionada));
            imagemLabel.revalidate();
            imagemLabel.repaint();
        }
    }

    /**
     * Lida com a ação de anunciar um novo jogo.
     * Valida os campos, cria o objeto Jogo correspondente e utiliza o JogoFacade para salvar.
     */
    private void anunciarJogo() {
        String tipoJogo = (String) tipoJogoComboBox.getSelectedItem();
        String nome = nomeField.getText();
        String descricao = descricaoField.getText();
        String genero = generoField.getText();
        String dataLancamento = dataLancamentoField.getText();
        String precoText = precoField.getText();
        String imagem = imagemSelecionada != null ? imagemSelecionada.getPath() : null;

        // Validação dos campos obrigatórios
        if (nome.isEmpty() || descricao.isEmpty() || genero.isEmpty() || dataLancamento.isEmpty() || precoText.isEmpty() ||
                (tipoJogo.equals("PC") && requisitosPcField.getText().isEmpty()) ||
                (tipoJogo.equals("Console") && consoleField.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        // Validação do ano de lançamento
        int anoAtual = LocalDate.now().getYear();
        int anoLancamento;
        try {
            anoLancamento = Integer.parseInt(dataLancamento.split("-")[0]);
            if (anoLancamento > anoAtual || anoLancamento < 1971) {
                JOptionPane.showMessageDialog(this, "O ano de lançamento deve ser entre 1971 e o ano atual.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Data de lançamento inválida. Use o formato AAAA-MM-DD.");
            return;
        }

        // Validação do preço
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

        // Criação do objeto Jogo correspondente
        Jogo jogo;
        if (tipoJogo.equals("PC")) {
            String requisitosPc = requisitosPcField.getText();
            jogo = new JogoPC(nome, descricao, genero, dataLancamento, preco, imagem, requisitosPc, vendedor.getNome());
        } else {
            String console = consoleField.getText();
            jogo = new JogoConsole(nome, descricao, genero, dataLancamento, preco, imagem, console, vendedor.getNome());
        }

        // Adicionar o novo jogo à lista de jogos anunciados
        jogosAnunciados.add(jogo);

        // Utilizar o JogoFacade para salvar os jogos, o que notificará os observadores
        jogoFacade.salvarJogosAnunciados(jogosAnunciados);

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

    private List<Jogo> carregarJogosAnunciados() {
        return jogoFacade.carregarJogosAnunciados();
    }
}
