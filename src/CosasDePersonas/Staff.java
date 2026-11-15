package CosasDePersonas;
import Excepciones.ElementoDuplicadoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Staff<T> extends Persona {
    public static int contador;
    int id_Empleado;
    private ETipoUsuario puesto;//recepcionista o administrador
    private double salarioBase;
    private String contrasenia;

    public Staff(String nombre, String dni,String contrasenia, String domicilio, String localidad, int id_Empleado, ETipoUsuario puesto, double salarioBase) {
        super(nombre, dni, domicilio, localidad);
        this.id_Empleado = id_Empleado;
        this.puesto = puesto;
        this.contrasenia=contrasenia;
        this.salarioBase = 300*salarioStaff();//el salario base es 300, se multiplica por el un valor segun el puesto del empleado
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getId_Empleado() {
        return id_Empleado;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public ETipoUsuario getPuesto() {
        return puesto;
    }

    public void setPuesto(ETipoUsuario puesto) {
        this.puesto = puesto;
    }

    public  boolean equals(Object object){
        if(this==object) return true;
        if(!(object instanceof Staff)) return false;

        Staff otro=(Staff) object;

        return getDni().equals(otro.getDni());
    }

    public int hashCode(){
        return getDni().hashCode();
    }

    //metodo para calcular los salarios del staff, devuelve un multiplicador que varia el salario base
    public int salarioStaff(){
        int resultado=0;
        if(puesto==ETipoUsuario.Administrador){
            resultado=4;
        }
        if(puesto==ETipoUsuario.Recepcionista){
            resultado=3;
        }
        if(puesto==ETipoUsuario.mantenimiento){
            resultado=2;
        }
        if(puesto==ETipoUsuario.Limpieza){
            resultado=1;
        }
        return resultado;
    }


}