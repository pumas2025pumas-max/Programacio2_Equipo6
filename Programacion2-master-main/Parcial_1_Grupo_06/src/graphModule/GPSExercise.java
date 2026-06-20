package graphModule;

import java.util.Scanner;
import application.Exercise;
import listModule.SimpleList;

public class GPSExercise extends Exercise {
	private int currentPhase = 0;
	private boolean firstTime = true;
	private SimpleAdjacencyListGraph<String> mapa;

	public GPSExercise(Scanner scanner) {
		super(scanner);
		this.mapa = new SimpleAdjacencyListGraph<>();
		cargarMapaInicial();
	}

	// Carga el mapa de ciudades argentinas al iniciar
	private void cargarMapaInicial() {
		// Rutas bidireccionales (se agregan en ambas direcciones)
		agregarRutaBidireccional("Buenos Aires", "Rosario", 300);
		agregarRutaBidireccional("Buenos Aires", "La Plata", 60);
		agregarRutaBidireccional("Rosario", "Córdoba", 400);
		agregarRutaBidireccional("Rosario", "Santa Fe", 170);
		agregarRutaBidireccional("Córdoba", "Mendoza", 650);
		agregarRutaBidireccional("Córdoba", "Tucumán", 530);
		agregarRutaBidireccional("Santa Fe", "Paraná", 30);
		agregarRutaBidireccional("La Plata", "Mar del Plata", 360);
		agregarRutaBidireccional("Mendoza", "San Juan", 170);
	}

	// Agrega una ruta en ambas direcciones
	private void agregarRutaBidireccional(String from, String to, int weight) {
		mapa.addEdge(from, to, weight);
		mapa.addEdge(to, from, weight);
	}

	@Override
	protected void exerciseLogic() {
		switch (currentPhase) {
			case 1:
				mostrarMapa();
				break;
			case 2:
				calcularCamino();
				break;
			case 3:
				agregarCiudad();
				break;
			case 4:
				agregarRuta();
				break;
			case 5:
				menuLogic();
				break;
			default:
				System.out.println("Opción no válida, volviendo al menú.");
				currentPhase = 5;
				break;
		}
	}

	private void menuLogic() {
		if (firstTime) {
			System.out.println("¡Bienvenido al GPS - Camino más corto!");
			System.out.println("--------------------");
			firstTime = false;
		}

		System.out.println("\n--- GPS ---");
		System.out.println("Ciudades cargadas: " + mapa.size());

		System.out.println("\nElija una opción:");
		System.out.println("1. Mostrar mapa (todas las rutas)");
		System.out.println("2. Calcular camino más corto");
		System.out.println("3. Agregar ciudad");
		System.out.println("4. Agregar ruta");
		System.out.println("5. Salir al Menú Principal");

		if (scanner.hasNextInt()) {
			currentPhase = scanner.nextInt();
			scanner.nextLine();
			if (currentPhase == 5) {
				running = false;
			}
		} else {
			System.out.println("Error: Ingrese un número.");
			scanner.nextLine();
		}
	}

	// Muestra todas las rutas del mapa
	private void mostrarMapa() {
		System.out.println("\n--- Mapa de Rutas ---");

		if (mapa.size() == 0) {
			System.out.println("El mapa está vacío.");
			currentPhase = 0;
			return;
		}

		mapa.printGraph();
		System.out.println("\nTotal: " + mapa.size() + " ciudad(es).");

		currentPhase = 0;
	}

