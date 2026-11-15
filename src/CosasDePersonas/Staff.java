package CosasDePersonas;
import Excepciones.ElementoDuplicadoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Staff extends Persona {
    private static int contador = 1; // Contador global
    private int id_Empleado;
    private ETipoUsuario puesto;//recepcionista o administrador
    private double salarioBase;
    private String contrasenia;
    private boolean estadoStaff; // Esto es para cuando se elimina un Staff, para poder manejarlo en la base de datos

    public Staff(String nombre, String dni, String domicilio, String localidad, ETipoUsuario puesto, double salarioBase, String contrasenia) {
        super(nombre, dni, domicilio, localidad);
        this.id_Empleado = contador;
        this.puesto = puesto;
        this.salarioBase = 300 * salarioStaff();//el salario base es 300, se multiplica por el un valor segun el puesto del empleado
        this.contrasenia = contrasenia;
        this.estadoStaff = true;
        contador++;
    }

    public int getId_Empleado() {
        return id_Empleado;
    }

    public ETipoUsuario getPuesto() {
        return puesto;
    }

    public void setPuesto(ETipoUsuario puesto) {
        this.puesto = puesto;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isEstadoStaff() {
        return estadoStaff;
    }

    public void setEstadoStaff(boolean estadoStaff) {
        this.estadoStaff = estadoStaff;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Staff)) return false;

        Staff otro = (Staff) object;

        return getDni().equals(otro.getDni());
    }

    public int hashCode() {
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