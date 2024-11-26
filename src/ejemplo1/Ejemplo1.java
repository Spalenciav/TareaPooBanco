package ejemplo1;

import java.util.Scanner;

public class Ejemplo1 {

    public static void main(String[] args) {
        // Crear cliente y cuenta
        Cliente Nestor = new Cliente("Néstor");
        Cuenta cuentaNestor = new Cuenta(Nestor);

        // Crear otra cuenta para transferencias
        Cliente Juan = new Cliente("Juan");
        Cuenta cuentaJuan = new Cuenta(Juan);

        // Variables para autenticación y límite
        String PIN = "1234"; // Simulamos un PIN fijo para autenticación.
        int limiteDiario = 20000000; // Límite diario de transacciones.
        int transaccionesHoy = 0; // Acumulador de transacciones realizadas en el día.

        // Scanner para leer entrada del usuario
        Scanner scanner = new Scanner(System.in);
        int opcion;

        // Menú interactivo
        do {
            System.out.println("\n--- MENÚ DE OPERACIONES ---");
            System.out.println("1. Consignación");
            System.out.println("2. Retiro");
            System.out.println("3. Transferencia");
            System.out.println("4. Consultar saldo");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1: // Consignación
                    System.out.print("Ingrese la cantidad a consignar: ");
                    int cantidadConsignar = scanner.nextInt();
                    cuentaNestor.consignacion(cantidadConsignar);
                    notificarTransaccion("Consignación", cantidadConsignar);
                    break;

                case 2: // Retiro
                    if (!autenticarUsuario(scanner, PIN)) break; // Verificar autenticación.
                    System.out.print("Ingrese la cantidad a retirar: ");
                    int cantidadRetirar = scanner.nextInt();

                    if ((transaccionesHoy + cantidadRetirar) > limiteDiario) {
                        System.out.println("Error: Excede el límite diario de transacciones.");
                    } else {
                        if (cuentaNestor.retiro(cantidadRetirar)) {
                            transaccionesHoy += cantidadRetirar;
                            notificarTransaccion("Retiro", cantidadRetirar);
                            verificarMontoAlto("Retiro", cantidadRetirar);
                        }
                    }
                    break;

                case 3: // Transferencia
                    if (!autenticarUsuario(scanner, PIN)) break; // Verificar autenticación.
                    System.out.print("Ingrese la cantidad a transferir a la cuenta de Juan: ");
                    int cantidadTransferir = scanner.nextInt();

                    if ((transaccionesHoy + cantidadTransferir) > limiteDiario) {
                        System.out.println("Error: Excede el límite diario de transacciones.");
                    } else {
                        if (cuentaNestor.transferencia(cuentaJuan, cantidadTransferir)) {
                            transaccionesHoy += cantidadTransferir;
                            notificarTransaccion("Transferencia", cantidadTransferir);
                            verificarMontoAlto("Transferencia", cantidadTransferir);
                        }
                    }
                    break;

                case 4: // Consultar saldo
                    System.out.println("Saldo actual de la cuenta de " + Nestor.nombre + ": " + cuentaNestor.getSaldo());
                    break;

                case 5: // Salir
                    System.out.println("Saliendo del programa...");
                    break;

                default:
                    System.out.println("Opción no válida. Por favor, intente de nuevo.");
            }
        } while (opcion != 5);

        scanner.close(); // Cerramos el Scanner al final
    }

    // Método para notificar transacciones
    private static void notificarTransaccion(String tipo, int monto) {
        System.out.println("Notificación: Se realizó una operación de tipo " + tipo + " por un valor de " + monto + " COP.");
    }

    // Método para verificar transacciones mayores a $50 millones
    private static void verificarMontoAlto(String tipo, int monto) {
        if (monto >= 50000000) {
            System.out.println("ALERTA: La operación de tipo " + tipo + " supera los $50 millones. Es obligatorio reportarla ante la UIAF.");
        }
    }

    // Método para autenticación del usuario
    private static boolean autenticarUsuario(Scanner scanner, String PIN) {
        System.out.print("Ingrese su PIN para continuar: ");
        String pinIngresado = scanner.next();
        if (pinIngresado.equals(PIN)) {
            System.out.println("Autenticación exitosa.");
            return true;
        } else {
            System.out.println("Autenticación fallida. Operación cancelada.");
            return false;
        }
    }
}