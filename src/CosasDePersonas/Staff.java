package CosasDePersonas;

public class Staff extends Persona {
    private int idEmpleado;
    private ETipoUsuario puesto; // recepcionista o administrador
    private double salarioBase;
    private String contrasenia;
    private boolean estadoStaff; // Esto es para cuando se elimina un Staff, para poder manejarlo en la base de datos

    public Staff(int idEmpleado, String nombre, String dni, String domicilio, String localidad, ETipoUsuario puesto, double salarioBase, String contrasenia) {
        super(nombre, dni, domicilio, localidad);
        this.idEmpleado = idEmpleado;
        this.puesto = puesto;
        this.salarioBase = salarioBase;
        this.contrasenia = contrasenia;
        this.estadoStaff = true;
    }

    public int getIdEmpleado() {
        return idEmpleado;
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
        if(puesto==ETipoUsuario.Mantenimiento){
            resultado=2;
        }
        if(puesto==ETipoUsuario.Limpieza){
            resultado=1;
        }
        return resultado;
    }
}