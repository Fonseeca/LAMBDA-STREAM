package DAOs.Banco;

public class BancoInexistenteException extends Exception{
    public BancoInexistenteException(String mensagem) {
        super(mensagem);
    }
}
