package core;

import java.io.Serializable;
import java.util.Random;

public class playGround implements Serializable {

	public int[][] matrix;
	private final int rows;
	private final int columns;
		
	public playGround(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		matrix = new int[rows][columns];
	}	
	
	// inicializacia pola
	public void initialize() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				matrix[i][j] = -1;
			}
		}
	}

	// vytvorenie pola
	public void createField() {		
		initialize();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				matrix[i][j] = generator();
			}
		}
	}

	// funkcia pre generovanie nahodnych cisel
	public int generator() {
		int helper = 0;
		int randomInt = 0;
		Random randomGenerator = new Random();

		while (helper == 0) {
			helper = 1;
			randomInt = randomGenerator.nextInt(rows * columns);
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					if (matrix[i][j] == randomInt) {
						helper = 0;
					}
				}
			}
		}
		return randomInt;
	}

	public int[][] getMatrix() {
		return matrix;
	}
	
	public String toString() {
		String gameField = "";
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (matrix[i][j] < 10) {
					gameField += matrix[i][j] + "  ";
				} else {
					gameField += matrix[i][j] + " ";
				}
			}
			gameField += "\n";
		}
		return gameField;
	}
}
