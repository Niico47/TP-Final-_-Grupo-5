package CosasDePersonas;

import java.util.Objects;

public abstract class Persona<T> {

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Persona<?> persona)) return false;
        return Objects.equals(dni, persona.dni);
    }

    @Override
    public String toString() {
        return "\nNombre= " + nombre +
                "\nDNI= " + dni +
                "\nLocalidad= " + localidad +
                "\nDomicilio= " + domicilio;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(dni);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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
