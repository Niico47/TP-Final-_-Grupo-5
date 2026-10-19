import java.util.ArrayList;

public final class Cliente extends Persona{
    String nombre;
    String dni;
    String Localidad;
    String domicilio;
    List<String>informacionPasajero;

    public Cliente(String nombre,String dni,String localidad,String domicilio){
        this.nombre=nombre;
        this.dni=dni;
        this.Localidad=localidad;
        this.domicilio=domicilio;
        this.informacionPasajero=new ArrayList<>();
    }

    
}
