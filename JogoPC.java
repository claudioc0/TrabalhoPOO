public class JogoPC extends Jogo{
    //ATRiBUTOS
    private String requisitosPc;

    //CONSTRUTOR
    public JogoPC(String nome, String descricao, String genero, String dataLancamento, double preco, String imagem, String requisitosPc){
        super(nome, descricao, genero, dataLancamento, preco, imagem);
        this.requisitosPc = requisitosPc;
    }

    public void setRequisitosPc(String requisitosPc){
        this.requisitosPc = requisitosPc;
    }

    public String getRequisitosPc(){
        return requisitosPc;
    }


        @Override
        public String toTexto() {
            // Concatenando todos os atributos em uma única string, separados por vírgula
            return String.join(",", getNomeJogo(), getDescricao(), getGeneroJogo(), getDataLancamento(), String.valueOf(getPrecoJogo()), getImagem(), requisitosPc);
    }

    public static JogoConsole fromTexto(String linha) {
        String[] partes = linha.split(",");
        if (partes.length < 7) {
            return null;
        }

        String nome = partes[0];
        String descricao = partes[1];
        String genero = partes[2];
        String dataLancamento = partes[3];
        double preco = Double.parseDouble(partes[4]);
        String imagem = partes[5];
        String console = partes[6];

        return new JogoConsole(nome, descricao, genero, dataLancamento, preco, imagem, console);
    }

}
