package CosasDeHabitaciones;

import Excepciones.HabitacionNoDisponibleException;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

//La disponibilidad va a depender de su su estado es Disponible y de las reservas que los usuarios hallan echo en ella.
//
public class Habitacion {
    private String motivoDisponibilidad;
    int numeroHabitacion;
    ETipoHabitacion tipoHabitacion;
    EEstadoHabitacion estadoHabitacion;
    private boolean estadoHabitacion2; // Esto es para cuando se elimina una habitacion, para poder manejarlo en la base de datos
    double precioXnoche;
    private Map<String, Integer> stockMiniBar;
    private Map<String, Integer> consumos;

    public Habitacion(int numeroHabitacion, EEstadoHabitacion estadoHabitacion, ETipoHabitacion tipoHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
        this.estadoHabitacion = estadoHabitacion;
        this.estadoHabitacion2 = true;
        this.tipoHabitacion = tipoHabitacion;
        this.precioXnoche = 500 * precioNoche();
        this.stockMiniBar = new HashMap<>();
        this.consumos = new HashMap<>();
        this.reponerMiniBarADefault(); // para q cualquier habitacion nazca con su minibar
    }

    private static final Map<String, Double> PRECIOS_MINIBAR = Map.of( //precios, las claves son las mismas que usa stock
            "1.Agua", 1500.0,
            "2.Jugo de naranja", 2000.0,
            "3.Coca Cola", 2500.0,
            "4.Cerveza", 3500.0,
            "5.Chocolate", 2800.0,
            "6.Papas fritas clasicas", 3000.0,
            "7.Barra de cereal", 1800.0,
            "8.Champagne", 25000.0
    );

    public void reponerMiniBarADefault() {
        stockMiniBar.clear();
        int tope = 1; // cantidad maxima de unidades
        if (tipoHabitacion == ETipoHabitacion.DOBLE) {
            tope = 2;
        } else if (tipoHabitacion == ETipoHabitacion.TRIPLE) {
            tope = 3;
        } else if (tipoHabitacion == ETipoHabitacion.SUIT) {
            tope = 4;
        }

        stockMiniBar.put("1.Agua", tope);
        stockMiniBar.put("2.Jugo de naranja", tope);
        stockMiniBar.put("3.Coca Cola", tope);
        stockMiniBar.put("4.Cerveza", tope);
        stockMiniBar.put("5.Chocolate", tope);
        stockMiniBar.put("6.Papas fritas clasicas", tope);
        stockMiniBar.put("7.Barra de cereal", tope);

        if (tipoHabitacion == ETipoHabitacion.SUIT) {
            stockMiniBar.put("8.Champagne", 1);
        }

    }

    public void consumirItem(int nroproducto, int cantidad) {
        System.out.printf("\n Productos disponibles en el minibar (Hab)" + numeroHabitacion);
        for (Map.Entry<String, Integer> entry : stockMiniBar.entrySet()) {
            System.out.println("\n " + entry.getKey() + ":" + entry.getValue() + " u.");
        }
        System.out.printf("\n=======================================================");

        if (cantidad <= 0) {
            System.out.printf("\n Ingrese cantidad mayor a 0.");
            return;
        }

        String productoEncontrado = null; // si no encuentra nada queda null
        for (String clave : stockMiniBar.keySet()) {  //keyset lo q hace devuelve un conjunto set q contiene UNICAMENTE las claves
            if (clave.startsWith(nroproducto + ".")) {
                productoEncontrado = clave;
                break; // para salir del for
            }
        }

        if (productoEncontrado == null) {
            System.out.printf("\n Producto no existe.");
            return;
        }

        int disponible = stockMiniBar.get(productoEncontrado);
        if (disponible == 0) {
            System.out.printf("\n Sin stock, no hay mas unidades hasta la proxima limpieza");
            return;
        }
        if (disponible < cantidad) {
            System.out.printf("\n No alcanzan , solo quedan " + disponible + " u.");
            return;
        }

        stockMiniBar.put(productoEncontrado, disponible - cantidad); // remplaza el valor q ya existe en el mapa
        int acumuladoActual = consumos.getOrDefault(productoEncontrado, 0);// como va a ser la primera vez q se consume u agua la defaltuvalue va a ser 0
        consumos.put(productoEncontrado, acumuladoActual + cantidad);// se guarda en connsumos los valores sumados
    }

    public void realizarLimpieza() {
        System.out.printf("\n Iniciando limpieza de habitacion " + numeroHabitacion);
        reponerMiniBarADefault();
        System.out.printf("\n Limpieza finalizada. ");
    }


