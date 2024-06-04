import javax.swing.*;
import java.awt.*;
import java.awt.event.*;





public class GUIcarrinho extends JFrame implements ActionListener {
    private GUIcarrinho carrinho;
    private JTextField txtNomeJogo;
    private JTextField txtPrecoJogo;
    private JButton htnAdicionar;
    private JButton btnListar;

    public GUIcarrinho() {
        setTitle("Carrinho de compras");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3,2));

        carrinho = new CarrinhoCompras();

        JLabel lblNomeJogo = new JLabel("Nome do jogo:");
        txtNomeJogo = new JTextField();
        JLabel lblPrecoJogo = new JLabel("Preço do jogo: ");
        txtPrecoJogo = new JTextField();

        btnAdicionar = new JButton("Adicionar ao carrinho");
        btnAdicionar.addActionListener(this);
        btnListar = new JButton("Listar jogos no carrinho");
        btnListar.addActionListener(this);


        add(lblNomeJogo);
        add(txtNomeJogo);
        add(lbl)
    }
}
