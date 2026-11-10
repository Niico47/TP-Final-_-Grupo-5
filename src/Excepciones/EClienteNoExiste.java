package Excepciones;

public class EClienteNoExiste extends RuntimeException {
    public EClienteNoExiste() {
        super("El cliente especificado no existe en el sistema.");
    }
}
