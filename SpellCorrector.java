package spell;

import spell.ISpellCorrector;
import spell.Trie;

import java.util.*;
import java.io.File;

import java.io.IOException;

public class SpellCorrector implements ISpellCorrector {

    private Trie dictionary;
    private final char[] ALPHABET = new char[] {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'
            , 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'};

    public SpellCorrector() {
        dictionary = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File dictFile = new File(dictionaryFileName);
        Scanner scan = new Scanner(dictFile);
        while (scan.hasNext()) {
            String next = (scan.next()).toLowerCase();
            dictionary.add(next);
        }
    }

    public Trie getDictionary() {
        return dictionary;
    }

    private void deletionDistance(String input, Set<String> deleteDist) {
        StringBuilder addString = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            addString.append(input);
            addString.deleteCharAt(i);
            deleteDist.add(addString.toString());
            addString = new StringBuilder();
        }
    }

    private static char[] swapinString(String str, int i, int j)
    {
        char charstoSwap[] = str.toCharArray();
        char temp = charstoSwap[i];
        charstoSwap[i] = charstoSwap[j];
        charstoSwap[j] = temp;
        return charstoSwap;
    }

    private void transpositionDistance(String input, Set<String> transDist) {
        StringBuilder addString = new StringBuilder();
        //Set<String> transDist = new TreeSet<>();
        addString.append(input);
        if (input.length() == 1) {
            return;
        }
        for (int i = 0; i < input.length(); ++i) {
            if (i != (input.length() - 1)) {
                char[] swapArray = swapinString(addString.toString(), i, i+1);
                StringBuilder swapString = new StringBuilder();
                for (int j = 0; j < swapArray.length; ++j) {
                    swapString.append(swapArray[j]);
                }
                transDist.add(swapString.toString());
            }
            else {
                char[] swapArray = swapinString(addString.toString(), i, i-1);
                StringBuilder swapString = new StringBuilder();
                for (int j = 0; j < swapArray.length; ++j) {
                    swapString.append(swapArray[j]);
                }
                transDist.add(swapString.toString());
            }
        }
    }

    private void alterationDistance(String input, Set<String> altDist)  {
        StringBuilder addString = new StringBuilder();
        addString.append(input);
        for (int i = 0; i < input.length(); ++i) {
            char initialChar = input.charAt(i);
            for (int j = 0; j < ALPHABET.length; ++j) {
                if (ALPHABET[j] == initialChar) {
                    continue;
                }
                addString.deleteCharAt(i);
                addString.insert(i, ALPHABET[j]);
                altDist.add(addString.toString());
                addString.deleteCharAt(i);
                addString.insert(i, initialChar);
            }
        }
    }

    private void insertString(StringBuilder addString, int position, char toAdd) {
        if (position > addString.length() - 1) {
            addString.append(toAdd);
        }
        else {
            addString.insert(position, toAdd);
        }
    }


    private void insertionDistance(String input, Set<String> insertDist) {
        for (int i = 0; i <= input.length(); ++i) {
            for (int j = 0; j < ALPHABET.length; ++j) {
                StringBuilder addString = new StringBuilder();
                addString.append(input);
                if (i > addString.length() - 1) {
                    addString.append(ALPHABET[j]);
                }
                else {
                    addString.insert(i, ALPHABET[j]);
                }
                insertDist.add(addString.toString());
            }
        }
    }

    public String tieBreaker(Map<String, Integer> suggest) {
        int maxCount = 0;
        Set<String> maxCounts = new TreeSet<>();
        //find the word with the greatest frequency
        for (Map.Entry<String, Integer> n : suggest.entrySet()) {
            if (n.getValue() > maxCount) {
                maxCount = n.getValue();
            }
        }
        //check to see if there are any other words at that frequency
        for (Map.Entry<String, Integer> n : suggest.entrySet()) {
            if (n.getValue() == maxCount) {
                maxCounts.add(n.getKey());
            }
        }
        // if there are, choose the one that appears first in alphabetical order (which will just be the first element in the set)
        for (String s : maxCounts) {
            return s;
        }
        return null;
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        String suggest = "";
        Node suggestNode = new Node();
        inputWord = inputWord.toLowerCase();
        Map<String, Integer> suggestMap = new TreeMap<String, Integer>();
        Set<String> editSuggest = new TreeSet<>();
        deletionDistance(inputWord, editSuggest);
        transpositionDistance(inputWord, editSuggest);
        alterationDistance(inputWord, editSuggest);
        insertionDistance(inputWord, editSuggest);

        //see if word is already in dictionary
        suggestNode = (Node)dictionary.find(inputWord);
        if (suggestNode != null) {
            suggest = inputWord;
            System.out.println(suggest);
            return suggest;
        }

        //edit distance 1 words
        for (String s : editSuggest) {
             suggestNode = (Node)dictionary.find(s);
             if (suggestNode != null) {
                 suggestMap.put(s, suggestNode.getValue());
             }
        }

        // if there are no edit distance 1 words, check for edit distance 2
        if (suggestMap.isEmpty()) {
            Set<String> editSuggest2 = new TreeSet<>();

            for (String s1 : editSuggest) {
                deletionDistance(s1, editSuggest2);
                alterationDistance(s1, editSuggest2);
                transpositionDistance(s1, editSuggest2);
                insertionDistance(s1, editSuggest2);
                for (String s2 : editSuggest2) {
                    suggestNode = (Node)dictionary.find(s2);
                    if (suggestNode != null) {
                        suggestMap.put(s2, suggestNode.getValue());
                    }
                }
            }

            if (suggestMap.isEmpty()) {
                return null;
            }
        }

        if (suggestMap.size() > 1) {
            return tieBreaker(suggestMap);
        }
        else if (suggestMap.size() == 1) {
             for (String s2 : suggestMap.keySet()) {
                 return s2;
             }
        }

        return null;
    }

    public String toString() {
        return dictionary.toString();
    }
}
