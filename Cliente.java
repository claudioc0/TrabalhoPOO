import java.util.Scanner;
import java.util.List;

public class Cliente extends Pessoa {
    Scanner scanner = new Scanner(System.in);

    //ATRIBUTOS

    //CONSTRUTOR
    public Cliente(String nome, String email, String senha, int cpf){
        super(nome, email, senha, cpf);
    }

    // Método para cadastrar Cliente
    public void cadastrarCliente(){
        Cliente cliente = new Cliente(getNome(), getEmail(), getSenha(), getCpf());

        System.out.println("Informe seu nome: ");
        String nome = scanner.nextLine();
        cliente.setNome(nome);

        // Validação do email
        System.out.println("Informe seu email: ");
        String email = scanner.nextLine();
        while (!cliente.validaEmail(email)) {
            System.out.println("Email inválido. Por favor, insira novamente: ");
            email = scanner.nextLine();
        }
        cliente.setEmail(email);

        System.out.println("Informe sua senha: ");
        String senha = scanner.nextLine();
        cliente.setSenha(senha);

        // Validação do CPF
        System.out.println("Informe seu CPF: ");
        int cpf = scanner.nextInt();
        while (!cliente.validaCPF(cpf)) {
            System.out.println("CPF inválido. Por favor, insira novamente: ");
            cpf = scanner.nextInt();
        }
        cliente.setCpf(cpf);
    }

    // Método para exibir jogos disponíveis para compra
    public void exibirJogosDisponiveis(Vendedor vendedor) {
        List<Jogo> jogosAnunciados = vendedor.getJogosAnunciados();
        System.out.println("Jogos disponíveis para compra:");
        for (int i = 0; i < jogosAnunciados.size(); i++) {
            System.out.println((i+1) + ". " + jogosAnunciados.get(i).getNomejogo());
        }
    }
}
