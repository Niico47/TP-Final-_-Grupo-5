package Excepciones;

public class ENoExisteReservaParaFecha extends RuntimeException {
    public ENoExisteReservaParaFecha(String message) {
        super(message);
    }
}