    public int getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(int numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public ETipoHabitacion getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(ETipoHabitacion tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public EEstadoHabitacion getEstadoHabitacion() {
        return estadoHabitacion;
    }

    public void setEstadoHabitacion(EEstadoHabitacion estadoHabitacion) {
        this.estadoHabitacion = estadoHabitacion;
    }

    public double getPrecioXnoche() {
        return precioXnoche;
    }

    public void setPrecioXnoche(double precioXnoche) {
        this.precioXnoche = precioXnoche;
    }

    //devuelve un multiplicador al precio base segun el tipo de habitacion
    public double precioNoche() {
        double resultado = 0;
        if (tipoHabitacion == ETipoHabitacion.SINGLE) {
            resultado = 1;
        }
        if (tipoHabitacion == ETipoHabitacion.DOBLE) {
            resultado = 2;
        }
        if (tipoHabitacion == ETipoHabitacion.TRIPLE) {
            resultado = 3;
        }
        if (tipoHabitacion == ETipoHabitacion.SUIT) {
            resultado = 4;
        }
        return resultado;
    }

    public boolean estaDisponible() { //metodo para saber si hay lugar
        return this.estadoHabitacion == EEstadoHabitacion.DISPONIBLE;
    }

    public void realizarCheckIn() throws HabitacionNoDisponibleException { // si el metodo falla lanza esta excepcion
        if (!estaDisponible()) {
            throw new HabitacionNoDisponibleException("\nLa habitacion " + numeroHabitacion + " no esta disponible");
        }
        this.estadoHabitacion = EEstadoHabitacion.OCUPADA;
        this.motivoDisponibilidad = "\n Ocupada por cliente.";
    }

    public void realizarCheckOut() {
        this.estadoHabitacion = EEstadoHabitacion.LIMPIEZA;
        this.motivoDisponibilidad = "\n Requiere limpieza posterior a salida de cliente";
    }

    public double calcularCostoEstadia(int cantidadNoches) {
        if (cantidadNoches <= 0) {
            return 0;
        }
        return this.precioXnoche * cantidadNoches;
    }


    public String getMotivoDisponibilidad() {
        return motivoDisponibilidad;
    }

    public void cambiarESTADOhabitacion(EEstadoHabitacion nuevoEstado, String motivo) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        // Determina si el nuevo estado deja la habitación fuera de servicio, caso en el que es obligatorio justificarlo
        boolean requiereMotivo = nuevoEstado == EEstadoHabitacion.LIMPIEZA || nuevoEstado == EEstadoHabitacion.REPARACION || nuevoEstado == EEstadoHabitacion.DESINFECCION;
        if (requiereMotivo && (motivo == null)) {
            throw new IllegalArgumentException("Se debe detallar el motivo.");
        }
        // Evita mandar a limpieza, reparación o desinfección una habitación que todavía tiene un huésped adentro
        if (this.estadoHabitacion == EEstadoHabitacion.OCUPADA && requiereMotivo) {
            throw new IllegalArgumentException("La habitacion " + numeroHabitacion + " esta ocupada, primero hacer check out.");
        }

        this.estadoHabitacion = nuevoEstado;
        // Asigna el motivo correspondiente según el nuevo estado
        if (requiereMotivo) {
            this.motivoDisponibilidad = motivo;
        } else if (nuevoEstado == EEstadoHabitacion.OCUPADA) {
            this.motivoDisponibilidad = "Ocupado por cliente";
        } else {
            this.motivoDisponibilidad = null;
        }

    }

    public void ponerHabitacionEnLimpieza(String motivo) {
        cambiarESTADOhabitacion(EEstadoHabitacion.LIMPIEZA, motivo);
    }

    public void ponerHabitacionEnDesinfeccion(String motivo) {
        cambiarESTADOhabitacion(EEstadoHabitacion.DESINFECCION, motivo);
    }

    public void ponerHabitacionEnReparacion(String motivo) {
        cambiarESTADOhabitacion(EEstadoHabitacion.REPARACION, motivo);
    }

    public void habilitarHabitacion() {
        cambiarESTADOhabitacion(EEstadoHabitacion.DISPONIBLE, null);
    }

    public boolean estaNoDisponibleXmotivo() { // evuelve true si no esta disponible por limpieza, reparacion o desinfeccion.
        return estadoHabitacion == EEstadoHabitacion.LIMPIEZA || estadoHabitacion == EEstadoHabitacion.REPARACION || estadoHabitacion == EEstadoHabitacion.DESINFECCION;
    }

    public void finalizarLimpieza() {
        if (estadoHabitacion != EEstadoHabitacion.LIMPIEZA) {
            throw new IllegalArgumentException("La habitacion " + numeroHabitacion + " no esta en limpieza. ");
        }
        reponerMiniBarADefault();
        habilitarHabitacion();
    }

    public double calcularTotalConsumos() {
        double total = 0;

        for (Map.Entry<String, Integer> entry : consumos.entrySet()) {
            total += PRECIOS_MINIBAR.getOrDefault(entry.getKey(), 0.0) * entry.getValue();
        }
        return total;
    }


    public String getDetalleConsumos(){
        if (consumos.isEmpty()){
            // Si no se registró ningún consumo en la habitación, retorna un mensaje indicándolo
            return "  (Sin consumos)\n";
        }
        StringBuilder detalle=new StringBuilder();
        for (Map.Entry<String,Integer> entry: new TreeMap<>(consumos).entrySet()){ // treemap para ordenar claves
            String clave=entry.getKey();
            String nombre=clave.substring(clave.indexOf('.')+1); // saca el "1." del inicio
            double subtotal=PRECIOS_MINIBAR.getOrDefault(clave,0.0)*entry.getValue();
            detalle.append(String.format("  %-25s x%-3d $%.2f%n",nombre,entry.getValue(),subtotal));
            // Agrega la línea al ticket acomodando el nombre, la cantidad y el precio en columnas prolijas
        }
        return detalle.toString();
    }

    public void limpiarConsumos(){
        consumos.clear();
    }

    public double costoFinal(int cantnoches){
        return calcularCostoEstadia(cantnoches)+calcularTotalConsumos();
    }

    @Override
    public String toString() {
        return "Habitacion{" +
                "numeroHabitacion=" + numeroHabitacion +
                ", tipoHabitacion=" + tipoHabitacion +
                ", estadoHabitacion=" + estadoHabitacion +
                ", precioXnoche=" + precioXnoche +
                '}';
    }
}

