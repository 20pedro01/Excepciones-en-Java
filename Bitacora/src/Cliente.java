import java.util.InputMismatchException;
import java.util.Scanner;

public class Cliente {
    String nombre;
    String apellido;
    int edad;
    String direccion;
    String telefono;

    public Cliente(String nombre, String apellido, int edad, String direccion, String telefono) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public static Cliente crearCliente(Scanner scanner) {
        String nombre = "";
        String apellido = "";
        int edad = 0;
        String direccion = "";
        String telefono = "";

        System.out.println("\nIngrese el nombre del cliente:");
        nombre = scanner.nextLine();

        System.out.println("\nIngrese los apellidos del cliente:");
        apellido = scanner.nextLine();

        while (true) {
            try {
                System.out.println("\nIngrese la edad del cliente:");
                edad = scanner.nextInt();
                scanner.nextLine();
                break; // Salir del bucle si la edad se ingresa correctamente
            } catch (InputMismatchException e) {
                System.out.println("\nHa intentado ingresar un tipo de dato distinto al solicitado.");
                System.out.println("Por favor, ingrese nuevamente la edad del cliente.");
                scanner.nextLine();
            }
        }

        System.out.println("\nIngrese la dirección del cliente:");
        direccion = scanner.nextLine();

        while (true) {
            System.out.println("\nIngrese el teléfono del cliente (10 dígitos):");
            telefono = scanner.nextLine();
            if (telefono.matches("\\d{10}")) {
                break;
            } else {
                System.out.println("Error: El teléfono debe contener exactamente 10 dígitos.");
            }
        }

        // Cliente creado exitosamente
        System.out.println("\nCliente creado exitosamente");
        return new Cliente(nombre, apellido, edad, direccion, telefono);
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }
}