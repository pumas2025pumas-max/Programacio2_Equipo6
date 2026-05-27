package priorityQueueModule;

public class Mail {
	private String asunto;
	private String categoria; // "Principal", "Notificaciones", "Spam"
	private String prioridad; // "Alto", "Bajo"

	public Mail(String asunto, String categoria, String prioridad) {
		this.asunto = asunto;
		this.categoria = categoria;
		this.prioridad = prioridad;
	}

	public String getAsunto() {
		return asunto;
	}

	public String getCategoria() {
		return categoria;
	}

	public String getPrioridad() {
		return prioridad;
	}

	// Devuelve el valor numerico de la prioridad para la cola
	public int getPrioridadNumerica() {
		if (prioridad.equalsIgnoreCase("Alto")) {
			return 2;
		}
		return 1; // "Bajo"
	}

	@Override
	public String toString() {
		return "[" + prioridad.toUpperCase() + "] " + asunto + " (" + categoria + ")";
	}
}
