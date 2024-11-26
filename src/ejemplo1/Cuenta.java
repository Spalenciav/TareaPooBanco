package ejemplo1;

public class Cuenta {
    
    private String Titular;
    private int Saldo;
    private static final double IMPUESTO_4X1000 = 0.004; // Tasa del 4x1000

    // Constructor que asigna el titular basado en el cliente.
    Cuenta(Cliente cliente) {
        Titular = cliente.nombre;
        Saldo = 0; // Inicializamos el saldo en 0.
    }

    // Método para consignar dinero en la cuenta.
    boolean consignacion(int x) {
        if (x > 0) {
            Saldo += x; // Incrementamos el saldo.
            System.out.println("Consignación exitosa. Saldo actual: " + Saldo);
            return true;
        } else {
            System.out.println("La cantidad a consignar debe ser positiva.");
            return false;
        }
    }

    // Método para retirar dinero de la cuenta, aplicando el 4x1000.
    boolean retiro(int x) {
        double impuesto = x * IMPUESTO_4X1000;
        int totalConImpuesto = (int) Math.ceil(x + impuesto);

        if (x > 0 && Saldo >= totalConImpuesto) {
            Saldo -= totalConImpuesto; // Disminuimos el saldo incluyendo el impuesto.
            System.out.println("Retiro exitoso. Saldo actual: " + Saldo + ". Impuesto aplicado: " + (int) impuesto);
            return true;
        } else {
            System.out.println("Retiro fallido. Saldo insuficiente o cantidad no válida.");
            return false;
        }
    }

    // Método para transferir dinero a otra cuenta, aplicando el 4x1000.
    boolean transferencia(Cuenta destino, int x) {
        double impuesto = x * IMPUESTO_4X1000;
        int totalConImpuesto = (int) Math.ceil(x + impuesto);

        if (x > 0 && Saldo >= totalConImpuesto) {
            this.retiro(x); // Retiramos el dinero de la cuenta actual con el impuesto aplicado.
            destino.consignacion(x); // Lo consignamos en la cuenta de destino.
            System.out.println("Transferencia exitosa. Saldo actual: " + Saldo + ". Impuesto aplicado: " + (int) impuesto);
            return true;
        } else {
            System.out.println("Transferencia fallida. Saldo insuficiente o cantidad no válida.");
            return false;
        }
    }

    // Método para obtener el saldo actual.
    int getSaldo() {
        return this.Saldo;
    }
}