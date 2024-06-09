import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class CadastroClienteGUI extends JFrame {
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField cpfField;
    private JButton cadastrarButton;
    private JButton voltarButton;

    public CadastroClienteGUI() {
        setTitle("Cadastro de Cliente");
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
        cadastrarButton.setBackground(new Color(25, 120, 165)); // Azul
        cadastrarButton.setForeground(Color.WHITE);
        southPanel.add(cadastrarButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setPreferredSize(new Dimension(100, 25)); // Define um tamanho menor para o botão de voltar
        voltarButton.setBackground(new Color(25, 120, 165)); // Azul
        voltarButton.setForeground(Color.WHITE);
        southPanel.add(voltarButton);

        panel.add(southPanel, BorderLayout.SOUTH);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteMainGUI().setVisible(true);
                dispose();
            }
        });
    }

    private void cadastrarCliente() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        String cpf = cpfField.getText();

        // Verifica se todos os campos foram preenchidos
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.");
            return;
        }

        Cliente cliente = new Cliente(nome, email, senha, cpf);

        // Validação do email
        if (!cliente.validaEmail(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido. Por favor, insira novamente.");
            return;
        }

        // Validação do CPF
        if (!cliente.validaCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido. O CPF deve conter exatamente 11 dígitos numéricos.");
            return;
        }

        // Verifica se o cliente já está cadastrado
        if (clienteJaCadastrado(nome, email, cpf)) {
            JOptionPane.showMessageDialog(this, "Cliente já cadastrado com o mesmo nome, email ou CPF.");
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("clientes.txt", true));
            writer.write("Nome: " + nome + ", Email: " + email + ", Senha: " + senha + ", CPF: " + cpf);
            writer.newLine();
            writer.close();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o cliente: " + e.getMessage());
        }
    }

    private boolean clienteJaCadastrado(String nome, String email, String cpf) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("clientes.txt"));
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CadastroClienteGUI().setVisible(true);
            }
        });
    }
}
