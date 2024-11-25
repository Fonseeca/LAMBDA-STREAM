package DAOs.Agencia;

public class AgenciaInexistenteException extends Exception{
    public AgenciaInexistenteException(String message) {
        super(message);
    }
}
