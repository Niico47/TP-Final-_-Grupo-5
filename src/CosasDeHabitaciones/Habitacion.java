package CosasDeHabitaciones;

//La disponibilidad va a depender de su su estado es Disponible y de las reservas que los usuarios hallan echo en ella.
//
public class Habitacion {
    private String motivoDisponibilidad;
    int numeroHabitacion;
    ETipoHabitacion tipoHabitacion;
    EEstadoHabitacion estadoHabitacion;
    boolean reservada;
    double precioXnoche;

    public Habitacion(int numeroHabitacion, EEstadoHabitacion estadoHabitacion, ETipoHabitacion tipoHabitacion, boolean reservada, double precioXnoche) {
        this.numeroHabitacion = numeroHabitacion;
        this.estadoHabitacion = estadoHabitacion;
        this.tipoHabitacion = tipoHabitacion;
        this.reservada = reservada;
        this.precioXnoche = 500*precioNoche();
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




}

//public class CosasDeHabitaciones.Habitacion<T> {
//    int nroHabitacion;
//    CosasDeHabitaciones.ETipoHabitacion tipoHabitacion;
//    boolean disponibilidad;//ocupado o no;
//    List<String> estado;
//    Scanner sc=new Scanner(System.in);
//    public CosasDeHabitaciones.Habitacion(int nroHabitacion,boolean disponibilidad, TipoHabitacion tipo) {
//        this.nroHabitacion = nroHabitacion;
//        this.disponibilidad = disponibilidad;
//        this.tipo = tipo;
//        this.estado=new ArrayList<>();
//    }

//    public int getNroHabitacion() {
//        return nroHabitacion;
//    }
//
//    public boolean isDisponibilidad() {
//        return disponibilidad;
//    }
//
//    public void setDisponibilidad(boolean disponibilidad) {
//        this.disponibilidad = disponibilidad;
//    }
//
//    public void agregarDescripcionHabitacion(String e){
//        int num;
//        String mensaje="";
//        mensaje="Opcion 1: No se puede usar la habitacion \nOpcion 2: habilitar la habitacion";
//        num=sc.nextInt();
//        switch(num){
//            case 1:
//                estado.add(e);
//                setDisponibilidad(false);
//            case 2:
//                estado.add(e);
//                setDisponibilidad(true);
//        }
//    }
//    public String mostrarEstadoHabitacion(){
//        String mensaje="";
//        if(estado.isEmpty()){
//            mensaje="listaVacia";
//        }
//        for(int i=0;i<estado.size();i++){
//            mensaje=mensaje+estado.get(i);
//        }
//        return mensaje;
//    }
//
//}
