import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class CuentaBancaria {
    int numeroCuenta;
    String fechaApertura;
    int indiceCliente;

    public abstract void CompraRetiro(Scanner scanner);

    public abstract void PagoDeposito(Scanner scanner);

    protected ArrayList<Cliente> clientes;

    public CuentaBancaria(int numeroCuenta, String fechaApertura, int indiceCliente, ArrayList<Cliente> clientes) {
        this.numeroCuenta = numeroCuenta;
        this.fechaApertura = fechaApertura;
        this.indiceCliente = indiceCliente;
        this.clientes = clientes;
    }

    public static CuentaBancaria crearCuentaBancaria(Scanner scanner, ArrayList<Cliente> clientes) {
        int tipoCuenta = 0; // Variable para almacenar el tipo de cuenta

        try {
            System.out.println("\nSeleccione el tipo de cuenta:");
            System.out.println("1. Cuenta de débito");
            System.out.println("2. Tarjeta de crédito");
            tipoCuenta = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea
        } catch (InputMismatchException e) {
            System.out.println("\nTipo de cuenta no válido. Debe ser un número.");
            scanner.nextLine();
            return null;
        }

        if (tipoCuenta != 1 && tipoCuenta != 2) {
            System.out.println("\nTipo de cuenta no válido. Debe ser 1 (Cuenta de débito) o 2 (Tarjeta de crédito).");
            return null;
        }
        System.out.println("\nIngrese la fecha de apertura:");
        String fechaApertura = scanner.nextLine();
        System.out.println("\nIngrese el número de cuenta:");

        int numeroCuenta = 0;
        try {
            numeroCuenta = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("\nNúmero de cuenta no válido. Debe ser un número.");
            scanner.nextLine();
            return null;
        }
        int indiceCliente = 0;
        while (true) {
            try {
                System.out.println("\nSeleccione el índice del cliente asociado");
                for (int i = 0; i < clientes.size(); i++) {
                    System.out.println((i + 1) + ". " + clientes.get(i).nombre + " " + clientes.get(i).apellido);
                }
                indiceCliente = scanner.nextInt();
                scanner.nextLine();
                indiceCliente--; // Ajuste del índice

                if (indiceCliente < 0 || indiceCliente >= clientes.size()) {
                    throw new ArrayIndexOutOfBoundsException(); // Lanzar excepción si el índice está fuera de los
                                                                // límites
                }
                break; // Salir del bucle si el índice es válido
            } catch (InputMismatchException e) {
                System.out.println("\nÍndice no válido. Debe ser un número entero.");
                scanner.nextLine();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("\nEl índice no es válido. Intente nuevamente.");
            }
        }

        if (tipoCuenta == 1) {
            double saldoInicial = 0;
            boolean inputValido = false;
            do {
                try {
                    System.out.println("\nIngrese el saldo inicial:");
                    String input = scanner.nextLine();
                    saldoInicial = Double.parseDouble(input);

                    if (saldoInicial < 0) {
                        throw new IllegalArgumentException();
                    } else {
                        inputValido = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("\nEntrada inválida. Por favor, ingrese un número válido.");
                } catch (IllegalArgumentException f) {
                    System.out.println("\nEl saldo inicial no puede ser negativo. Por favor, inténtelo de nuevo.");
                }
            } while (!inputValido);

            System.out.println("\nIngrese el nip para realizar transferencias (sólo pueden ser números enteros):");
            int contraseña = scanner.nextInt();

            return new CuentaDebito(numeroCuenta, fechaApertura, indiceCliente, saldoInicial, clientes, contraseña);
        } else if (tipoCuenta == 2) {
            while (true) {
                try {
                    System.out.println("\nIngrese el límite de crédito:");
                    int limiteCredito = scanner.nextInt();
                    scanner.nextLine();

                    System.out.println("\nIngrese la tasa de interés:");
                    double tasaInteres = scanner.nextDouble();
                    scanner.nextLine();

                    System.out.println("\nIngrese la fecha de vencimiento (MM/YY):");
                    String fechaVencimiento = scanner.nextLine();

                    return new TarjetaCredito(numeroCuenta, fechaApertura, indiceCliente, limiteCredito, tasaInteres,
                            fechaVencimiento, clientes);
                } catch (InputMismatchException e) {
                    System.out.println("Error: Entrada no válida. Por favor, ingrese el tipo de dato correcto.");
                    scanner.nextLine();
                    continue;
                }
            }

        } else {
            System.out.println("\nTipo de cuenta no válido.");
            return null;
        }
    }

    public abstract double getSaldo();

    public abstract Cliente getCliente();

    public int getNumeroCuenta() {
        return numeroCuenta;
    }
}