package application;

import java.util.Scanner;

// Clase Base de los ejercicios
public abstract class Exercise {

	protected boolean running = true;
	protected Scanner scanner;

	public Exercise(Scanner scanner) {
		this.scanner = scanner;
	}

	public void run() {
		running = true;
		while (running) {
			exerciseLogic();
		}

	}

	protected abstract void exerciseLogic();

}
