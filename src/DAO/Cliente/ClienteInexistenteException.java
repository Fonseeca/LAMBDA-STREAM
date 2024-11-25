package DAOs.Cliente;

public class ClienteInexistenteException extends Exception{
    public ClienteInexistenteException(String message) {
        super(message);
    }
}
