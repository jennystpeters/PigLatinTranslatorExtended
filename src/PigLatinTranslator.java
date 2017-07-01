import java.util.Scanner;

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
        String closingPrompt = "Goodbye!";

        System.out.printf(welcomePrompt + "\n\n");

        String originalSentence;
        Scanner entry = new Scanner(System.in);

        //Loop as long as user enters "y" or "Y"
        while (keepGoing.equalsIgnoreCase("y")) {
            System.out.print(entryPrompt);

            originalSentence = entry.nextLine();

            if (!originalSentence.equals("")) {
                originalSentence.trim().toLowerCase();

                //Use getTranslatedSentence to translate user's sentence to Pig Latin
                System.out.println(getTranslatedSentence(originalSentence));
            }
            System.out.print(continuePrompt);
            keepGoing = entry.nextLine();
        }
        System.out.print(closingPrompt);
    }

    public static StringBuilder getTranslatedSentence(String originalSentence) {
        //Separate user's sentence into array of words
        String[] words = originalSentence.split("[^\\p{Alpha}]+");
        StringBuilder translatedSentence = new StringBuilder("");

        //Loop through array of words and use getTranslatedWord to translate each to Pig Latin
        for (int i = 0; i < words.length; i++) {
            //Reassemble words into sentence
            translatedSentence.append(getTranslatedWord(words[i]));
            if (i < words.length - 1) {
                translatedSentence.append(" ");
            }
        }
        //Return translated sentence to main to output to user
        return translatedSentence;
    }

    public static String getTranslatedWord(String words) {
        String first = words.substring(0, 1);
        String translatedWords;

        //Check if the first letter of the word is a vowel (either option below [1 or 2] can be used)
        //Option 1:
        //if (first.equals("a") || first.equals("e") || first.equals("i") || first.equals("o") || first.equals("u")) {
        //Option 2:
        if (first.matches("a|e|i|o|u")) {
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
        String translatedWords = words + "way";
        return translatedWords;
    }

    public static String setConsonantTranslation(String words) {
        int i;
        for (i = 0; i < words.length(); i++) {
            //Find how many consonants exist before the first vowel
            if (words.substring(i, i + 1).matches("a|e|i|o|u")) {
                //Break out of loop when vowel is found to use the active index value
                break;
            }
        }
        //Skip consonants preceding the first vowel. Add the skipped consonants and "ay" to end of word.
        String translatedWords = words.substring(i, words.length()) + words.substring(0, i) + "ay";
        return translatedWords;
    }
}