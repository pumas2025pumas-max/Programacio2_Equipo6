package application;

import java.util.Scanner;
import listModule.ListExercise;
import stackModule.StackExercise;
import queueModule.QueueExercise;

//
public class MainProgram {

    private boolean running = true;
    private Exercise exercise;

    // Crea una instancia de MainProgram y ejecuta run en la misma
    public static void main(String[] args) {
        MainProgram program = new MainProgram();
        program.run();
    }

    // Crea una Variable Scanner, Ejecuta el ciclo y luego cierra el Scanner
    private void run() {
        Scanner sc = new Scanner(System.in);

        while (running) {
            selectExercise(sc);
        }

        sc.close();
        System.out.println("Programa finalizado con éxito.");
    }

    // Muestra las opciones para ejecutar los diferentes ejercicios
    private void selectExercise(Scanner scanner) {
        System.out.println("\n----- Main Program -----");
        System.out.println("1. Ejecutar ejercicio de Listas (ListExercise)");
        System.out.println("2. Ejecutar ejercicio de Pila (StackExercise)");
        System.out.println("3. Ejecutar ejercicio de Cola (QueueExercise)");
        System.out.println("4. Terminar el programa");
        System.out.print("Seleccione una opción: ");

        if (scanner.hasNextInt()) {
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    exercise = new ListExercise(scanner);
                    exercise.run();
                    break;
                case 2:
                    exercise = new StackExercise(scanner);
                    exercise.run();
                    break;
                case 3:
                    exercise = new QueueExercise(scanner);
                    exercise.run();
                    break;
                case 4:
                    running = false;
                    System.out.println("Saliendo del programa principal...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        } else {
            System.out.println("Error: Ingrese un número.");
            scanner.nextLine();
        }
    }
}
