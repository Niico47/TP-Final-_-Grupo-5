package CosasDeHabitaciones;

import java.util.HashMap;
import java.util.Map;

//La disponibilidad va a depender de su su estado es Disponible y de las reservas que los usuarios hallan echo en ella.
//
public class Habitacion {
    private String motivoDisponibilidad;
    int numeroHabitacion;
    ETipoHabitacion tipoHabitacion;
    EEstadoHabitacion estadoHabitacion;
    boolean reservada;
    double precioXnoche;

    private Map<String,Integer> stockMiniBar;
    private Map<String,Integer> consumos;


    public Habitacion(int numeroHabitacion, EEstadoHabitacion estadoHabitacion, ETipoHabitacion tipoHabitacion, boolean reservada, double precioXnoche) {
        this.numeroHabitacion = numeroHabitacion;
        this.estadoHabitacion = estadoHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.reservada = reservada;
        this.precioXnoche = 500*precioNoche();
        this.stockMiniBar=new HashMap<>();
        this.consumos=new HashMap<>();
        this.reponerMiniBarADefault(); // para q cualquier habitacion nazca con su minibar
    }

    public void reponerMiniBarADefault(){
        stockMiniBar.clear();
        int tope=1; // cantidad maxima de unidades
        if (tipoHabitacion== ETipoHabitacion.DOBLE){
            tope=2;
        } else if (tipoHabitacion== ETipoHabitacion.TRIPLE){
            tope=3;
        } else if (tipoHabitacion== ETipoHabitacion.SUIT){
            tope=4;
            stockMiniBar.put("Champagne",1);
        }

        stockMiniBar.put("Agua",tope);
        stockMiniBar.put("Jugo de naranja",tope);
        stockMiniBar.put("Coca Cola",tope);
        stockMiniBar.put("Cerveza",tope);
        stockMiniBar.put("Chocolate",tope);
        stockMiniBar.put("Papas fritas clasicas",tope);
        stockMiniBar.put("Barra de cereal",tope);


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

    public boolean isReservada() {
        return reservada;
    }

    public void setReservada(boolean reservada) {
        this.reservada = reservada;
    }

    public double getPrecioXnoche() {
        return precioXnoche;
    }

    public void setPrecioXnoche(double precioXnoche) {
        this.precioXnoche = precioXnoche;
    }

    public double precioNoche(){
        double resultado=0;
        if(tipoHabitacion== ETipoHabitacion.SINGLE){
            resultado=1;
        }
        if(tipoHabitacion==ETipoHabitacion.DOBLE){
            resultado=2;
        }
        if(tipoHabitacion==ETipoHabitacion.TRIPLE){
            resultado=3;
        }
        if(tipoHabitacion==ETipoHabitacion.SUIT){
            resultado=4;
        }
        return resultado;
    }

    public boolean estaDisponible(){ //metodo para saber si hay lugar
        return this.estadoHabitacion== EEstadoHabitacion.DISPONIBLE && !this.reservada;
    }

    public void realizarCheckIn() throws HabitacionNoDisponibleException { // si el metodo falla lanza esta excepcion
        if (!estaDisponible()){
            throw new HabitacionNoDisponibleException("\nLa habitacion "+numeroHabitacion+" no esta disponible");
        }
        this.estadoHabitacion=EEstadoHabitacion.OCUPADA;
        this.reservada=true;
        this.motivoDisponibilidad="\n Ocupada por cliente.";
    }

    public void realizarCheckOut() {
        this.estadoHabitacion = EEstadoHabitacion.LIMPIEZA;
        this.reservada = false;
        this.motivoDisponibilidad = "\n Requiere limpieza posterior a salida de cliente";
    }

    public double calcularCostoEstadia(int cantidadNoches){
        if (cantidadNoches<=0){
            return 0;
        }
        return this.precioXnoche*cantidadNoches;
    }

}

