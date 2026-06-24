package dictionaryModule;

import java.util.Scanner;
import application.Exercise;

public class LoginExercise extends Exercise {
	private int currentPhase = 0;
	private boolean firstTime = true;
	private SimpleDictionary<String, String> usuarios;    // username -> password
	private SimpleDictionary<String, Integer> intentos;   // username -> intentos fallidos
	private SimpleDictionary<String, Boolean> bloqueados;  // username -> esta bloqueado?

	public LoginExercise(Scanner scanner) {
		super(scanner);
		this.usuarios = new SimpleLinkedDictionary<>();
		this.intentos = new SimpleLinkedDictionary<>();
		this.bloqueados = new SimpleLinkedDictionary<>();
	}

	@Override
	protected void exerciseLogic() {
		switch (currentPhase) {
			case 1:
				registrarLogic();
				break;
			case 2:
				ingresarLogic();
				break;
			case 3:
				menuLogic();
				break;
			default:
				System.out.println("Opción no válida, volviendo al menú.");
				currentPhase = 3;
				break;
		}
	}

	private void menuLogic() {
		if (firstTime) {
			System.out.println("¡Bienvenido al Sistema de Login!");
			System.out.println("--------------------");
			firstTime = false;
		}

		System.out.println("\n--- Sistema de Login ---");
		System.out.println("Usuarios registrados: " + usuarios.size());

		System.out.println("\nElija una opción:");
		System.out.println("1. Registrar usuario");
		System.out.println("2. Ingresar (login)");
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

	// Registrar un nuevo usuario
	private void registrarLogic() {
		System.out.println("\n--- Registrar Usuario ---");

		// Pedir nombre de usuario
		String username = pedirTexto("Ingrese el nombre de usuario:");
		if (username == null) {
			currentPhase = 0;
			return;
		}

		// Verificar que no exista
		if (usuarios.containsKey(username)) {
			System.out.println("Error: El usuario '" + username + "' ya existe.");
			currentPhase = 0;
			return;
		}

		// Pedir contraseña
		String password = pedirTexto("Ingrese la contraseña:");
		if (password == null) {
			currentPhase = 0;
			return;
		}

		// Registrar
		usuarios.put(username, password);
		intentos.put(username, 0);
		bloqueados.put(username, false);

		System.out.println("¡Usuario '" + username + "' registrado exitosamente!");

		if (preguntarRepetir()) {
			registrarLogic();
			return;
		}
		currentPhase = 0;
	}

	// Ingresar con un usuario existente
	private void ingresarLogic() {
		System.out.println("\n--- Ingresar ---");

		// Pedir nombre de usuario
		String username = pedirTexto("Ingrese su nombre de usuario:");
		if (username == null) {
			currentPhase = 0;
			return;
		}

		// Verificar que exista
		if (!usuarios.containsKey(username)) {
			System.out.println("Error: El usuario '" + username + "' no existe.");
			currentPhase = 0;
			return;
		}

		// Verificar si esta bloqueado
		Boolean estaBloqueado = bloqueados.get(username);
		if (estaBloqueado != null && estaBloqueado) {
			System.out.println("Error: La cuenta '" + username + "' está bloqueada por demasiados intentos fallidos.");
			currentPhase = 0;
			return;
		}

		// Pedir contraseña
		System.out.println("Ingrese su contraseña:");
		String password = scanner.nextLine().trim();

		// Verificar contraseña
		String passwordCorrecta = usuarios.get(username);
		if (password.equals(passwordCorrecta)) {
			System.out.println("¡Bienvenido, " + username + "! Ingreso exitoso.");
			intentos.put(username, 0); // Reiniciar intentos al ingresar correctamente
		} else {
			// Incrementar intentos fallidos
			Integer intentosFallidos = intentos.get(username);
			if (intentosFallidos == null) {
				intentosFallidos = 0;
			}
			intentosFallidos++;
			intentos.put(username, intentosFallidos);

			if (intentosFallidos >= 3) {
				bloqueados.put(username, true);
				System.out.println("Contraseña incorrecta. Has alcanzado 3 intentos fallidos.");
				System.out.println("¡La cuenta '" + username + "' ha sido BLOQUEADA!");
			} else {
				System.out.println("Contraseña incorrecta. Intento " + intentosFallidos + " de 3.");
			}
		}

		currentPhase = 0;
	}

	// Pide un texto al usuario, validando que no este vacio
	private String pedirTexto(String mensaje) {
		int maxIntentos = 3;
		int intento = 0;
		while (intento < maxIntentos) {
			System.out.println(mensaje);
			String input = scanner.nextLine().trim();
			if (!input.isEmpty()) {
				return input;
			}
			System.out.println("El campo no puede estar vacío.");
			intento++;
		}
		System.out.println("Demasiados intentos inválidos. Volviendo al menú.");
		return null;
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
