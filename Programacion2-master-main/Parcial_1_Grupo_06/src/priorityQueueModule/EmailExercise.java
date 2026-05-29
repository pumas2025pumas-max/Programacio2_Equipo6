package priorityQueueModule;

import java.util.Scanner;
import application.Exercise;

public class EmailExercise extends Exercise {
	private int currentPhase = 0;
	private boolean firstTime = true;
	private SimplePriorityQueue<Mail> principal;
	private SimplePriorityQueue<Mail> notificaciones;
	private SimplePriorityQueue<Mail> spam;

	public EmailExercise(Scanner scanner) {
		super(scanner);
		this.principal = new SimpleLinkedPriorityQueue<>();
		this.notificaciones = new SimpleLinkedPriorityQueue<>();
		this.spam = new SimpleLinkedPriorityQueue<>();
	}

	@Override
	protected void exerciseLogic() {
		switch (currentPhase) {
			case 1:
				redactarMail();
				break;
			case 2:
				visualizarMails();
				break;
			case 3:
				menuLogic();
				break;
			default:
				System.out.println("Volviendo al menú.");
				currentPhase = 3;
				break;
		}
	}

	private void menuLogic() {
		if (firstTime) {
			System.out.println("¡Bienvenido al Sistema de Organización de E-mail!");
			System.out.println("--------------------");
			firstTime = false;
		}

		System.out.println("\n--- ESTADO DEL SISTEMA ---");
		System.out.println("Principal: " + principal.size() + " mail(s)");
		System.out.println("Notificaciones: " + notificaciones.size() + " mail(s)");
		System.out.println("Spam: " + spam.size() + " mail(s)");

		System.out.println("\nElija una opción:");
		System.out.println("1. Redactar un mail");
		System.out.println("2. Visualizar mails de una pestaña");
		System.out.println("3. Salir al Menú Principal");

		if (scanner.hasNextInt()) {
			currentPhase = scanner.nextInt();
			scanner.nextLine();
			if (currentPhase == 3) {
				running = false;
			}
		} else {
			System.out.println("Error: Ingrese un número.");
			scanner.nextLine();
		}
	}

	// Permite al usuario redactar un mail ingresando asunto, categoria y prioridad
	private void redactarMail() {
		System.out.println("\n--- Redactar Mail ---");

		// Pedir asunto con validación (Modificado)
		String asunto = pedirAsunto();
		if (asunto == null) {
			currentPhase = 0;
			return;
		}

		// Pedir categoria con validacion
		String categoria = pedirCategoria();
		if (categoria == null) {
			currentPhase = 0;
			return;
		}

		// Pedir prioridad con validacion
		String prioridad = pedirPrioridad();
		if (prioridad == null) {
			currentPhase = 0;
			return;
		}

		// Crear el mail y agregarlo a la cola correspondiente
		Mail mail = new Mail(asunto, categoria, prioridad);
		SimplePriorityQueue<Mail> cola = obtenerCola(categoria);
		cola.enqueue(mail, mail.getPrioridadNumerica());

		System.out.println("¡Mail enviado exitosamente!");
		System.out.println("Destino: " + categoria + " | Prioridad: " + prioridad);

		if (preguntarRepetir()) {
			redactarMail();
			return;
		}
		currentPhase = 0;
	}

	// Valida que el asunto no esté vacío (Método NUEVO)
	private String pedirAsunto() {
		int intentos = 0;
		while (intentos < 3) {
			System.out.println("Ingrese el asunto del mail:");
			String input = scanner.nextLine().trim();

			if (!input.isEmpty()) {
				return input;
			}

			System.out.println("Error: El asunto no puede estar vacío.");
			intentos++;
		}
		System.out.println("Demasiados intentos inválidos. Volviendo al menú.");
		return null;
	}

