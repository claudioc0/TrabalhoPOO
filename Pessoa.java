import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pessoa {
    //ATRIBUTOS
    private String nome;
    private String email;
    private String senha;
    private String cpf;


    //CONSTRUTOR
    public Pessoa(String nome, String email, String senha, String cpf){
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


    public String getCpf() {
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

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }


    // Método para validar o CPF
    public boolean validaCPF(String cpf) {
        return cpf.matches("\\d{11}");
    }

    // Método para validar o email
    public boolean validaEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
