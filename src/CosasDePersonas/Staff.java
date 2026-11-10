package CosasDePersonas;

import java.util.Scanner;

public class Staff<T> extends Persona {
    int id_Empleado;
    ETipoUsuario puesto;
    double salarioBase;

    public Staff(String nombre, String dni, String domicilio, String localidad, int id_Empleado, ETipoUsuario puesto, double salarioBase) {
        super(nombre, dni, domicilio, localidad);
        this.id_Empleado = id_Empleado;
        this.puesto = puesto;
        this.salarioBase = salarioBase;
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

    public String crearUsuario(Cliente cliente) {
        Scanner sc = new Scanner(System.in);
        String mensaje = "";
        if (puesto == ETipoUsuario.Administrador) {
            mensaje = "Nombre usuario: ";
            cliente.setUsuario(sc.nextLine());
            mensaje = "\nContrasenia: ";
            cliente.setContrasenia(sc.nextLine());
            mensaje = "\nUsuario creado con exito";
        } else {
            mensaje = "\nPara crear usuarios se necesita ser GERENTE";
        }
        return mensaje;
    }
}
