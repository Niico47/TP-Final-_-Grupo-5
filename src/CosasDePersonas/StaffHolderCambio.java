package CosasDePersonas;

import Excepciones.ElementoDuplicadoException;
import java.util.Scanner;

public class StaffHolderCambio extends Persona {
    private static int contador = 1; // Contador global
    private int id_Empleado;
    private ETipoUsuario puesto;//recepcionista o administrador
    private double salarioBase;
    private String contrasenia;
    private boolean estadoStaff; // Esto es para cuando se elimina un Staff, para poder manejarlo en la base de datos

    public StaffHolderCambio(String nombre, String dni, String domicilio, String localidad, ETipoUsuario puesto, double salarioBase, String contrasenia) {
        super(nombre, dni, domicilio, localidad);
        this.id_Empleado = contador;
        this.puesto = puesto;
        this.salarioBase = salarioBase*salarioStaff();//el salario base es 300, se multiplica por el un valor segun el puesto del empleado
        this.contrasenia = contrasenia;
        this.estadoStaff = true;
        contador++;
    }

    @Override
    public String toString() {
        return super.toString() +
                "id_Empleado=" + id_Empleado +
                ", puesto=" + puesto +
                ", salarioBase=" + salarioBase +
                ", contrasenia='" + contrasenia + '\'' +
                ", estadoStaff=" + estadoStaff +
                '}';
    }

    public static int getContador() {
        return contador;
    }


    public int getId_Empleado() {
        return id_Empleado;
    }

    public void setId_Empleado(int id_Empleado) {
        this.id_Empleado = id_Empleado;
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

    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    //metodo para calcular los salarios del staff, devuelve un multiplicador que varia el salario base
    public double salarioStaff(){
        double resultado=0;
        if(puesto==ETipoUsuario.Administrador){
            resultado=1.5;
        }
        if(puesto==ETipoUsuario.Recepcionista){
            resultado=1.25;
        }
        if(puesto==ETipoUsuario.mantenimiento){
            resultado=1.1;
        }
        if(puesto==ETipoUsuario.Limpieza){
            resultado=1;
        }
        return resultado;
    }


}