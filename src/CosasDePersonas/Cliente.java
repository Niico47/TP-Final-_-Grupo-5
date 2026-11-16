package CosasDePersonas;

import java.util.ArrayList;
import java.util.List;

public final class Cliente extends Persona {
    List<String> informacionPasajero; // esto guarda informacion de reservas pasadas del cliente
    private boolean estadoCliente; // Esto es para cuando se elimina un Cliente, para poder manejarlo en la base de datos

    public Cliente(String nombre, String dni, String domicilio, String localidad) {
        super(nombre, dni, domicilio, localidad);
        this.informacionPasajero = new ArrayList<>();
        this.estadoCliente = true;
    }

    public List<String> getInformacionPasajero() {
        return informacionPasajero;
    }

    public boolean isEstadoCliente() {
        return estadoCliente;
    }

    public void setEstadoCliente(boolean estadoCliente) {
        this.estadoCliente = estadoCliente;
    }

    public void agregarInfoPasajero(String e){
        informacionPasajero.add(e);
    }
    //Devuelve una lista de informacion del pasajero en el hotel
    public String verInfoPasajero(){
        String mensaje="";
        if(informacionPasajero.isEmpty()){
            mensaje="LstaVacia";
        }
        for(int i=0;i<informacionPasajero.size();i++){
            mensaje=mensaje+informacionPasajero.get(i);
        }
        return mensaje;
    }
}
