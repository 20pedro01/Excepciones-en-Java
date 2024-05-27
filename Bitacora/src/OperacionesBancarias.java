import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class OperacionesBancarias {
    private ArrayList<Cliente> clientes;
    private ArrayList<TarjetaCredito> tarjetasCredito;
    private ArrayList<CuentaDebito> cuentasDebito;

    public OperacionesBancarias() {
        this.clientes = new ArrayList<>();
        this.tarjetasCredito = new ArrayList<>();
        this.cuentasDebito = new ArrayList<>();
    }

    // Métodos para acceder a los campos privados
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public ArrayList<TarjetaCredito> getTarjetasCredito() {
        return tarjetasCredito;
    }

    public ArrayList<CuentaDebito> getCuentasDebito() {
        return cuentasDebito;
    }

    // Método para agregar un cliente a la lista de clientes
    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void agregarTarjetaCredito(TarjetaCredito tarjetaCredito) {
        tarjetasCredito.add(tarjetaCredito);
    }

    public void agregarCuentaDebito(CuentaDebito cuentaDebito) {
        cuentasDebito.add(cuentaDebito);
    }

    public void CompraRetiro(Scanner scanner, int tipo) {
        try {
            if (tipo == 1) { // Tarjeta de crédito
                System.out.println("\nSeleccione la tarjeta de crédito:");
                for (int i = 0; i < tarjetasCredito.size(); i++) {
                    TarjetaCredito tarjeta = tarjetasCredito.get(i);
                    System.out.println(i + ". Número de cuenta: " + tarjeta.numeroCuenta + ", Cliente: "
                            + clientes.get(tarjeta.indiceCliente).nombre + " "
                            + clientes.get(tarjeta.indiceCliente).apellido);
                }
                System.out.println("\nSeleccione el índice de la tarjeta de crédito:");
                int indiceTarjeta = scanner.nextInt();
                scanner.nextLine();
                if (indiceTarjeta < 0 || indiceTarjeta >= tarjetasCredito.size()) {
                    System.out.println("\nÍndice de tarjeta de crédito no válido.");
                    return;
                }
                tarjetasCredito.get(indiceTarjeta).CompraRetiro(scanner);
            } else if (tipo == 2) { // Cuenta de débito
                System.out.println("\nSeleccione la cuenta de débito:");
                for (int i = 0; i < cuentasDebito.size(); i++) {
                    CuentaDebito cuenta = cuentasDebito.get(i);
                    System.out.println(i + ". Número de cuenta: " + cuenta.numeroCuenta + ", Cliente: "
                            + clientes.get(cuenta.indiceCliente).nombre + " "
                            + clientes.get(cuenta.indiceCliente).apellido);
                }
                System.out.println("\nSeleccione el índice de la cuenta de débito:");
                int indiceCuenta = scanner.nextInt();
                scanner.nextLine();
                if (indiceCuenta < 0 || indiceCuenta >= cuentasDebito.size()) {
                    System.out.println("\nÍndice de cuenta de débito no válido.");
                    return;
                }
                cuentasDebito.get(indiceCuenta).CompraRetiro(scanner);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine(); // Limpiar el buffer del scanner
        }
    }

    public void PagoDeposito(Scanner scanner, int tipo) {
        try {
            if (tipo == 1) { // Tarjeta de crédito
                System.out.println("\nSeleccione la tarjeta de crédito:");
                for (int i = 0; i < tarjetasCredito.size(); i++) {
                    TarjetaCredito tarjeta = tarjetasCredito.get(i);
                    System.out.println(i + ". Número de cuenta: " + tarjeta.numeroCuenta + ", Cliente: "
                            + clientes.get(tarjeta.indiceCliente).nombre + " "
                            + clientes.get(tarjeta.indiceCliente).apellido);
                }
                System.out.println("\nSeleccione el índice de la tarjeta de crédito:");
                int indiceTarjeta = scanner.nextInt();
                scanner.nextLine();
                if (indiceTarjeta < 0 || indiceTarjeta >= tarjetasCredito.size()) {
                    System.out.println("\nÍndice de tarjeta de crédito no válido.");
                    return;
                }
                tarjetasCredito.get(indiceTarjeta).PagoDeposito(scanner);
            } else if (tipo == 2) { // Cuenta de débito
                System.out.println("\nSeleccione la cuenta de débito:");
                for (int i = 0; i < cuentasDebito.size(); i++) {
                    CuentaDebito cuenta = cuentasDebito.get(i);
                    System.out.println(i + ". Número de cuenta: " + cuenta.numeroCuenta + ", Cliente: "
                            + clientes.get(cuenta.indiceCliente).nombre + " "
                            + clientes.get(cuenta.indiceCliente).apellido);
                }
                System.out.println("\nSeleccione el índice de la cuenta de débito:");
                int indiceCuenta = scanner.nextInt();
                scanner.nextLine();
                if (indiceCuenta < 0 || indiceCuenta >= cuentasDebito.size()) {
                    System.out.println("\nÍndice de cuenta de débito no válido.");
                    return;
                }
                cuentasDebito.get(indiceCuenta).PagoDeposito(scanner);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine(); 
        }
    }

    public void consultarSaldo(Scanner scanner, int tipoCuenta) {
        try {
            ArrayList<? extends CuentaBancaria> cuentas;
            String tipoCuentaStr;

            if (tipoCuenta == 1) {
                cuentas = getCuentasDebito();
                tipoCuentaStr = "cuentas de débito";
            } else if (tipoCuenta == 2) {
                cuentas = getTarjetasCredito();
                tipoCuentaStr = "tarjetas de crédito";
            } else {
                System.out.println("Tipo de cuenta no válido.");
                return;
            }

            if (cuentas.isEmpty()) {
                System.out.println("No hay " + tipoCuentaStr + " disponibles para consultar el saldo.");
                return;
            }

            System.out.println("\nSeleccione la " + tipoCuentaStr + " de la que desea consultar el saldo:");
            for (int i = 0; i < cuentas.size(); i++) {
                CuentaBancaria cuenta = cuentas.get(i);
                System.out.println(
                        i + ". Para la " + tipoCuentaStr + " con num. " + cuenta.getNumeroCuenta() + " a nombre de: "
                                + cuenta.getCliente().getNombre() + " " + cuenta.getCliente().getApellido());
            }

            int indiceCuenta = scanner.nextInt();
            if (indiceCuenta < 0 || indiceCuenta >= cuentas.size()) {
                System.out.println("Índice de " + tipoCuentaStr + " inválido.");
                return;
            }

            CuentaBancaria cuentaSeleccionada = cuentas.get(indiceCuenta);
            System.out.println("Saldo de la " + tipoCuentaStr + " " + cuentaSeleccionada.getNumeroCuenta() + " de "
                    + cuentaSeleccionada.getCliente().getNombre() + " " + cuentaSeleccionada.getCliente().getApellido()
                    + ": " + cuentaSeleccionada.getSaldo());
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine(); // Limpiar el buffer del scanner
        }
    }

    public void consultarSaldoTotal(ArrayList<CuentaBancaria> cuentas) {
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas disponibles para consultar el saldo total.");
            return;
        }

        System.out.println("\nSaldo de todas las cuentas:");

        for (CuentaBancaria cuenta : cuentas) {
            int numeroCuenta = cuenta.getNumeroCuenta();
            String nombreCliente = cuenta.getCliente().getNombre() + " " + cuenta.getCliente().getApellido();
            String saldo = String.valueOf(cuenta.getSaldo());

            System.out.println("\n Número de Cuenta: " + numeroCuenta);
            System.out.println("Nombre del Cliente: " + nombreCliente);
            System.out.println("Saldo: " + saldo);
            System.out.println();
        }
    }
}