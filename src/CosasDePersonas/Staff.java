package CosasDePersonas;

import Excepciones.ElementoDuplicadoException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Staff<T> extends Persona {
    private List<Cliente> listaClientes = new ArrayList<>();
    private List<String> listaStaff = new ArrayList<>();
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

    private boolean existeUsuario(String usuario) {
        for (Cliente c : listaClientes) {
            if (c.getUsuario().equals(usuario)) {
                return true; // Se encontró el duplicado
            }
        }
        return false; // El nombre está libre
    }

    public String crearUsuario(Cliente cliente) {
        Scanner sc = new Scanner(System.in);
        String mensaje = "";

        if (puesto == ETipoUsuario.Administrador) {
            System.out.print("Nombre usuario: ");
            String nuevoUsuario = sc.nextLine();

            // 1. VALIDACIÓN:
            if (existeUsuario(nuevoUsuario)) {
                throw new ElementoDuplicadoException("El usuario '" + nuevoUsuario + "' ya existe en el sistema.");
            }

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

    public StringBuilder usuariosCreados() {
        StringBuilder mensaje = new StringBuilder();
        for (int i = 0; i < listaClientes.size(); i++) {
            mensaje.append(listaClientes.get(i)).append("\n");
        }
        return mensaje;
    }

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

    private boolean existeUsuarioStaff(String usuario) {
        for (String staff : listaStaff) {
            if (staff.equals(usuario)) {
                return true; // Se encontró el duplicado
            }
        }
        return false; // El nombre está libre
    }

    public String crearUsuarioStaff() {
        Scanner sc = new Scanner(System.in);
        String mensaje = "";
        String nuevoUsuario;
        String contrasenia;
        if (puesto == ETipoUsuario.Administrador) {
            System.out.print("Nombre usuario: ");
            nuevoUsuario = sc.nextLine();

            // 1. VALIDACIÓN:
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

    public void eliminarStaff(String usuario){
        if(puesto==ETipoUsuario.Administrador){
            for(String e:listaStaff){
                if(e.equalsIgnoreCase(usuario)){
                    listaStaff.remove(usuario);
                }
            }
        }
    }

}