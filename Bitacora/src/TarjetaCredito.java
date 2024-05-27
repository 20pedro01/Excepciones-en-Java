import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TarjetaCredito extends CuentaBancaria {
    int limiteCredito;
    double montoUtilizado;
    double tasaInteres;
    String fechaVencimiento;

    public TarjetaCredito(int numeroCuenta, String fechaApertura, int indiceCliente, int limiteCredito,
            double tasaInteres, String fechaVencimiento, ArrayList<Cliente> clientes) {
        super(numeroCuenta, fechaApertura, indiceCliente, clientes);
        this.limiteCredito = limiteCredito;
        this.montoUtilizado = 0; // Inicializamos el monto utilizado en 0
        this.tasaInteres = tasaInteres;
        this.fechaVencimiento = fechaVencimiento;
    }

    public void CompraRetiro(Scanner scanner) {
        try {
            System.out.println("\nIngrese el monto de la compra:");
            double montoCompra = scanner.nextDouble();
            scanner.nextLine();
            double saldoDisponible = limiteCredito - montoUtilizado;
            if (saldoDisponible < montoCompra) {
                System.out.println("\nCrédito insuficiente.");
                return;
            }
            montoUtilizado += montoCompra;
            System.out.println("\nCompra realizada.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine(); // Limpiar el buffer del scanner
        }
    }

    public void PagoDeposito(Scanner scanner) {
        try {
            System.out.println("\nIngrese el monto a pagar:");
            double cantidadPagar = scanner.nextDouble();
            scanner.nextLine();
            montoUtilizado -= cantidadPagar;
            System.out.println("\nPago realizado correctamente.");
        } catch (InputMismatchException e) {
            System.out.println("Error: Ingrese un número válido.");
            scanner.nextLine();
        }
    }

    public double getSaldo() {
        return montoUtilizado; // Devuelve el monto utilizado como saldo
    }

    public Cliente getCliente() {
        return clientes.get(indiceCliente); // Devuelve el cliente asociado
    }
}