import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class VendedorCadastroGUI extends JFrame {
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField cpfField;
    private JButton cadastrarButton;
    private JButton voltarButton;

    public VendedorCadastroGUI() {
        setTitle("Cadastro de Vendedor");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        JPanel centerPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        centerPanel.setBackground(Color.WHITE);

        centerPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        centerPanel.add(nomeField);

        centerPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        centerPanel.add(emailField);

        centerPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        centerPanel.add(senhaField);

        centerPanel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        centerPanel.add(cpfField);

        panel.add(centerPanel, BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        southPanel.setBackground(Color.WHITE);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBackground(new Color(25, 120, 165));
        cadastrarButton.setForeground(Color.WHITE);
        southPanel.add(cadastrarButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setPreferredSize(new Dimension(100, 25));
        voltarButton.setBackground(new Color(25, 120, 165));
        voltarButton.setForeground(Color.WHITE);
        southPanel.add(voltarButton);

        panel.add(southPanel, BorderLayout.SOUTH);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarVendedor();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltar();
            }
        });
    }

    private void cadastrarVendedor() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        String cpf = cpfField.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        Vendedor vendedor = new Vendedor(nome, email, senha, cpf);

        if (!vendedor.validaEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido. Por favor, insira novamente.");
            return;
        }

        if (!vendedor.validaCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos.");
            return;
        }

        // Verifica se o vendedor já está cadastrado
        if (vendedorJaCadastrado(nome, email, cpf)) {
            JOptionPane.showMessageDialog(this, "Vendedor já cadastrado com o mesmo nome, email ou CPF.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("vendedores.txt", true))) {
            writer.write("Nome: " + nome + ", Email: " + email + ", Senha: " + senha + ", CPF: " + cpf);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Vendedor cadastrado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os dados do vendedor: " + e.getMessage());
        }
    }

    private boolean vendedorJaCadastrado(String nome, String email, String cpf) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("vendedores.txt"));
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.contains("Nome: " + nome) || linha.contains("Email: " + email) || linha.contains("CPF: " + cpf)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void voltar() {
        dispose();
        new VendedorMainGUI().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VendedorCadastroGUI().setVisible(true);
            }
        });
    }
}
