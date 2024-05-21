import java.util.Scanner;
import java.util.List;

public class Cliente extends Pessoa {
    Scanner scanner = new Scanner(System.in);

    // ATRIBUTOS

    // CONSTRUTOR
    public Cliente(String nome, String email, String senha, String cpf) {
        super(nome, email, senha, cpf);
    }

    // Método para cadastrar Cliente
    public void cadastrarCliente() {
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
        String cpf = scanner.nextLine();
        while (!cliente.validaCPF(cpf)) {
            System.out.println("CPF inválido. Por favor, insira novamente: ");
            cpf = scanner.nextLine();
        }
        cliente.setCpf(cpf);
    }

    // Método para exibir jogos disponíveis para compra
    public void exibirJogosDisponiveis(Vendedor vendedor) {
        List<Jogo> jogosAnunciados = vendedor.getJogosAnunciados();
        System.out.println("Jogos disponíveis para compra:");
        for (int i = 0; i < jogosAnunciados.size(); i++) {
            System.out.println((i + 1) + ". " + jogosAnunciados.get(i).getNomejogo());
        }
    }

    // Método para o cliente comprar um jogo
    public void comprarJogo(Vendedor vendedor) {
        exibirJogosDisponiveis(vendedor); // Mostra os jogos disponíveis

        // Solicitação da escolha do jogo
        System.out.println("Escolha o número do jogo que deseja comprar: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer de entrada

        // Verifica se a escolha é válida
        if (escolha < 1 || escolha > vendedor.getJogosAnunciados().size()) {
            System.out.println("Escolha inválida!");
            return;
        }

        // Obtém o jogo escolhido
        Jogo jogoEscolhido = vendedor.getJogosAnunciados().get(escolha - 1);

        // Adiciona o jogo ao histórico de compras do cliente
        adicionarJogoAoHistorico(jogoEscolhido);

        // Remove o jogo dos jogos disponíveis do vendedor
        //vendedor.removerJogoAnunciado(jogoEscolhido);

        // Mostra mensagem de sucesso
        System.out.println("Compra realizada com sucesso! O jogo '" + jogoEscolhido.getNomejogo() + "' foi adicionado ao seu histórico de compras.");
    }

    // Método para adicionar um jogo ao histórico de compras do cliente
    private void adicionarJogoAoHistorico(Jogo jogo) {
        // Aqui você pode implementar a lógica para adicionar o jogo ao histórico de compras do cliente
        // Por exemplo, você pode ter uma lista de jogos comprados no próprio Cliente e adicionar o jogo a essa lista.
    }
}
