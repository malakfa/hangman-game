import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

//this class represents a Hangman game ,the game consists of a word to guess , number of guesses and a list of chosen letters
public class Game {
	private String aWordToGuess;
	private int guessesCounter;
	private String chosenLetters;

	// Game constructor
	public Game() {
		aWordToGuess= readWord();
		guessesCounter = 0;
		chosenLetters = "";
	}

	// get a word to guess
	public String getAWordToGuess() {
		return aWordToGuess;
	}

	// get guesses counter
	public int getCount(){
		return guessesCounter;
	}

	// set guesses counter - after guessing a wrong letter we add 1 to the counter
	public void setCount(int num){
		guessesCounter = num;
	}

	// set letters list - after guessing a new letter , we add it to the list
	public void setLetters(String st){
		chosenLetters = st;
	}

	// get chosen letters list
	public String getLetters() {
		return chosenLetters;
	}

	// reading random word from the file 
	private String readWord(){
		String[] words = readFile();
		Random rnd = new Random();
		String word = words[rnd.nextInt(words.length)];
		return word ;
	}


	//this function returns array that contain random words from myFile.txt
	private String[] readFile(){
		FileReader fr;
		Random rnd = new Random();
		int linesNum = numOfLines();
		int randLine = rnd.nextInt(linesNum) + 1;
		String line = "";
		String[] arrOfStr = null;
		try {
			fr = new FileReader("myFile.txt");// check
			BufferedReader br = new BufferedReader(fr);
			while(randLine != 0) {
				line = br.readLine();
				randLine--;
			}
			arrOfStr = line.split(" ", 15);
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrOfStr;
		
	}
	
	// this function returns the number of lines in file MyFile.txt
	private int numOfLines() {
		int count = 0;
		try {
			Scanner in = new Scanner(new File("myFile.txt"));// check
			while(in.hasNext())
			{
				in.nextLine();
				count ++;
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	// 
	public void  roundManage( Text match ,Text guessedList, char c , GraphicsContext gc) {
		int len = getAWordToGuess().length();
		boolean flag = containC(c);

		addLetter(guessedList,c);
		if(flag == false) {//The word does not contain the letter c
			setCount(getCount() + 1 );
			showCurrentGal(gc);
			if(getCount() == 7) {
				gameOver( guessedList , false);
			}

		}
		else {
			match.setText(placeLetter(len , c , match ));
			if(win((match.getText()).toCharArray()) == true)
				gameOver( guessedList , true);
		}
	}
	
	// this function returns true if the user won
	private boolean win(char[] guessed) {
		for(int i = 0; i < guessed.length ; i++ ) {
			if(guessed[i] == '_')
				return false;
		}
		return true;
		
	}
	// show the current Gallows according to count(The number of fouls)
	private void showCurrentGal( GraphicsContext gc) {
		int count = getCount();
		if(count == 1) {
			showGallows(gc);
		}
		else {
			addAlimb(gc);
		}
	}
	private void showGallows(GraphicsContext gc){
		gc.strokeLine(25, 25, 200, 25);
		gc.strokeLine(25, 25, 25, 300);
		gc.strokeLine(300, 300, 25, 300);
		gc.strokeLine(200, 25, 200, 75);
	}

	// adding a limb to the Hangman
	private void addAlimb( GraphicsContext gc){
		int count = getCount();
		if(count == 2)
			gc.strokeOval(185, 80, 35, 35);
		if(count == 3)
			gc.strokeLine(202.5, 115, 202.5, 180);
		if(count == 4)
			gc.strokeLine(202.5, 135 , 220, 175);
		if(count == 5)
			gc.strokeLine(202.5, 135 , 175, 175);
		if(count == 6)
			gc.strokeLine(202.5, 180 , 220, 240);
		if(count == 7) {//Game over
			gc.strokeLine(202.5, 180 , 175, 240);
		}
	}

	// this function adds the guessed letter to guessedList
	private void addLetter(Text guessedList, char c) {
		if(getLetters() == "") {//first guess
			setLetters(getLetters() + " "  + c);
		}
		else {
			setLetters(getLetters() + " , " + c);    		
		}
		guessedList.setText("chosen letters list :" + getLetters());
	}

	// this function returns true if the word contain the letter c , else it returns false
	private  boolean containC( char c) {
		int len = getAWordToGuess().length();
		boolean flag = false;

		for(int i = 0; i < len ; i++ ) {
			if(c == getAWordToGuess().charAt(i)) {
				flag = true;
			}

		}
		return flag;
	}

	//Place the letter in appropriate place
	private String placeLetter(int len , char c , Text match) {
		char[] guessed = (match.getText()).toCharArray();

		for(int i = 0; i < len ; i++ ) {
			if(c == getAWordToGuess().charAt(i)) {
				guessed[2*i] = c;
			}
		}
		return String.valueOf(guessed);
	}
	
	// game over - show messege to the user according his state
	private void gameOver(Text guessedList , boolean won) {
		
		if(won == true) {
			guessedList.setText("Game Over , CONGRATS ! YOU WIN");
			guessedList.setFill(Color.GREEN);
			
		}
		else {
			guessedList.setText("Game Over , you lose :(");
			guessedList.setFill(Color.RED);
			
		}
		
	}
	



}
