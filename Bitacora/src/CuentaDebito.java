import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class CuentaDebito extends CuentaBancaria implements Contrasenia {
    double saldo;

    private int contraseña;

    public CuentaDebito(int numeroCuenta, String fechaApertura, int indiceCliente, double saldoInicial,
            ArrayList<Cliente> clientes, int contraseña) {
        super(numeroCuenta, fechaApertura, indiceCliente, clientes);
        this.saldo = saldoInicial;
        this.contraseña = contraseña;
    }

    public int obtenerContraseña() {
        return contraseña;
    }

    public boolean verificarContraseña(int contraseña) {
        return this.contraseña == contraseña;
    }

    public CuentaDebito(int numeroCuenta, String fechaApertura, int indiceCliente, double saldoInicial,
            ArrayList<Cliente> clientes) {
        super(numeroCuenta, fechaApertura, indiceCliente, clientes);
        this.saldo = saldoInicial;
    }

    public void CompraRetiro(Scanner scanner) {
        try {
            System.out.println("\nIngrese la cantidad a retirar:");
            double cantidadRetirar = scanner.nextDouble();
            scanner.nextLine();
            if (saldo < cantidadRetirar) {
                System.out.println("\nSaldo insuficiente para realizar el retiro.");
                return;
            }
            saldo -= cantidadRetirar;
            System.out.println("\nRetiro realizado correctamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine(); // Limpiar el buffer del scanner
        }
    }

    public void PagoDeposito(Scanner scanner) {
        try {
            System.out.println("\nIngrese la cantidad a depositar:");
            double cantidadDepositar = scanner.nextDouble();
            scanner.nextLine();
            saldo += cantidadDepositar;
            System.out.println("\nDepósito realizado correctamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine(); // Limpiar el buffer del scanner
        }
    }

    public static void transferir(ArrayList<CuentaDebito> cuentasDebito, Scanner scanner) {
        if (cuentasDebito.isEmpty() || cuentasDebito.size() < 2) {
            System.out.println("\nDebe haber al menos dos cuentas de débito creadas para transferir.");
            return;
        }
        try {
            System.out.println("\nCuentas de débito disponibles:");
            for (int i = 0; i < cuentasDebito.size(); i++) {
                CuentaDebito cuenta = cuentasDebito.get(i);
                Cliente cliente = cuenta.getCliente();
                System.out
                        .println(i + ". Número de cuenta: " + cuenta.getNumeroCuenta() + ", Cliente: " + cliente.nombre
                                + " " + cliente.apellido);
            }
            System.out.println("\nSeleccione el índice de la cuenta de débito origen:");
            int indiceOrigen = scanner.nextInt();
            scanner.nextLine();
            if (indiceOrigen < 0 || indiceOrigen >= cuentasDebito.size()) {
                System.out.println("\nÍndice de cuenta de débito origen no válido.");
                return;
            }
            System.out.println("Índice de origen seleccionado: " + indiceOrigen);

            System.out.println("\nSeleccione el índice de la cuenta de débito destino:");
            int indiceDestino = scanner.nextInt();
            scanner.nextLine();
            if (indiceDestino < 0 || indiceDestino >= cuentasDebito.size() || indiceOrigen == indiceDestino) {
                System.out.println("\nÍndice de cuenta de débito destino no válido.");
                return;
            }

            System.out.println("\nIngrese la cantidad a transferir:");
            double cantidadTransferir = scanner.nextDouble();
            scanner.nextLine();

            if (cantidadTransferir <= 0) {
                System.out.println("\nLa cantidad a transferir debe ser positiva.");
                return;
            }

            if (cuentasDebito.get(indiceOrigen).getSaldo() < cantidadTransferir) {
                System.out.println("\nSaldo insuficiente para realizar la transferencia.");
                return;
            }

            boolean contraseñaCorrecta = false;

            do {
                try {
                    String inputContraseña = JOptionPane
                            .showInputDialog("Ingrese la contraseña para realizar la transferencia:");
                    int contraseña = Integer.parseInt(inputContraseña);

                    if (!cuentasDebito.get(indiceOrigen).verificarContraseña(contraseña)) {
                        JOptionPane.showMessageDialog(null,
                                "Contraseña incorrecta. No se puede realizar la transferencia.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        contraseñaCorrecta = true;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "La contraseña debe ser un número entero.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while (!contraseñaCorrecta);

            CuentaDebito cuentaOrigen = cuentasDebito.get(indiceOrigen);
            CuentaDebito cuentaDestino = cuentasDebito.get(indiceDestino);
            cuentaOrigen.saldo -= cantidadTransferir;
            cuentaDestino.saldo += cantidadTransferir;
            System.out.println("\nTransferencia realizada correctamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }

    public double getSaldo() {
        return saldo; // Devuelve el saldo de la cuenta de débito
    }

    public Cliente getCliente() {
        return clientes.get(indiceCliente); // Devuelve el cliente asociado
    }

    public int getNumeroCuenta() {
        return numeroCuenta;
    }
}