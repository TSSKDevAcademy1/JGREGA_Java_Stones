package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class TextFileGameLoader {
	private static final String REGISTER_FILE = "file.txt";
	private int[][] matrix;

	// nacitanie ulozenej hry
	public int[][] load() {
		File file = new File(REGISTER_FILE);
		if (!file.exists()) {
			System.out.println("File no exist!!");
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

			String value;
			int rows = Integer.parseInt(reader.readLine());
			int columns = Integer.parseInt(reader.readLine());
			matrix = new int[rows][columns];
			while (true) {
				for (int row = 0; row < rows; row++) {
					for (int column = 0; column < columns; column++) {
						value = reader.readLine();
						matrix[row][column] = Integer.parseInt(value);
					}
				}
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return matrix;
	}

	// ulozenie hry pri jej ukonceni (exit)
	public void save(int[][] matrix2) {

		try (PrintWriter pw = new PrintWriter(REGISTER_FILE)) {

			pw.println(matrix2.length);
			pw.println(matrix2[0].length);
			for (int row = 0; row < matrix2.length; row++) {
				for (int column = 0; column < matrix2[0].length; column++) {
					int p = matrix2[row][column];
					pw.println(p);
				}
			}
		} catch (Exception e) {
			System.out.printf("File no exist");
		}
	}
}
