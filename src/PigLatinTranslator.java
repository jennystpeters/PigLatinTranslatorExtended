import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by jenny on 6/30/2017.
 */

//Translate from English to Pig Latin
public class PigLatinTranslator {

    public static void main(String[] args) {

        String keepGoing = "y";

        //User Prompts
        String welcomePrompt = "Welcome to the Pig Latin Translator!";
        String entryPrompt = "Enter a line to be translated: ";
        String continuePrompt = "Translate another line? (y/n) ";
        String closingPrompt = "See you later!";

        System.out.printf(welcomePrompt + "\n\n");

        String originalSentence;
        Scanner entry = new Scanner(System.in);

        //Loop as long as user enters "y" or "Y"
        while (keepGoing.equalsIgnoreCase("y")) {
            System.out.print(entryPrompt);

            originalSentence = entry.nextLine();

            if (!originalSentence.equals("")) {
                originalSentence.trim();

                //Use getTranslatedSentence to translate user's sentence to Pig Latin
                System.out.println(getTranslatedSentence(originalSentence));
            }
            System.out.print(continuePrompt);
            keepGoing = entry.nextLine();
        }
        System.out.println(closingPrompt + " -- " + getTranslatedSentence(closingPrompt));
    }

    public static String getTranslatedSentence(String originalSentence) {
        //Separate user's sentence into array of words
        String[] words = originalSentence.split("[^\\p{Alpha}\\p{Digit}\\p{Punct}]+");
        StringBuilder translatedSentence = new StringBuilder("");

        //Loop through array of words and use getTranslatedWord to translate each to Pig Latin
        for (int i = 0; i < words.length; i++) {

            String digitSymbolCheck = "[\\p{Digit}#$%&()*@~]";

            Pattern pattern = Pattern.compile(digitSymbolCheck);
            Matcher matcher = pattern.matcher(words[i]);

            //Do not translate if word contains number or symbol
            if (matcher.find()) {
                translatedSentence.append(words[i]);
            }
            //Translate contractions
            else if (words[i].contains("'")) {
                translatedSentence.append(getTranslatedWord(words[i]));
            }
            //Allow translation with punctuation - Temporarily split word and punctuation / Translate / Append
            else if (words[i].matches(".*\\p{Punct}*.")) {
                String[] punctuation = words[i].split("[\\p{Alpha}]");
                if(words[i].matches(".*[a-zA-Z].*")) {
                    words[i] = words[i].replaceAll("\\p{Punct}", "");
                    translatedSentence.append(getTranslatedWord(words[i]));
                }
                for (int j = 0; j < punctuation.length; j++) {
                    translatedSentence.append(punctuation[j]);
                }
            }
            //Translate words
            else {
                //Reassemble words into sentence
                translatedSentence.append(getTranslatedWord(words[i]));
            }

            if (i < words.length - 1) {
                translatedSentence.append(" ");
            }
        }
        //Return translated sentence to main to output to user
        return translatedSentence.toString();
    }

    public static String getTranslatedWord(String words) {
        String first = words.substring(0, 1);
        String translatedWords;

        //Check if the first letter of the word is a vowel (either option below [1 or 2] can be used)
        //Option 1:
        //if (first.equalsIgnoreCase("a") || first.equalsIgnoreCase("e") || first.equalsIgnoreCase("i") || first.equalsIgnoreCase("o") || first.equalsIgnoreCase("u")) {
        //Option 2:
        if (first.matches("a|e|i|o|u|A|E|I|O|U")) {
            //If the first letter is a vowel, use setVowelTranslation to translate the word to Pig Latin
            translatedWords = setVowelTranslation(words);
        } else {
            //If the first letter is not a vowel, use setConsonantTranslation to translate the word to Pig Latin
            translatedWords = setConsonantTranslation(words);
        }
        return translatedWords;
    }

    public static String setVowelTranslation(String words) {

        //Add "way" to the end of the word
        String translatedWords = getWordCase(words, (words.concat("way")));
        return translatedWords;
    }

    public static String setConsonantTranslation(String words) {
        int i;

        for (i = 0; i < words.length(); i++) {
            //Find how many consonants exist before the first vowel
            if (words.substring(i, i + 1).matches("a|e|i|o|u|A|E|I|O|U")) {
                //Break out of loop when vowel is found to use the active index value
                break;
            }
        }
        //Skip consonants preceding the first vowel. Add the skipped consonants and "ay" to end of word.
        String translatedWords = getWordCase(words, (words.substring(i, words.length()).concat(words.substring(0, i)).concat("ay")));
        return translatedWords;
    }

    public static String getWordCase(String originalWord, String wordCase) {

        if (originalWord.substring(0, originalWord.length()).matches("\\p{Upper}+")) {
            //Option 2: if (originalWord.substring(0,originalWord.length()).matches("\\p{Upper}+")) {
            //Option 3: if (wordCase.substring(0, 1).equals(wordCase.substring(0, 1).toUpperCase())) {
            //Uppercase word
            return wordCase.toUpperCase();
        } else if (originalWord.substring(0, 1).matches("\\p{Upper}")) {
            //Option 2: else if (originalWord.substring(0, 1).matches("[A-Z]")) {
            //Title case word (First letter is uppercase)
            return wordCase.substring(0, 1).toUpperCase().concat(wordCase.substring(1, wordCase.length()).toLowerCase());
        } else {
            //Lowercase word
            return wordCase.toLowerCase();
        }
    }
}