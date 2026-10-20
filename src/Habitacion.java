import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Habitacion<T> {
    int nroHabitacion;
    TipoHabitacion tipo;
    boolean disponibilidad;//ocupado o no;
    List<String> estado;
    Scanner sc=new Scanner(System.in);
    public Habitacion(int nroHabitacion,boolean disponibilidad, TipoHabitacion tipo) {
        this.nroHabitacion = nroHabitacion;
        this.disponibilidad = disponibilidad;
        this.tipo = tipo;
        this.estado=new ArrayList<>();
    }

    public int getNroHabitacion() {
        return nroHabitacion;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public void agregarDescripcionHabitacion(String e){
        int num;
        String mensaje="";
        mensaje="Opcion 1: No se puede usar la habitacion \nOpcion 2: habilitar la habitacion";
        num=sc.nextInt();
        switch(num){
            case 1:
                estado.add(e);
                setDisponibilidad(false);
            case 2:
                estado.add(e);
                setDisponibilidad(true);
        }
    }
    public String mostrarEstadoHabitacion(){
        String mensaje="";
        if(estado.isEmpty()){
            mensaje="listaVacia";
        }
        for(int i=0;i<estado.size();i++){
            mensaje=mensaje+estado.get(i);
        }
        return mensaje;
    }

}
