import java.util.HashMap;
import java.util.HashSet;

public class Hotel {

    private String nombreHotel;
    private String direccionHotel;
    private HashMap<Integer, Habitacion> listaHabitaciones; // numero de habitacion(Key) - info habitacion(data)
    private HashSet<Staff> listaStaff;
    private HashSet<Cliente> listaClientes;
}
