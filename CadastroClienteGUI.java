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

        // Configura o layout do painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        Font font = new Font("Arial", Font.PLAIN, 16);

        // Painel central para os campos de entrada
        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridLayout(4, 2, 10, 10));

        // Adiciona campos
        fieldsPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        nomeField.setFont(font);
        fieldsPanel.add(nomeField);

        fieldsPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        emailField.setFont(font);
        fieldsPanel.add(emailField);

        fieldsPanel.add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        senhaField.setFont(font);
        fieldsPanel.add(senhaField);

        fieldsPanel.add(new JLabel("CPF:"));
        cpfField = new JTextField();
        cpfField.setFont(font);
        fieldsPanel.add(cpfField);

        panel.add(fieldsPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        // Adiciona botões ao buttonsPanel
        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setFont(font);
        cadastrarButton.setBackground(new Color(70, 130, 180));
        cadastrarButton.setForeground(Color.WHITE);
        buttonsPanel.add(cadastrarButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setFont(font);
        voltarButton.setBackground(new Color(70, 130, 180));
        voltarButton.setForeground(Color.WHITE);
        buttonsPanel.add(voltarButton);

        panel.add(buttonsPanel);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Voltar para a tela anterior
                // Por exemplo, para a tela de escolha entre cliente e vendedor
                new ClienteMainGUI().setVisible(true);
                dispose(); // Fecha a tela atual
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
            // Salvar o cliente no arquivo de texto
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
