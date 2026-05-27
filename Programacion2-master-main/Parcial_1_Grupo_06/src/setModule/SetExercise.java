package setModule;

import java.util.Scanner;
import application.Exercise;

public class SetExercise extends Exercise {
	private int currentPhase = 0;
	private boolean firstTime = true;
	private SimpleSet<String> setA;
	private SimpleSet<String> setB;

	public SetExercise(Scanner scanner) {
		super(scanner);
		this.setA = new SimpleLinkedSet<>();
		this.setB = new SimpleLinkedSet<>();
	}

	@Override
	protected void exerciseLogic() {
		switch (currentPhase) {
			case 1:
				trabajarConSet(setA, "A");
				break;
			case 2:
				trabajarConSet(setB, "B");
				break;
			case 3:
				unionLogic();
				break;
			case 4:
				intersectLogic();
				break;
			case 5:
				differenceLogic();
				break;
			case 6:
				menuLogic();
				break;
			default:
				System.out.println("Opción no válida, volviendo al menú.");
				currentPhase = 6;
				break;
		}
	}

	private void menuLogic() {
		if (firstTime) {
			System.out.println("¡Bienvenido al ejercicio de Set (Conjunto)!");
			System.out.println("--------------------");
			firstTime = false;
		}

		System.out.println("\n--- SET A ---");
		mostrarSet(setA);
		System.out.println("--- SET B ---");
		mostrarSet(setB);

		System.out.println("\nElija una opción:");
		System.out.println("1. Trabajar con Set A (agregar/remover)");
		System.out.println("2. Trabajar con Set B (agregar/remover)");
		System.out.println("3. Unión (A ∪ B)");
		System.out.println("4. Intersección (A ∩ B)");
		System.out.println("5. Diferencia (A - B o B - A)");
		System.out.println("6. Salir al Menú Principal");

		if (scanner.hasNextInt()) {
			currentPhase = scanner.nextInt();
			scanner.nextLine();
			if (currentPhase == 6) {
				running = false;
			}
		} else {
			System.out.println("Error: Ingrese un número.");
			scanner.nextLine();
		}
	}

	// Muestra los elementos de un set
	private void mostrarSet(SimpleSet<String> set) {
		Object[] elementos = set.toArray();
		System.out.print("Elementos: {");
		for (int i = 0; i < elementos.length; i++) {
			System.out.print(elementos[i] + (i < elementos.length - 1 ? ", " : ""));
		}
		System.out.println("}");
		System.out.println("Cantidad: " + set.size() + " | Vacío: " + (set.isEmpty() ? "Sí" : "No"));
	}

	// Submenu para trabajar con un set especifico (agregar o remover)
	private void trabajarConSet(SimpleSet<String> set, String nombre) {
		System.out.println("\n--- Trabajando con Set " + nombre + " ---");
		mostrarSet(set);
		System.out.println("\n1. Agregar elemento");
		System.out.println("2. Remover elemento");

		if (scanner.hasNextInt()) {
			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1) {
				agregarElemento(set, nombre);
			} else if (opcion == 2) {
				removerElemento(set, nombre);
			} else {
				System.out.println("Opción no válida.");
			}
		} else {
			System.out.println("Error: Ingrese un número.");
			scanner.nextLine();
		}
		currentPhase = 0;
	}

	// Agrega un elemento al set
	private void agregarElemento(SimpleSet<String> set, String nombre) {
		System.out.println("Escriba el elemento a agregar al Set " + nombre + ":");
		String elemento = scanner.nextLine();
		if (set.add(elemento)) {
			System.out.println("¡Elemento agregado exitosamente!");
		} else {
			System.out.println("El elemento ya existe en el Set, no se agregó.");
		}
		mostrarSet(set);
		if (preguntarRepetir()) {
			agregarElemento(set, nombre);
		}
	}

	// Remueve un elemento del set
	private void removerElemento(SimpleSet<String> set, String nombre) {
		if (set.isEmpty()) {
			System.out.println("El Set " + nombre + " está vacío, no hay elementos para remover.");
			return;
		}
		System.out.println("Escriba el elemento a remover del Set " + nombre + ":");
		String elemento = scanner.nextLine();
		if (set.remove(elemento)) {
			System.out.println("¡Elemento removido exitosamente!");
		} else {
			System.out.println("El elemento no existe en el Set, no se removió.");
		}
		mostrarSet(set);
		if (preguntarRepetir()) {
			removerElemento(set, nombre);
		}
	}

	// Muestra la union de A y B
	private void unionLogic() {
		System.out.println("\n--- Unión (A ∪ B) ---");
		SimpleSet<String> resultado = setA.unionWith(setB);
		System.out.print("Set A: ");
		mostrarSet(setA);
		System.out.print("Set B: ");
		mostrarSet(setB);
		System.out.print("Resultado A ∪ B: ");
		mostrarSet(resultado);
		currentPhase = 0;
	}

	// Muestra la interseccion de A y B
	private void intersectLogic() {
		System.out.println("\n--- Intersección (A ∩ B) ---");
		SimpleSet<String> resultado = setA.intersectWith(setB);
		System.out.print("Set A: ");
		mostrarSet(setA);
		System.out.print("Set B: ");
		mostrarSet(setB);
		System.out.print("Resultado A ∩ B: ");
		mostrarSet(resultado);
		currentPhase = 0;
	}

	// Muestra la diferencia, permitiendo elegir direccion
	private void differenceLogic() {
		System.out.println("\n--- Diferencia ---");
		System.out.println("¿Desde qué Set desea calcular la diferencia?");
		System.out.println("1. A - B (elementos de A que no están en B)");
		System.out.println("2. B - A (elementos de B que no están en A)");

		if (scanner.hasNextInt()) {
			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1) {
				SimpleSet<String> resultado = setA.differenceWith(setB);
				System.out.print("Set A: ");
				mostrarSet(setA);
				System.out.print("Set B: ");
				mostrarSet(setB);
				System.out.print("Resultado A - B: ");
				mostrarSet(resultado);
			} else if (opcion == 2) {
				SimpleSet<String> resultado = setB.differenceWith(setA);
				System.out.print("Set A: ");
				mostrarSet(setA);
				System.out.print("Set B: ");
				mostrarSet(setB);
				System.out.print("Resultado B - A: ");
				mostrarSet(resultado);
			} else {
				System.out.println("Opción no válida.");
			}
		} else {
			System.out.println("Error: Ingrese un número.");
			scanner.nextLine();
		}
		currentPhase = 0;
	}

	// Pregunta si se quiere repetir la operacion
	private boolean preguntarRepetir() {
		while (true) {
			System.out.println("¿Desea repetir la operación? (si/no):");
			String respuesta = scanner.nextLine().trim().toLowerCase();
			if (respuesta.equals("si") || respuesta.equals("sí")) {
				return true;
			} else if (respuesta.equals("no")) {
				return false;
			}
			System.out.println("Entrada no válida. Ingrese 'si' o 'no'.");
		}
	}
}