	// Calcula el camino más corto entre dos ciudades usando Dijkstra
	private void calcularCamino() {
		System.out.println("\n--- Calcular Camino Más Corto ---");

		// Mostrar ciudades disponibles
		System.out.println("Ciudades disponibles:");
		SimpleList<String> ciudades = mapa.vertices();
		for (int i = 0; i < ciudades.size(); i++) {
			System.out.println("  - " + ciudades.get(i));
		}

		// Pedir origen
		String origen = pedirCiudad("Ingrese la ciudad de origen:");
		if (origen == null) {
			currentPhase = 0;
			return;
		}

		if (!mapa.containsVertex(origen)) {
			System.out.println("Error: La ciudad '" + origen + "' no existe en el mapa.");
			currentPhase = 0;
			return;
		}

		// Pedir destino
		String destino = pedirCiudad("Ingrese la ciudad de destino:");
		if (destino == null) {
			currentPhase = 0;
			return;
		}

		if (!mapa.containsVertex(destino)) {
			System.out.println("Error: La ciudad '" + destino + "' no existe en el mapa.");
			currentPhase = 0;
			return;
		}

		if (origen.equals(destino)) {
			System.out.println("El origen y el destino son la misma ciudad. Distancia: 0 km.");
			currentPhase = 0;
			return;
		}

		// Ejecutar Dijkstra
		DijkstraResult<String> resultado = Dijkstra.shortestPath(mapa, origen, destino);

		if (!resultado.hasPath() || resultado.getTotalDistance() < 0) {
			System.out.println("No se encontró un camino entre " + origen + " y " + destino + ".");
		} else {
			System.out.println("\n--- Resultado ---");
			System.out.println("Camino más corto de " + origen + " a " + destino + ":");

			SimpleList<String> camino = resultado.getPath();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < camino.size(); i++) {
				sb.append(camino.get(i));
				if (i < camino.size() - 1) {
					sb.append(" → ");
				}
			}
			System.out.println(sb.toString());
			System.out.println("Distancia total: " + resultado.getTotalDistance() + " km");
		}

		currentPhase = 0;
	}

	// Agregar una nueva ciudad al mapa
	private void agregarCiudad() {
		System.out.println("\n--- Agregar Ciudad ---");

		String ciudad = pedirTexto("Ingrese el nombre de la nueva ciudad:");
		if (ciudad == null) {
			currentPhase = 0;
			return;
		}

		if (mapa.containsVertex(ciudad)) {
			System.out.println("La ciudad '" + ciudad + "' ya existe en el mapa.");
		} else {
			mapa.addVertex(ciudad);
			System.out.println("¡Ciudad '" + ciudad + "' agregada exitosamente!");
		}

		currentPhase = 0;
	}

	// Agregar una nueva ruta entre dos ciudades
	private void agregarRuta() {
		System.out.println("\n--- Agregar Ruta ---");

		// Mostrar ciudades disponibles
		System.out.println("Ciudades disponibles:");
		SimpleList<String> ciudades = mapa.vertices();
		for (int i = 0; i < ciudades.size(); i++) {
			System.out.println("  - " + ciudades.get(i));
		}

		String desde = pedirCiudad("Ingrese la ciudad de origen:");
		if (desde == null) {
			currentPhase = 0;
			return;
		}

		String hacia = pedirCiudad("Ingrese la ciudad de destino:");
		if (hacia == null) {
			currentPhase = 0;
			return;
		}

		if (desde.equals(hacia)) {
			System.out.println("Error: La ciudad de origen y destino no pueden ser la misma.");
			currentPhase = 0;
			return;
		}

		System.out.println("Ingrese la distancia en km:");
		if (!scanner.hasNextInt()) {
			System.out.println("Error: Ingrese un número válido.");
			scanner.nextLine();
			currentPhase = 0;
			return;
		}

		int distancia = scanner.nextInt();
		scanner.nextLine();

		if (distancia <= 0) {
			System.out.println("Error: La distancia debe ser mayor a 0.");
			currentPhase = 0;
			return;
		}

		// Agregar ruta bidireccional
		agregarRutaBidireccional(desde, hacia, distancia);
		System.out.println("¡Ruta agregada: " + desde + " ↔ " + hacia + " (" + distancia + " km)!");

		currentPhase = 0;
	}

	// Pide el nombre de una ciudad (con validacion de vacio)
	private String pedirCiudad(String mensaje) {
		return pedirTexto(mensaje);
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
}
