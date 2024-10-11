import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CadastroCartaoGUI extends JFrame {
    private List<Jogo> jogos;
    private Cliente cliente;
    private List<Jogo> jogosAnunciados;
    private VisualizarJogosGUI visualizarJogosGUI;
    private JTextField numeroCartaoField;
    private JTextField nomeTitularField;
    private JTextField validadeField;
    private JTextField cvvField;

    private JogoFacade jogoFacade;

    public CadastroCartaoGUI(List<Jogo> jogos, Cliente cliente, List<Jogo> jogosAnunciados, VisualizarJogosGUI visualizarJogosGUI) {
        this.jogos = jogos;
        this.cliente = cliente;
        this.jogosAnunciados = jogosAnunciados;
        this.visualizarJogosGUI = visualizarJogosGUI;

        this.jogoFacade = JogoFacade.getInstance();

        setTitle("Cadastro de Cartão de Crédito");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        setContentPane(panel);

        panel.add(new JLabel("Número do Cartão:"));
        numeroCartaoField = new JTextField();
        panel.add(numeroCartaoField);

        panel.add(new JLabel("Nome do Titular:"));
        nomeTitularField = new JTextField();
        panel.add(nomeTitularField);

        panel.add(new JLabel("Validade (MM/AA):"));
        validadeField = new JTextField();
        panel.add(validadeField);

        panel.add(new JLabel("CVV:"));
        cvvField = new JTextField();
        panel.add(cvvField);

        JButton finalizarCompraButton = new JButton("Finalizar Compra");
        finalizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarCompra();
            }
        });
        finalizarCompraButton.setBackground(new Color(25, 120, 165));
        finalizarCompraButton.setForeground(Color.WHITE);;


        panel.add(finalizarCompraButton);
    }

    private void finalizarCompra() {
        String numeroCartao = numeroCartaoField.getText();
        String nomeTitular = nomeTitularField.getText();
        String validade = validadeField.getText();
        String cvv = cvvField.getText();

        if (!validarNumeroCartao(numeroCartao)) {
            JOptionPane.showMessageDialog(this, "Número do cartão inválido.");
            return;
        }

        if (!validarNomeTitular(nomeTitular)) {
            JOptionPane.showMessageDialog(this, "Nome do titular inválido.");
            return;
        }

        if (!validarValidade(validade)) {
            JOptionPane.showMessageDialog(this, "Data de validade inválida.");
            return;
        }

        if (!validarCVV(cvv)) {
            JOptionPane.showMessageDialog(this, "CVV inválido.");
            return;
        }

        for (Jogo jogo : jogos) {
            cliente.adicionarJogoAoHistorico(jogo);
            jogosAnunciados.remove(jogo);

            jogoFacade.registrarVenda(cliente, "Cartão de Crédito", jogo, "Número do Cartão: " + numeroCartao);

            jogoFacade.registrarCompra(cliente, jogo, "Cartão de Crédito");
        }

        jogoFacade.salvarJogosAnunciados(jogosAnunciados);

        visualizarJogosGUI.atualizarJogosAnunciados();
        JOptionPane.showMessageDialog(this, "Compra efetuada com sucesso!");
        dispose();
    }

    private boolean validarNumeroCartao(String numeroCartao) {
        return numeroCartao.matches("\\d{16}");
    }

    private boolean validarNomeTitular(String nomeTitular) {
        return nomeTitular.matches("[A-Za-z ]+");
    }

    private boolean validarValidade(String validade) {
        if (!validade.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            return false;
        }
        String[] partes = validade.split("/");
        int mes = Integer.parseInt(partes[0]);
        int ano = Integer.parseInt(partes[1]) + 2000;
        java.time.YearMonth dataValidade = java.time.YearMonth.of(ano, mes);
        return dataValidade.isAfter(java.time.YearMonth.now());
    }

    private boolean validarCVV(String cvv) {
        return cvv.matches("\\d{3,4}");
    }

    private void salvarJogosAnunciados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("jogos_anunciados.txt"))) {
            for (Jogo jogo : jogosAnunciados) {
                writer.write(jogo.toTexto());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registrarVenda(Cliente cliente, String metodoPagamento, Jogo jogo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("historico_vendas.txt", true))) {
            String registro = "Cliente: " + cliente.getNome() +
                    ", Jogo: " + jogo.getNomeJogo() +
                    ", Data: " + java.time.LocalDate.now() +
                    ", Preço: R$" + jogo.getPrecoJogo() +
                    ", Vendedor: " + jogo.getVendedorNome() +
                    ", Método de Pagamento: " + metodoPagamento;
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cliente.getNome() + "_historico_compras.txt", true))) {
            String registro = "Jogo: " + jogo.getNomeJogo() +
                    ", Data: " + java.time.LocalDate.now() +
                    ", Preço: R$" + jogo.getPrecoJogo() +
                    ", Método de Pagamento: " + metodoPagamento;
            writer.write(registro);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
