package Excepciones;

public class EClienteNoExiste extends RuntimeException {
    public EClienteNoExiste() {
        super("El cliente especificado no existe en el sistema.");
    }
}
//public class EClienteNoExiste extends Exception {