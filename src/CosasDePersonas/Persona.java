package CosasDePersonas;

public class Persona<T> {

    private String nombre;
    private String dni;
    private String localidad;
    private String domicilio;

    public Persona(String nombre, String dni, String domicilio, String localidad) {
        this.nombre = nombre;
        this.dni = dni;
        this.domicilio = domicilio;
        this.localidad = localidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDni() {
        return dni;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
}
