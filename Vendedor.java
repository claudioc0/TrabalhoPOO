import java.util.Scanner;

public class Vendedor extends Pessoa{

    Scanner scanner = new Scanner(System.in);

    //ATRIBUTOS

    //CONTRUTOR
    public Vendedor(String nome, String email, String senha, int cpf){
        super(nome, email, senha, cpf);
    }

    public void anuciarJogo(){
        System.out.println("anuncia jogo");
    }


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
}
