package bstModule;

import java.util.Scanner;
import application.Exercise;

public class ContactsExercise extends Exercise {
	private int currentPhase = 0;
	private boolean firstTime = true;
	private SimpleBST<String, Contact> contactos;

	public ContactsExercise(Scanner scanner) {
		super(scanner);
		this.contactos = new LinkedAVL<>();
	}

	@Override
	protected void exerciseLogic() {
		switch (currentPhase) {
			case 1:
				agregarContacto();
				break;
			case 2:
				buscarContacto();
				break;
			case 3:
				editarContacto();
				break;
			case 4:
				borrarContacto();
				break;
			case 5:
				mostrarTodos();
				break;
			case 6:
				cargarDatosPrueba();
				break;
			case 7:
				menuLogic();
				break;
			default:
				System.out.println("Volviendo al menú.");
				currentPhase = 7;
				break;
		}
	}

	private void menuLogic() {
		if (firstTime) {
			System.out.println("¡Bienvenido a la Aplicación de Contactos!");
			System.out.println("--------------------");
			firstTime = false;
		}

		System.out.println("\n--- Aplicación de Contactos ---");
		System.out.println("Contactos registrados: " + contactos.size());

		System.out.println("\nElija una opción:");
		System.out.println("1. Agregar contacto");
		System.out.println("2. Buscar contacto");
		System.out.println("3. Editar contacto");
		System.out.println("4. Borrar contacto");
		System.out.println("5. Mostrar todos los contactos");
		System.out.println("6. Cargar datos de prueba");
		System.out.println("7. Salir al Menú Principal");

		if (scanner.hasNextInt()) {
			currentPhase = scanner.nextInt();
			scanner.nextLine();
			if (currentPhase == 7) {
				running = false;
			}
		} else {
			System.out.println("Error: Ingrese un número.");
			scanner.nextLine();
		}
	}

	// Agregar un nuevo contacto
	private void agregarContacto() {
		System.out.println("\n--- Agregar Contacto ---");

		String nombre = pedirTexto("Ingrese el nombre del contacto:");
		if (nombre == null) {
			currentPhase = 0;
			return;
		}

		// Verificar que no exista
		if (contactos.containsKey(nombre)) {
			System.out.println("Error: Ya existe un contacto con el nombre '" + nombre + "'.");
			currentPhase = 0;
			return;
		}

		String numero = pedirTexto("Ingrese el número de teléfono:");
		if (numero == null) {
			currentPhase = 0;
			return;
		}

		String mail = pedirTexto("Ingrese el mail:");
		if (mail == null) {
			currentPhase = 0;
			return;
		}

		Contact contacto = new Contact(nombre, numero, mail);
		contactos.put(nombre, contacto);
		System.out.println("¡Contacto agregado exitosamente!");
		System.out.println(contacto.toString());

		if (preguntarRepetir()) {
			agregarContacto();
			return;
		}
		currentPhase = 0;
	}

	// Buscar un contacto por nombre
	private void buscarContacto() {
		System.out.println("\n--- Buscar Contacto ---");

		if (contactos.isEmpty()) {
			System.out.println("No hay contactos registrados.");
			currentPhase = 0;
			return;
		}

		String nombre = pedirTexto("Ingrese el nombre del contacto a buscar:");
		if (nombre == null) {
			currentPhase = 0;
			return;
		}

		Contact contacto = contactos.get(nombre);
		if (contacto == null) {
			System.out.println("No se encontró un contacto con el nombre '" + nombre + "'.");
		} else {
			System.out.println("Contacto encontrado:");
			System.out.println(contacto.toString());
		}

		currentPhase = 0;
	}