	// Valida y devuelve la categoria ingresada por el usuario
	private String pedirCategoria() {
		int intentos = 0;
		while (intentos < 3) {
			System.out.println("Ingrese la categoría (Principal / Notificaciones / Spam):");
			String input = scanner.nextLine().trim();

			if (input.equalsIgnoreCase("Principal")) {
				return "Principal";
			} else if (input.equalsIgnoreCase("Notificaciones")) {
				return "Notificaciones";
			} else if (input.equalsIgnoreCase("Spam")) {
				return "Spam";
			}

			System.out.println("Categoría no válida. Las opciones son: Principal, Notificaciones, Spam.");
			intentos++;
		}
		System.out.println("Demasiados intentos inválidos. Volviendo al menú.");
		return null;
	}

	// Valida y devuelve la prioridad ingresada por el usuario
	private String pedirPrioridad() {
		int intentos = 0;
		while (intentos < 3) {
			System.out.println("Ingrese la prioridad (Alta / Baja):");
			String input = scanner.nextLine().trim();

			if (input.equalsIgnoreCase("Alta")) {
				return "Alta";
			} else if (input.equalsIgnoreCase("Baja")) {
				return "Baja";
			}

			System.out.println("Prioridad no válida. Las opciones son: Alta, Baja.");
			intentos++;
		}
		System.out.println("Demasiados intentos inválidos. Volviendo al menú.");
		return null;
	}

	// Devuelve la cola correspondiente a la categoria
	private SimplePriorityQueue<Mail> obtenerCola(String categoria) {
		if (categoria.equalsIgnoreCase("Principal")) {
			return principal;
		} else if (categoria.equalsIgnoreCase("Notificaciones")) {
			return notificaciones;
		} else {
			return spam;
		}
	}

	// Permite visualizar los mails de una pestaña, ordenados por prioridad
	private void visualizarMails() {
		System.out.println("\n--- Visualizar Mails ---");
		System.out.println("¿Qué pestaña desea ver?");
		System.out.println("1. Principal (" + principal.size() + " mails)");
		System.out.println("2. Notificaciones (" + notificaciones.size() + " mails)");
		System.out.println("3. Spam (" + spam.size() + " mails)");

		if (!scanner.hasNextInt()) {
			System.out.println("Error: Ingrese un número.");
			scanner.nextLine();
			currentPhase = 0;
			return;
		}

		int opcion = scanner.nextInt();
		scanner.nextLine();

		String nombrePestana;
		SimplePriorityQueue<Mail> cola;

		switch (opcion) {
			case 1:
				nombrePestana = "Principal";
				cola = principal;
				break;
			case 2:
				nombrePestana = "Notificaciones";
				cola = notificaciones;
				break;
			case 3:
				nombrePestana = "Spam";
				cola = spam;
				break;
			default:
				System.out.println("Opción no válida.");
				currentPhase = 0;
				return;
		}

		if (cola.isEmpty()) {
			System.out.println("La pestaña " + nombrePestana + " no tiene mails.");
			currentPhase = 0;
			return;
		}

		// Mostrar mails en orden de prioridad (sacando y reinsertando)
		System.out.println("\n--- Mails en " + nombrePestana + " (ordenados por prioridad) ---");

		// Sacar todos los mails para mostrarlos
		SimplePriorityQueue<Mail> temporal = new SimpleLinkedPriorityQueue<>();
		int contador = 1;
		while (!cola.isEmpty()) {
			Mail mail = cola.dequeue();
			System.out.println(contador + ". " + mail.toString());
			temporal.enqueue(mail, mail.getPrioridadNumerica());
			contador++;
		}

		// Preguntar si quiere marcar alguno como resuelto
		System.out.println("\n¿Desea marcar el mail de mayor prioridad como leído? (si/no):");
		String respuesta = scanner.nextLine().trim().toLowerCase();

		if (respuesta.equals("si") || respuesta.equals("sí")) {
			Mail leido = temporal.dequeue();
			System.out.println("Mail leído: " + leido.toString());
		}

		// Devolver los mails restantes a la cola original
		while (!temporal.isEmpty()) {
			Mail mail = temporal.dequeue();
			cola.enqueue(mail, mail.getPrioridadNumerica());
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