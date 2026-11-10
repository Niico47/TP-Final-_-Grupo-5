package CosasDeHabitaciones;//habitaciones disponibles, y las habitaciones que no estén disponibles por algún motivo
// (limpieza, reparación, desinfección, etc. Detallar el motivo).

public enum EEstadoHabitacion {

    DISPONIBLE("Disponible"),
    OCUPADA("Ocupada"),
    LIMPIEZA("En limpieza"),
    REPARACION("En reparación"),
    DESINFECCION("En desinfección");

    private final String descripcion;

    EEstadoHabitacion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
