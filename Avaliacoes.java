import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Avaliacoes extends JFrame implements ActionListener {
    private JLabel lblTitulo, lblAvaliacao;
    private JRadioButton[] estrelas;
    private JButton btnEnviar;

    private JTextArea txtReview;


    public Avaliacoes() {
        setTitle("Avaliação do produto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelTitulo = new JPanel();
        lblTitulo = new JLabel("Avalie o jogo");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        painelTitulo.add(lblTitulo);
        add(painelTitulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new GridLayout(7, 2));
        lblAvaliacao = new JLabel("Sua Avaliação: ");
        estrelas = new JRadioButton[5];
        ButtonGroup group = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            estrelas[i] = new JRadioButton(String.valueOf(i + 1));
            estrelas[i].addActionListener(this);
            group.add(estrelas[i]);
            painelCentral.add(estrelas[i]);
        }
        painelCentral.add(lblAvaliacao);
        //add(lblAvaliacao, BorderLayout.WEST);
        //add(painelCentral, BorderLayout.CENTER);

        txtReview = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(txtReview);
        painelCentral.add(scrollPane);

        JPanel painelInferior = new JPanel();
        btnEnviar = new JButton("Enviar avaliação");
        btnEnviar.addActionListener(this);
        painelInferior.add(btnEnviar);
        add(painelInferior, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.CENTER);

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnEnviar) {
            String avaliacao = "";
            for (int i = 0; i < 5; i++) {
                if (estrelas[i].isSelected()) {
                    avaliacao = estrelas[i].getText() + " estrelas";
                    break;
                }
            }
            String review = txtReview.getText();
            JOptionPane.showMessageDialog(this, "Você avaliou com " + avaliacao);
            limparSelecaoEstrelas();
            txtReview.setText("");
        }
    }

    private void limparSelecaoEstrelas() {
        for (JRadioButton estrela : estrelas) {
            estrela.setSelected(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Avaliacoes().setVisible(true);
            }
        });
    }


}
