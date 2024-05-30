import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Vendedor extends Pessoa {
    Scanner scanner = new Scanner(System.in);

    private int avaliacoes;
    private List<Jogo> jogosAnunciados;

    public Vendedor(String nome, String email, String senha, String cpf) {
        super(nome, email, senha, cpf);
        jogosAnunciados = new ArrayList<>();
    }

    public void anunciarJogo(Jogo jogo) {
        jogosAnunciados.add(jogo);
        System.out.println("Jogo anunciado com sucesso!");
    }

    public void exibirJogosAnunciados() {
        System.out.println("Jogos anunciados:");
        for (Jogo jogo : jogosAnunciados) {
            System.out.println(jogo.getNomeJogo());
        }
    }

    public void cadastrarVendedor() {
        System.out.println("Informe seu nome: ");
        String nome = scanner.nextLine();
        setNome(nome);

        System.out.println("Informe seu email: ");
        String email = scanner.nextLine();
        setEmail(email);

        System.out.println("Informe sua senha: ");
        String senha = scanner.nextLine();
        setSenha(senha);

        System.out.println("Informe seu CPF: ");
        String cpf = scanner.nextLine();
        while (!super.validaCPF(cpf)) {
            System.out.println("CPF inválido. Por favor, insira novamente: ");
            cpf = scanner.nextLine();
        }
        setCpf(cpf);
    }

    public List<Jogo> getJogosAnunciados() {
        return jogosAnunciados;
    }
}
