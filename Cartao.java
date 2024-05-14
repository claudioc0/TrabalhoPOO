import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Cartao extends MetodoPagamento {
    //ATRIBUTOS
    private String nomeProprietario;
    private int numeroCartao;
    private int senhaCartao;
    private String dataVencimento;
    private double saldo;

    //CONSTRUTOR
    public Cartao(double valor, int numeroCartao, int senhaCartao, String dataVencimento, double saldo) {
        super(valor);
        this.numeroCartao = numeroCartao;
        this.senhaCartao = senhaCartao;
        this.dataVencimento = dataVencimento;
        this.saldo = saldo;
    }

    public void validaInformacoes(){
        Scanner scanner = new Scanner(System.in);

        // Valida o nome do proprietário
        System.out.println("Insira o nome do proprietário: ");
        nomeProprietario = scanner.nextLine();

        // Valida a data de vencimento
        System.out.println("Informe a data de vencimento do cartão (formato: MM/AAAA): ");
        dataVencimento = scanner.nextLine();
        while (!validaDataVencimento(dataVencimento)) {
            System.out.println("Data de vencimento inválida ou cartão expirado. Por favor, insira novamente (formato: MM/AAAA): ");
            dataVencimento = scanner.nextLine();
        }

        // Valida o número do cartão
        System.out.println("Insira o número do cartão (16 dígitos): ");
        numeroCartao = scanner.nextInt();
        while (!validaNumeroCartao(numeroCartao)) {
            System.out.println("Número de cartão inválido. Por favor, insira novamente (16 dígitos): ");
            numeroCartao = scanner.nextInt();
        }

        // Valida a senha
        System.out.println("Insira a senha (4 dígitos): ");
        senhaCartao = scanner.nextInt();
        while (!validaSenha(senhaCartao)) {
            System.out.println("Senha inválida. Por favor, insira novamente (4 dígitos): ");
            senhaCartao = scanner.nextInt();
        }
    }

    // Validando a data de vencimento
    private boolean validaDataVencimento(String dataVencimento) {
        // Verifica se a data está no formato correto MM/AAAA
        if (!dataVencimento.matches("\\d{2}/\\d{4}"))
            return false;

        // Tenta criar um objeto LocalDate com a data fornecida
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            LocalDate data = LocalDate.parse(dataVencimento, formatter);

            // Verifica se a data é futura
            if (data.isBefore(LocalDate.now()))
                return false; // A data está no passado, o cartão expirou

            return true;
        } catch (Exception e) {
            return false; // Se ocorrer alguma exceção ao tentar criar o LocalDate, a data é inválida
        }
    }

    // Validando o número do cartão
    private boolean validaNumeroCartao(int numeroCartao) {
        String cartaoString = Integer.toString(numeroCartao);
        return cartaoString.length() == 16; // Verifica se o número do cartão tem 16 dígitos
    }

    // Validando a senha
    private boolean validaSenha(int senha) {
        String senhaString = Integer.toString(senha);
        return senhaString.length() == 4; // Verifica se a senha tem 4 dígitos
    }

    //MÉTODOS
    @Override
    public void pagar() {
        if (saldo >= getValor()){
            saldo -= getValor();
            System.out.println("Pagamento efetuado com sucesso!");
        }else{
            System.out.println("Saldo insuficiente. Não foi possível realizar pagamento.");
        }
    }

    public int getNumeroCartao() {
        return numeroCartao;
    }

    public int getsenha() {
        return senhaCartao;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }
}
