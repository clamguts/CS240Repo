package hangman;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.util.Set;
import java.util.*;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.List;
import java.util.Map.Entry;

public class EvilHangmanGame implements IEvilHangmanGame {

        private SortedSet<Character> guessedLetters;
        private Set<String> dictionarySet;
        private boolean gameStillGoing;
        private Map<String, Set<String>> partionDictionary;

        public void setGuessedLetters(SortedSet<Character> guessedLetters) {
                this.guessedLetters = guessedLetters;
        }

        public Set<String> getDictionarySet() {
                return dictionarySet;
        }

        public void setDictionarySet(Set<String> dictionarySet) {
                this.dictionarySet = dictionarySet;
        }

        public boolean isGameStillGoing() {
                return gameStillGoing;
        }

        public void setGameStillGoing(boolean gameStillGoing) {
                this.gameStillGoing = gameStillGoing;
        }

        public Map<String, Set<String>> getPartionDictionary() {
                return partionDictionary;
        }

        public void setPartionDictionary(Map<String, Set<String>> partionDictionary) {
                this.partionDictionary = partionDictionary;
        }


        public EvilHangmanGame() {
                this. gameStillGoing = true;
                this.dictionarySet = new TreeSet<>();
                this.guessedLetters = new TreeSet<>();
                this.partionDictionary = new TreeMap<>();
        }

        @Override
        public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
            if (!dictionarySet.isEmpty()) {
                dictionarySet.clear();
            }
            Scanner scan = new Scanner(dictionary);
            if (!scan.hasNext()) {
                    EmptyDictionaryException empty = new EmptyDictionaryException();
                    throw empty;
            }
            while (scan.hasNext()) {
                    String next = scan.next();
                    String lowerNext = next.toLowerCase();
                    if (lowerNext.length() == wordLength) {
                            dictionarySet.add(lowerNext);
                    }
            }
            if (dictionarySet.isEmpty()) {
                    EmptyDictionaryException stillEmpty = new EmptyDictionaryException();
                    throw stillEmpty;
            }
        }

        @Override
        public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
            Set<String> guessSet = new TreeSet<>();
            List<Character> toRemove = new ArrayList<>();
            Character guessedLetter = guess;
            guessedLetter = guessedLetter.toLowerCase(guess);
            for (Character c : guessedLetters){
                if (c == guessedLetter) {
                    GuessAlreadyMadeException sameGuess = new GuessAlreadyMadeException("You already guessed that. Try again.");
                    throw sameGuess;
                }
            }
            guessedLetters.add(guessedLetter);
            partionDictionary = partition(guessedLetter);
            int maxSize = 0;
            for (Set<String> s : partionDictionary.values()) {
                 if (s.size() > maxSize) {
                     maxSize = s.size();
                     guessSet = s;
                 }
            }
            dictionarySet = guessSet;
            return guessSet;
        }


        private String generateKey(String patternString, char guess) {
            StringBuilder subsetKey = new StringBuilder();
            subsetKey.append(patternString);
            for (int i = 0; i < patternString.length(); ++i) {
                if (patternString.charAt(i) != guess) {
                    if (i != (patternString.length() - 1)) {
                        subsetKey.replace(i, i+1, "_");
                    }
                    else {
                        subsetKey.deleteCharAt(i);
                        subsetKey.append("_");
                    }
                }
            }
            return subsetKey.toString();
        }

        public Map<String, Set<String>> partition(char guess) {
                Map<String, Set<String>> partitionMap = new TreeMap<>();
                Set<String> subSetKeys = new TreeSet<>();
                StringBuilder guessString = new StringBuilder();
                guessString.append(guess);
                Set<Integer> indexes = new TreeSet<>();
                for (String s : dictionarySet) {
                    String subsetKey = generateKey(s, guess);
                    if (!partitionMap.containsKey(subsetKey)) {
                        Set<String> subSet = new TreeSet<>();
                        subSet.add(s);
                        partitionMap.put(subsetKey,subSet);
                    }
                    else {
                        partitionMap.get(subsetKey).add(s);
                    }
                }
                return partitionMap;
        }

        @Override
        public SortedSet<Character> getGuessedLetters() {
            return guessedLetters;
        }

}
