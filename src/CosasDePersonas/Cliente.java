package CosasDePersonas;

import java.util.ArrayList;
import java.util.List;

public final class Cliente extends Persona {
    List<String> informacionPasajero;
    private String usuario;
    private String contrasenia;

    public Cliente(String nombre, String dni, String localidad, String domicilio){
        super(nombre, dni, domicilio, localidad);
        this.informacionPasajero=new ArrayList<>();
        this.usuario=null;
        this.contrasenia=null;

    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    
    public void agregarInfoPasajero(String e){
        informacionPasajero.add(e);
    }

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

    public String hacerReserva(){
        return "Reseva hecha, cuarto numero 4 ahora acupado";
    }

    public String cancelarReserva(){
        return "Reseva cancelada, cuarto numero 4 ahora libre";
    }
}
