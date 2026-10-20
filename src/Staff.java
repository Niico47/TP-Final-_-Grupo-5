public class Staff<T> extends Persona{
    int id_Empleado;
    PuestoEmpleado puesto;
    double salarioBase;

    public Staff(String nombre, String dni, String domicilio, String localidad, int id_Empleado, PuestoEmpleado puesto, double salarioBase) {
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

    public PuestoEmpleado getPuesto() {
        return puesto;
    }

    public void setPuesto(PuestoEmpleado puesto) {
        this.puesto = puesto;
    }
}
