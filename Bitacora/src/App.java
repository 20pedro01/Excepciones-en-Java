import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        OperacionesBancarias operaciones = new OperacionesBancarias();

        while (true) {
            System.out.println("\nSeleccione una opción:");
            System.out.println("1. Crear cliente");
            System.out.println("2. Crear cuenta");
            System.out.println("3. Realizar compra con tarjeta de crédito");
            System.out.println("4. Pagar tarjeta de crédito");
            System.out.println("5. Depositar a cuenta de débito");
            System.out.println("6. Retirar a cuenta de débito");
            System.out.println("7. Transferir entre cuentas de débito");
            System.out.println("8. Consultar saldo de una cuenta en específico");
            System.out.println("9. Consultar saldo total de todas las cuentas");
            System.out.println("10. Salir del programa");
            System.out.println("");

            int opcion = -1;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida. Por favor, ingrese un número entero.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    Cliente cliente = Cliente.crearCliente(scanner);
                    if (cliente != null) {
                        operaciones.agregarCliente(cliente);
                    }
                    break;
                case 2:
                    try {
                        if (operaciones.getClientes().isEmpty()) {
                            throw new NullPointerException();
                        }
                        CuentaBancaria cuentaBancaria = CuentaBancaria.crearCuentaBancaria(scanner,
                                operaciones.getClientes());
                        if (cuentaBancaria != null) {
                            if (cuentaBancaria instanceof TarjetaCredito) {
                                operaciones.getTarjetasCredito().add((TarjetaCredito) cuentaBancaria);
                                System.out.println("\nTarjeta de crédito creada exitosamente.");
                            } else if (cuentaBancaria instanceof CuentaDebito) {
                                operaciones.getCuentasDebito().add((CuentaDebito) cuentaBancaria);
                                System.out.println("\nCuenta de débito creada exitosamente.");
                            }
                            break;
                        }
                    } catch (NullPointerException e) {
                        System.out.println("La lista de clientes está vacía. Debe crear un cliente primero.");
                        break;
                    }
                    break;
                case 3:
                    if (operaciones.getTarjetasCredito().isEmpty()) {
                        System.out.println("\nNo hay tarjetas de crédito creadas para realizar compras.");
                        break;
                    }
                    operaciones.CompraRetiro(scanner, 1); // 1 para tarjeta de crédito
                    break;
                case 4:
                    if (operaciones.getTarjetasCredito().isEmpty()) {
                        System.out.println("\nNo hay tarjetas de crédito creadas para pagar.");
                        break;
                    }
                    operaciones.PagoDeposito(scanner, 1); // 1 para tarjeta de crédito
                    break;
                case 5:
                    if (operaciones.getCuentasDebito().isEmpty()) {
                        System.out.println("\nNo hay cuentas de débito creadas para depositar.");
                        break;
                    }
                    operaciones.PagoDeposito(scanner, 2); // 2 para cuenta de débito
                    break;
                case 6:
                    if (operaciones.getCuentasDebito().isEmpty()) {
                        System.out.println("\nNo hay cuentas de débito creadas para retirar.");
                        break;
                    }
                    operaciones.CompraRetiro(scanner, 2); // 2 para cuenta de débito
                    break;
                case 7:
                    CuentaDebito.transferir(operaciones.getCuentasDebito(), scanner);
                    break;
                case 8:
                    System.out.println("\nSeleccione el tipo de cuenta para consultar el saldo:");
                    System.out.println("1. Cuenta de débito");
                    System.out.println("2. Tarjeta de crédito");
                    int tipoCuenta = scanner.nextInt();
                    scanner.nextLine();
                    if (tipoCuenta == 1) {
                        operaciones.consultarSaldo(scanner, 1); // 1 para cuenta de débito
                    } else if (tipoCuenta == 2) {
                        operaciones.consultarSaldo(scanner, 2); // 2 para tarjeta de crédito
                    } else {
                        System.out.println("\nTipo de cuenta no válido.");
                    }
                    break;
                case 9:
                    ArrayList<CuentaBancaria> todasLasCuentas = new ArrayList<>();
                    todasLasCuentas.addAll(operaciones.getCuentasDebito());
                    todasLasCuentas.addAll(operaciones.getTarjetasCredito());
                    operaciones.consultarSaldoTotal(todasLasCuentas);
                    break;
                case 10:
                    System.out.println("¡Hasta luego!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}