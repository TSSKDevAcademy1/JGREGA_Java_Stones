package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

public class Console implements Serializable {

	private int rows;
	private int columns;
	public int[][] matrix2;
	private int nullRow = 0;
	private int nullColumns = 0;
	private long CurrentTime = 0;
	boolean loading = false;

	TextFileGameLoader loader = new TextFileGameLoader();
	
	/**
	 * Menu options.
	 */
	private enum Option {
		START_NEW_GAME, START_PREVIOUS_GAME, EXIT
	};

	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	// Menu
	public void run() throws Exception {

		switch (showMenu()) {
		case START_NEW_GAME:
			startGame(loading);
			break;
		case START_PREVIOUS_GAME:			
			startGame(true);
			break;
		case EXIT:
			System.exit(0);
			break;
		}
	}

	private String readLine() {

		try {
			return input.readLine();
		} catch (IOException e) {
			return null;
		}
	}

	private Option showMenu() {

		System.out.println("Menu.");
		for (Option option : Option.values()) {
			System.out.printf("%d. %s%n", option.ordinal() + 1, option);
		}
		System.out.println("------------------------------------------------");

		int selection = -1;
		do {
			System.out.println("Option: ");
			selection = Integer.parseInt(readLine());
			if (selection <= 0 || selection > Option.values().length)
				System.out.println("Existent choice!! (Enter right choice)");
		} while (selection <= 0 || selection > Option.values().length);

		return Option.values()[selection - 1];
	}

	// Nacitanie velkosti hracieho pola a pociatocneho pola
	private void startGame(boolean loading) {
		if (loading) {
			matrix2 = loader.load();
			rows = matrix2.length;
			columns = matrix2[0].length;
			printer();
		} else {
			System.out.println("Enter value of rows:");
			rows = Integer.parseInt(readLine());
			System.out.println("Enter value of columns");
			columns = Integer.parseInt(readLine());
			playGround field = new playGround(rows, columns);
			field.createField();
			matrix2 = field.getMatrix();
			System.out.format("%1s", field.toString());
		}
		game();
	}

	// Vyber z moznosti pohybov a exit
	private void game() {
		CurrentTime = System.currentTimeMillis();
		String key = "";
		while (true) {
			findZero();
			System.out.println("W(up) S(down) A(left) D(right) E(EXIT) NEW(New Game)");
			key = readLine().toUpperCase();
			switch (key) {
			case "W": up(); break;
			case "UP": up(); break;
				
			case "S": down(); break;
			case "DOWN": down(); break;
								
			case "A": left(); break;
			case "LEFT": left(); break;	
				
			case "D": right(); break;
			case "RIGHT": right(); break;	
				
			case "E": System.out.println("Good bye :)"); time() ; loader.save(matrix2); System.exit(0); break;							
			case "EXIT": System.out.println("Good bye :)"); time();	System.exit(0); break;
			
			case "NEW" : startGame(false); break;
						
				
			default:
				System.out.println("Wrong input!!");				
			}	
			time();
			Solved();			
		}
	}

	public int[][] getMatrix2() {
		return matrix2;
	}

	public void setMatrix2(String value,int row, int column) {		
		matrix2[row][column] = Integer.parseInt(value);
	}

	// Posun do lava
	private void left() {
		if (nullColumns < columns - 1 && nullColumns != columns) {
			move(0,1);
			printer();
		}else{
			warming();
		}
	}

	// Posun do prava
	private void right() {	
		if (nullColumns <= columns - 1 && nullColumns != 0) { 
			move(0,-1);
			printer();
		}else{
			warming();
		}		
	}

	// Posun hore
	private void up() {
		if (nullRow < rows - 1 && nullRow != rows) {
			move(1,0);
			printer();
		}else{
			warming();
		}
		
	}

	// Posun dole
	private void down() {
		if (nullRow < rows && nullRow != 0) {
			move(-1,0);
			printer();
		}else{
			warming();
		}
	}
	
	// Vypis a formatovanie casu
	private void time() {
		if ((((System.currentTimeMillis() - CurrentTime) / 1000) / 60) >= 10
				&& (((System.currentTimeMillis() - CurrentTime) / 1000) % 60) < 10) {
			System.out.println("Your time is: " + ((System.currentTimeMillis() - CurrentTime) / 1000) / 60 + ":0"
					+ ((System.currentTimeMillis() - CurrentTime) / 1000) % 60);
		} else if ((((System.currentTimeMillis() - CurrentTime) / 1000) / 60) > 10
				&& (((System.currentTimeMillis() - CurrentTime) / 1000) % 60) >= 10) {
			System.out.println("Your time is: " + ((System.currentTimeMillis() - CurrentTime) / 1000) / 60 + ":"
					+ ((System.currentTimeMillis() - CurrentTime) / 1000) % 60);
		} else if ((((System.currentTimeMillis() - CurrentTime) / 1000) / 60) < 10
				&& (((System.currentTimeMillis() - CurrentTime) / 1000) % 60) < 10) {
			System.out.println("Your time is: 0" + ((System.currentTimeMillis() - CurrentTime) / 1000) / 60 + ":0"
					+ ((System.currentTimeMillis() - CurrentTime) / 1000) % 60);
		} else {
			System.out.println("Your time is: 0" + ((System.currentTimeMillis() - CurrentTime) / 1000) / 60 + ":"
					+ ((System.currentTimeMillis() - CurrentTime) / 1000) % 60);
		}
	}
	
	private void warming(){
		printer();
		System.out.println("Impossible move!!!");
	}
	
	// Posun vybraneho cisla 
	private void move(int rowEdit,int columnEdit){
		matrix2[nullRow][nullColumns] = matrix2[nullRow + rowEdit][nullColumns + columnEdit];
		matrix2[nullRow + rowEdit][nullColumns + columnEdit] = 0;
	}
	
	// Najdenie nuly v hracom poli
	private void findZero(){
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (matrix2[i][j] == 0) {
					nullRow = i;
					nullColumns = j;
				}
			}
		}
	}

	// Vykreslenie hracieho pola
	public void printer() {
		String gameField = "";		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (matrix2[i][j] < 10) {
					gameField += matrix2[i][j] + "  ";
				} else {
					gameField += matrix2[i][j] + " ";
				}
			}
			gameField += "\n";
		}
		System.out.println(gameField);
	}

	// Kontrola usporiadania kamenov.. 
	private void Solved() {
		int count = 0;
		int count2 = 0;

		if (matrix2[0][0] == 1) {
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					count++;
					if (matrix2[i][j] == count) {
						count2++;
					}
				}
			}
		}
		if (count2 == (rows * columns) - 1) {
			 System.out.println("You are Winner!!!");
			 time();
			 System.exit(0);			
		}
	}

}
