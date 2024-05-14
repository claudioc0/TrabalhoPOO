import java.util.Scanner;

public class Cliente extends Pessoa {
    Scanner scanner = new Scanner(System.in);

    //ATRIBUTOS

    //CONSTRUTOR
    public Cliente(String nome, String email, String senha, int cpf){
        super(nome, email, senha, cpf);
    }

    public void cadastrarCliente(){
        Cliente cliente = new Cliente(getNome(), getEmail(), getSenha(), getCpf());

        System.out.println("Informe seu nome: ");
        String nome = scanner.nextLine();
        cliente.setNome(nome);

        System.out.println("Informe seu email: ");
        String email = scanner.nextLine();
        cliente.setEmail(email);

        System.out.println("Informe sua senha: ");
        String senha = scanner.nextLine();
        cliente.setSenha(senha);

        // Validação do CPF
        System.out.println("Informe seu CPF: ");
        int cpf = scanner.nextInt();
        while (!super.validaCPF(cpf)) {
            System.out.println("CPF inválido. Por favor, insira novamente: ");
            cpf = scanner.nextInt();
        }
        cliente.setCpf(cpf);
    }
}
