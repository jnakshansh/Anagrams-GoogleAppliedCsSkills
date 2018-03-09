package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

 public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList;
    private HashSet<String> wordset;
    private HashMap<String, ArrayList<String>> lettersToWord;


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        wordset=new HashSet<String>();
        wordList = new ArrayList<String>();
        lettersToWord= new HashMap<String, ArrayList<String>>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordset.add(word);
            wordList.add(word);
            String k=sortLetters(word);
            //Add word to lettersToWord
            //Check if the key already exists
            if(lettersToWord.containsKey(k)){
                lettersToWord.get(k).add(word);
            }
            //key doesn't exist
            else {
                ArrayList<String> arrayList=new ArrayList<String>();
                arrayList.add(word);
                lettersToWord.put(k,arrayList);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordset.contains(word)&& !word.contains(base);
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String k,l;
        for(char i='a';i<'z';i++)
        {
            l=sortLetters(word.concat(""+i));
            if (lettersToWord.containsKey(l)) {
                //Get the words
                ArrayList<String> words = lettersToWord.get(l);

                for (int w = 0; w < words.size(); w++) {
                    String wrd = words.get(w);
                    // wrd is the new word
                    // word is the base word
                    if (isGoodWord(wrd, word)) {
                        result.add(wrd);
                    }
                }

            }
            for(char j='a';j<'z';j++) {
                k = sortLetters(word.concat("" + j + i));

                if (lettersToWord.containsKey(k)) {
                    //Get the words
                    ArrayList<String> words = lettersToWord.get(k);

                    for (int w = 0; w < words.size(); w++) {
                        String wrd = words.get(w);
                        // wrd is the new word
                        // word is the base word
                        if (isGoodWord(wrd, word)) {
                            result.add(wrd);
                        }
                    }

                }

            }

        }
        return result;
    }

    public String pickGoodStarterWord() {
        String pickedWord = "";

        while (true) {

            // Pick a random integer between 0 and number of words in the dictionary
            int randomIndex = random.nextInt(wordList.size());

            pickedWord = wordList.get(randomIndex);

            Log.v("pickGoodStarterWord", pickedWord);

            if (pickedWord.length() >= DEFAULT_WORD_LENGTH && getAnagramsWithOneMoreLetter(pickedWord).size() >= MIN_NUM_ANAGRAMS && pickedWord.length() <= MAX_WORD_LENGTH) {
                break;
            }
        }


        return pickedWord;
    }
    public String sortLetters(String word){
        char characters[] = word.toCharArray();

        for (int i = 0; i < characters.length; i++) {
            for (int j = 1; j < characters.length; j++) {

                if (characters[j] < characters[j - 1]) {
                    char temp = characters[j - 1];
                    characters[j - 1] = characters[j];
                    characters[j] = temp;
                }

            }
        }
        word = new String(characters);
        return word;
    }
}
