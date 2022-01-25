package hangman;

import java.io.File;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Set;
import java.util.Map;
import java.util.Scanner;

public class EvilHangman {

    public static void main(String[] args) {

        String fileName = args[0];
        File dictionary = new File(fileName);
        int wordLength = Integer.parseInt(args[1]);
        int numGuesses = Integer.parseInt(args[2]);
        SortedSet<Character> usedLetters = new TreeSet<>();
        Set<String> currSet = new TreeSet<>();
        Map<String, Set<String>> currMap = new TreeMap<>();
        boolean guessCorrect = false;


        EvilHangmanGame newGame = new EvilHangmanGame();

        StringBuilder currWord = new StringBuilder();
        currWord.append("_".repeat(wordLength));

        try{
            newGame.startGame(dictionary, wordLength);
        }
        catch(EmptyDictionaryException e) {
            System.out.println("Empty Dictionary");
        }
        catch (IOException f) {
            System.out.println("File not found");
        }

        while (numGuesses != 0) {
            try {
                System.out.println("You have " + numGuesses + " guesses left\nUsed Letters: ");
                usedLetters = newGame.getGuessedLetters();
                System.out.println(usedLetters);
                System.out.print("Word: " + currWord + "\n");
                System.out.println("Enter Guess: ");
                Scanner scan = new Scanner(System.in);
                String scanString = scan.next();
                char userGuess;
                if (scanString.length() > 1) {
                    throw new GuessAlreadyMadeException("Only one letter please");
                }
                else {
                  userGuess = scanString.charAt(0);
                  Character guessChar = userGuess;
                  if (guessChar.isLetter(guessChar)) {
                  }
                  else {
                      throw new GuessAlreadyMadeException("Please input only letters");
                  }
                }
                currSet = newGame.makeGuess(userGuess);
                if (!currSet.iterator().next().contains(Character.toString(userGuess))) {
                    System.out.println("Sorry, there are no " + userGuess);
                    --numGuesses;
                }
                else {
                    int numChar = 0;
                    for (int i = 0; i < currSet.iterator().next().length(); ++i) {
                        if (currSet.iterator().next().charAt(i) == userGuess) {
                            ++numChar;
                            currWord.setCharAt(i, userGuess);
                        }
                    }
                    if (!currWord.toString().contains("_")) {
                        System.out.println("You won! The word is " + currWord);
                        return;
                    }

                    System.out.println("Yes, there are " + numChar + " " + userGuess + "." );
                }
            }
            catch (GuessAlreadyMadeException g) {
                System.out.println(g.getError());
            }
            /*catch (IOException e) {
                System.out.println("Bad input");
            }*/

        }
        System.out.println("Sorry, you lost. the word was: " + currSet.iterator().next());
    }

}
