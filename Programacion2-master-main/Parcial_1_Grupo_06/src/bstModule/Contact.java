package bstModule;

public class Contact {
	private String nombre;
	private String numero;
	private String mail;

	public Contact(String nombre, String numero, String mail) {
		this.nombre = nombre;
		this.numero = numero;
		this.mail = mail;
	}

	public String getNombre() {
		return nombre;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return nombre + " | Tel: " + numero + " | Mail: " + mail;
	}

	public void setNombre(String nuevoNombre) {
		this.nombre = nuevoNombre;
		
	}
}
