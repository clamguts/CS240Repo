package spell;


import java.io.File;
import java.io.IOException;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {

    private Trie dictionary;
    private static final char[] ALPHABET = new char[] {'a', 'b', 'c', 'd', 'e', 'f',
    'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v','w', 'x', 'y', 'z'};

    public SpellCorrector() {
        this.dictionary = new Trie();
    }


    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File dictFile = new File(dictionaryFileName);
        Scanner scan = new Scanner(dictFile);
        while (scan.hasNext()) {
            String word = scan.next();
            word = word.toLowerCase();
            dictionary.add(word);
        }
    }

    private char[] swapInString(String swapString, int i, int j) {
        char[] toSwap = swapString.toCharArray();
        char temp = toSwap[i];
        toSwap[i] = toSwap[j];
        toSwap[j] = temp;
        return toSwap;
    }

    private void deletionDistance(String word, Set<String> deleteDist) {
        StringBuilder currString = new StringBuilder();
        for (int i = 0 ; i < word.length(); ++i) {
            currString.append(word);
            currString.deleteCharAt(i);
            deleteDist.add(currString.toString());
            currString = new StringBuilder();
        }
    }

    private void transpositionDistance(String word, Set<String> transDist) {
        if (word.length() == 1) {
            return;
        }
        for (int i = 0; i < word.length(); ++i) {
            StringBuilder currString = new StringBuilder();
            if (i != word.length() - 1) {
                char[] swapString = swapInString(word, i, i+1);
                for (int j = 0; j < swapString.length; ++j) {
                    currString.append(swapString[j]);
                }
                transDist.add(currString.toString());
            }
            else {
                char[] swapString = swapInString(word, i, i-1);
                for (int j = 0; j < swapString.length; ++j) {
                    currString.append(swapString[j]);
                }
                transDist.add(currString.toString());
            }
        }
    }

    private void alterationDistance(String word, Set<String> altDist) {
        StringBuilder currString = new StringBuilder();
        currString.append(word);
        for (int i = 0; i < word.length(); ++i) {
            char initialChar = word.charAt(i);
            for (int j = 0; j < ALPHABET.length; ++j) {
                if (ALPHABET[j] == initialChar) {
                    continue;
                }
                currString.deleteCharAt(i);
                currString.insert(i, ALPHABET[j]);
                altDist.add(currString.toString());
                currString.deleteCharAt(i);
                currString.insert(i, initialChar);
            }
        }
    }

    private void insertionDistance(String word, Set<String> insertDist) {
        for (int i = 0; i <= word.length(); ++i) {
            for (int j = 0; j < ALPHABET.length; ++j) {
                StringBuilder currString = new StringBuilder();
                currString.append(word);
                if (i > (word.length() - 1)) {
                    currString.append(ALPHABET[j]);
                }
                else {
                    currString.insert(i, ALPHABET[j]);
                }
                insertDist.add(currString.toString());
            }
        }
    }

    private String tieBreaker(Map<String, Integer> suggestMap) {
        int maxCount = 0;
        Set<String> maxCounts = new TreeSet<>();
        for (Map.Entry<String, Integer> i : suggestMap.entrySet()) {
            if (i.getValue() > maxCount) {
                maxCount = i.getValue();
            }
        }
        for (Map.Entry<String, Integer> i : suggestMap.entrySet()) {
            if (i.getValue() == maxCount) {
                maxCounts.add(i.getKey());
            }
        }
        if (!maxCounts.isEmpty()) {
            return maxCounts.iterator().next();
        }
        return null;
    }


    @Override
    public String suggestSimilarWord(String inputWord) {
        String suggest;
        Set<String> editSuggest = new TreeSet<>();
        Node suggestNode;
        Map<String, Integer> suggestMap = new TreeMap<>();
        inputWord = inputWord.toLowerCase();
        deletionDistance(inputWord, editSuggest);
        transpositionDistance(inputWord, editSuggest);
        alterationDistance(inputWord, editSuggest);
        insertionDistance(inputWord, editSuggest);

        suggestNode = (Node) dictionary.find(inputWord);
        if (suggestNode != null) {
            suggest = inputWord;
            return suggest;
        }

        for (String s : editSuggest) {
            suggestNode = (Node) dictionary.find(s);
            if (suggestNode != null) {
                suggestMap.put(s, suggestNode.getValue());
            }
        }

        if (suggestMap.isEmpty()) {
            Set<String> editSuggest2 = new TreeSet<>();
            for (String s : editSuggest) {
                deletionDistance(s, editSuggest2);
                alterationDistance(s, editSuggest2);
                transpositionDistance(s, editSuggest2);
                insertionDistance(s, editSuggest2);
                for (String s1 : editSuggest2) {
                    suggestNode = (Node) dictionary.find(s1);
                    if (suggestNode != null) {
                        suggestMap.put(s1, suggestNode.getValue());
                    }
                }
            }
        }

        if (suggestMap.isEmpty()) {
            return null;
        }
        if (suggestMap.size() > 1) {
            return tieBreaker(suggestMap);
        }

        return suggestMap.keySet().iterator().next();
    }

}
