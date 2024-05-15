import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendedor extends Pessoa{

    Scanner scanner = new Scanner(System.in);

    //ATRIBUTOS
    private int avaliacoes;
    private List<Jogo> jogosAnunciados;

    // Construtor
    public Vendedor(String nome, String email, String senha, int cpf) {
        super(nome, email, senha, cpf);
        jogosAnunciados = new ArrayList<>();
    }

    // Método para anunciar jogo
    public void anunciarJogo(Jogo jogo) {
        jogosAnunciados.add(jogo);
        System.out.println("Jogo anunciado com sucesso!");
    }

    // Método para exibir jogos anunciados
    public void exibirJogosAnunciados() {
        System.out.println("Jogos anunciados:");
        for (Jogo jogo : jogosAnunciados) {
            System.out.println(jogo.getNomejogo());
        }
    }

    public void anuciarJogo(){
        System.out.println("anuncia jogo");
    }

    // Método para cadastrar Vendedor
    public void cadastrarVendedor(){
        Vendedor vendedor = new Vendedor(getNome(), getEmail(), getSenha(), getCpf());

        System.out.println("Informe seu nome: ");
        String nome = scanner.nextLine();
        vendedor.setNome(nome);

        System.out.println("informe seu email: ");
        String email = scanner.nextLine();
        vendedor.setEmail(email);

        System.out.println("informe sua senha: ");
        String senha = scanner.nextLine();
        vendedor.setSenha(senha);

        System.out.println("Informe seu CPF: ");
        int cpf = scanner.nextInt();
        while (!super.validaCPF(cpf)) {
            System.out.println("CPF inválido. Por favor, insira novamente: ");
            cpf = scanner.nextInt();
        }
        vendedor.setCpf(cpf);
    }

    public List<Jogo> getJogosAnunciados() {
        return jogosAnunciados;
    }
}
