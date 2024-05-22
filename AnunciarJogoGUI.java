import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnunciarJogoGUI extends JFrame {
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

    private Vendedor vendedor;

    public AnunciarJogoGUI(Vendedor vendedor) {
        this.vendedor = vendedor;
        setTitle("Anunciar Jogo");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(panel);

        JPanel centerPanel = new JPanel(new GridLayout(8, 2, 10, 10));

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

    private void anunciarJogo() {
        String tipoJogo = (String) tipoJogoComboBox.getSelectedItem();
        String nome = nomeField.getText();
        String descricao = descricaoField.getText();
        String genero = generoField.getText();
        String dataLancamento = dataLancamentoField.getText();
        String precoText = precoField.getText();
        double preco;

        if (nome.isEmpty() || descricao.isEmpty() || genero.isEmpty() || dataLancamento.isEmpty() || precoText.isEmpty() ||
                (tipoJogo.equals("PC") && requisitosPcField.getText().isEmpty()) ||
                (tipoJogo.equals("Console") && consoleField.getText().isEmpty())) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            preco = Double.parseDouble(precoText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço inválido. Por favor, insira um número válido.");
            return;
        }

        if (tipoJogo.equals("PC")) {
            String requisitosPc = requisitosPcField.getText();
            JogoPC jogoPc = new JogoPC(nome, descricao, genero, dataLancamento, preco, requisitosPc);
            vendedor.anunciarJogo(jogoPc);
        } else {
            String console = consoleField.getText();
            JogoConsole jogoConsole = new JogoConsole(nome, descricao, genero, dataLancamento, preco, console);
            vendedor.anunciarJogo(jogoConsole);
        }

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
    }
}
