import java.util.ArrayList;
import java.util.List;

public final class Cliente extends Persona{
    List<String> informacionPasajero;

    public Cliente(String nombre,String dni,String localidad,String domicilio){
        super(nombre, dni, domicilio, localidad);
        this.informacionPasajero=new ArrayList<>();
    }

    public void agregarInfoPasajero(String e){
        informacionPasajero.add(e);
    }
    public String verInfoPasajero(){
        String mensaje="";
        if(informacionPasajero.isEmpty()){
            mensaje="LstaVacia";
        }
        for(int i=0;i<informacionPasajero.size()){
            mensaje=mensaje+informacionPasajero.get(i);
        }
        return mensaje;
    }
}
