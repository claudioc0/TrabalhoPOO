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
}
