public class Pessoa {
    //ATRIBUTOS
    private String nome;
    private String email;
    private String senha;
    private int cpf;


    //CONSTRUTOR
    public Pessoa(String nome, String email, String senha, int cpf){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
    }

    //GETTERS

    public String getNome() {
        return nome;
    }

    public String getEmail(){
        return email;
    }

    public String getSenha(){
        return senha;
    }


    public int getCpf() {
        return cpf;
    }

    //SETTERS

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }


    // Método para validar o CPF
    protected boolean validaCPF(int cpf) {
        String cpfStr = String.valueOf(cpf);
        // Verifica se o CPF tem 11 dígitos
        if (cpfStr.length() != 11) {
            return false;
        }
        // Calcula o dígito verificador do CPF
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            soma += Character.getNumericValue(cpfStr.charAt(i)) * (10 - i);
        }
        int primeiroDigitoVerificador = 11 - (soma % 11);
        if (primeiroDigitoVerificador > 9) {
            primeiroDigitoVerificador = 0;
        }

        // Verifica se o primeiro dígito verificador é igual ao décimo dígito do CPF
        if (Character.getNumericValue(cpfStr.charAt(9)) != primeiroDigitoVerificador)
            return false;

        soma = 0;
        for (int i = 0; i < 10; i++) {
            soma += Character.getNumericValue(cpfStr.charAt(i)) * (11 - i);
        }
        int segundoDigitoVerificador = 11 - (soma % 11);
        if (segundoDigitoVerificador > 9) {
            segundoDigitoVerificador = 0;
        }

        // Verifica se o segundo dígito verificador é igual ao décimo primeiro dígito do CPF
        return Character.getNumericValue(cpfStr.charAt(10)) == segundoDigitoVerificador;
    }

}
