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

    public static void setContador(int contador) {
        Staff.contador = contador;
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

    //1.Este metodo crea un usuario y contraseña al cliente para entrar al sistema
    public String crearUsuarioCliente(Cliente cliente) {
        Scanner sc = new Scanner(System.in);
        String mensaje = "";
        //2.Esto solo lo puede hacer el administrador, aqui lo validamos
        if (puesto == ETipoUsuario.Administrador) {
            System.out.print("Nombre usuario: ");
            String nuevoUsuario = sc.nextLine();

            // 3. VALIDACIÓN de que no existan dos clientes con el mismo usuario
            if (existeUsuario(nuevoUsuario)) {
                throw new ElementoDuplicadoException("El usuario '" + nuevoUsuario + "' ya existe en el sistema.");
            }
            //El cliente siempre tiene usuario y contrasenia null hasta que se cambia
            cliente.setUsuario(nuevoUsuario);

            System.out.print("\nContrasenia: ");
            cliente.setContrasenia(sc.nextLine());
            listaClientes.add(cliente);
            mensaje = "\nUsuario creado con exito";
        } else {
            mensaje = "\nPara crear usuarios se necesita ser Administrador";
        }

        return mensaje;
    }
    //Devuelve lista de usuarios creados
    public StringBuilder usuariosCreados() {
        StringBuilder mensaje = new StringBuilder();
        for (int i = 0; i < listaClientes.size(); i++) {
            mensaje.append(listaClientes.get(i)).append("\n");
        }
        return mensaje;
    }
    //Elimina usuario de un cliente
    public void eliminarUsuario(Cliente cliente) {
        if(puesto==ETipoUsuario.Administrador) {
            for (Cliente c : listaClientes) {
                if (c.getUsuario().equalsIgnoreCase(cliente.getUsuario())) {
                    listaClientes.remove(cliente);
                    cliente.setContrasenia(null);
                    cliente.setUsuario(null);
                }
            }
        }
    }
    //busca un usuario dentro del staff
    private boolean existeUsuarioStaff(String usuario) {
        for (String staff : listaStaff) {
            if (staff.equals(usuario)) {
                return true; // Se encontró el duplicado
            }
        }
        return false; // El nombre está libre
    }
    //crea usuario de staff
    public String crearUsuarioStaff() {
        Scanner sc = new Scanner(System.in);
        String mensaje = "";
        String nuevoUsuario;
        String contrasenia;
        if (puesto == ETipoUsuario.Administrador) {
            System.out.print("Nombre usuario: ");
            nuevoUsuario = sc.nextLine();

            // 1. VALIDACIÓN que no haya usuarios repetidos
            if (existeUsuario(nuevoUsuario)) {
                throw new ElementoDuplicadoException("El usuario '" + nuevoUsuario + "' ya existe en el sistema.");
            }
            System.out.print("\nContrasenia: ");
            contrasenia = sc.nextLine();
            listaStaff.add(nuevoUsuario);
            listaStaff.add(contrasenia);
            mensaje = "\nUsuario creado con exito";
        } else {
            mensaje = "\nPara crear usuarios se necesita ser Administrador";
        }

        return mensaje;
    }
    //elimina usuario del staff
    public void eliminarStaff(String usuario){
        if(puesto==ETipoUsuario.Administrador){
            for(String e:listaStaff){
                if(e.equalsIgnoreCase(usuario)){
                    listaStaff.remove(usuario);
                }
            }
        }
    }
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