	// Editar un contacto existente
		private void editarContacto() {
			System.out.println("\n--- Editar Contacto ---");

			if (contactos.isEmpty()) {
				System.out.println("No hay contactos registrados.");
				currentPhase = 0;
				return;
			}

			String nombre = pedirTexto("Ingrese el nombre del contacto a editar:");
			if (nombre == null) {
				currentPhase = 0;
				return;
			}

			Contact contacto = contactos.get(nombre);
			if (contacto == null) {
				System.out.println("No se encontró un contacto con el nombre '" + nombre + "'.");
				currentPhase = 0;
				return;
			}

			System.out.println("Contacto actual: " + contacto.toString());
			System.out.println("\n¿Qué desea editar?");
			System.out.println("1.Nombre");
			System.out.println("2 Número de teléfono");
			System.out.println("3. Mail");
			System.out.println("4. Todos los datos");

			if (!scanner.hasNextInt()) {
				System.out.println("Error: Ingrese un número.");
				scanner.nextLine();
				currentPhase = 0;
				return;
			}

			int opcion = scanner.nextInt();
			scanner.nextLine();

			if (opcion == 1 || opcion == 4) {
				String nuevoNombre = pedirTexto("Ingrese el nuevo nombre:");
				if (nuevoNombre != null) {
					if (!nuevoNombre.equals(nombre) && contactos.containsKey(nuevoNombre)) {
						System.out.println("Error: Ya existe un contacto con el nombre '" + nuevoNombre + "'.");
					} else if (!nuevoNombre.equals(nombre)) {
						contactos.remove(nombre);
						contacto.setNombre(nuevoNombre);
						contactos.put(nuevoNombre, contacto);
						nombre = nuevoNombre;
					}
				}
			}

			if (opcion == 2 || opcion == 4) {
				String nuevoNumero = pedirTexto("Ingrese el nuevo número:");
				if (nuevoNumero != null) {
					contacto.setNumero(nuevoNumero);
				}
			}

			if (opcion == 3 || opcion == 4) {
				String nuevoMail = pedirTexto("Ingrese el nuevo mail:");
				if (nuevoMail != null) {
					contacto.setMail(nuevoMail);
				}
			}

			if (opcion >= 1 && opcion <= 4) {
				System.out.println("¡Contacto actualizado!");
				System.out.println(contacto.toString());
			} else {
				System.out.println("Opción no válida.");
			}

			currentPhase = 0;
		}
	// Borrar un contacto
	private void borrarContacto() {
		System.out.println("\n--- Borrar Contacto ---");

		if (contactos.isEmpty()) {
			System.out.println("No hay contactos registrados.");
			currentPhase = 0;
			return;
		}

		String nombre = pedirTexto("Ingrese el nombre del contacto a borrar:");
		if (nombre == null) {
			currentPhase = 0;
			return;
		}

		Contact contacto = contactos.get(nombre);
		if (contacto == null) {
			System.out.println("No se encontró un contacto con el nombre '" + nombre + "'.");
			currentPhase = 0;
			return;
		}

		System.out.println("Contacto a borrar: " + contacto.toString());
		System.out.println("¿Está seguro? (si/no):");
		String respuesta = scanner.nextLine().trim().toLowerCase();

		if (respuesta.equals("si") || respuesta.equals("sí")) {
			contactos.remove(nombre);
			System.out.println("¡Contacto borrado exitosamente!");
		} else {
			System.out.println("Operación cancelada.");
		}

		currentPhase = 0;
	}

	// Mostrar todos los contactos ordenados por nombre
	private void mostrarTodos() {
		System.out.println("\n--- Todos los Contactos ---");

		if (contactos.isEmpty()) {
			System.out.println("No hay contactos registrados.");
			currentPhase = 0;
			return;
		}

		Object[] valores = contactos.values();
		for (int i = 0; i < valores.length; i++) {
			Contact c = (Contact) valores[i];
			System.out.println((i + 1) + ". " + c.toString());
		}

		System.out.println("\nTotal: " + contactos.size() + " contacto(s).");
		currentPhase = 0;
	}

	// Carga datos de prueba pre-definidos
	private void cargarDatosPrueba() {
		System.out.println("\n--- Cargar Datos de Prueba ---");

		String[][] datos = {
			{"Ana García", "1145678901", "ana.garcia@mail.com"},
			{"Carlos López", "1156789012", "carlos.lopez@mail.com"},
			{"Elena Martínez", "1167890123", "elena.martinez@mail.com"},
			{"Juan Pérez", "1134567890", "juan.perez@mail.com"},
			{"María Rodríguez", "1178901234", "maria.rodriguez@mail.com"}
		};

		int agregados = 0;
		for (String[] dato : datos) {
			if (!contactos.containsKey(dato[0])) {
				Contact contacto = new Contact(dato[0], dato[1], dato[2]);
				contactos.put(dato[0], contacto);
				agregados++;
			}
		}

		System.out.println("Se cargaron " + agregados + " contacto(s) de prueba.");
		if (agregados < datos.length) {
			System.out.println("(" + (datos.length - agregados) + " ya existían y no se duplicaron)");
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
