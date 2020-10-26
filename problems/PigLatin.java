package problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PigLatin {

    /*          ALGORITHM
===============================================

     * 1- Split stream of words by spaces
     * 2- Split words by hyphens
     * 3- For each word
     *      a- Transform to lowercase and remove any non-alphanumeric characters (e.g. punctuations)
     *      b- Check if the word begins with a vowel or constant and process accordingly
     *      c- Update the word casing (i.e. lowercase & uppercase)
     *      d- Update punctuations
     *      e- Append processed word to the stream
     * 
     */
    private static String pigLatin(String s) {
        if (s == null || s.length() == 0)
            return "";

        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if(words[i].contains("-")) {
                String[] hyphenatedWords = words[i].split("-");
                for(int j=0 ; j< hyphenatedWords.length ; j++) {
                    processWord(hyphenatedWords[j], sb);
                    
                    if(j != hyphenatedWords.length-1)
                       sb.append("-");
                }

            } else {
                processWord(words[i], sb);
                sb.append(" ");
            }

        }

        return sb.toString().trim();
    }

    /**
     * Driver method
     *      a- Transform to lowercase and remove any non-alphanumeric characters (e.g. punctuations)
     *      b- Check if the word begins with a vowel or constant and process accordingly
     *      c- Update the word casing (i.e. lowercase & uppercase)
     *      d- Update punctuations
     *      e- Append processed word to the stream
     * @param s word being processed
     * @param sb result stream
     */
    private static void processWord(String s, StringBuilder sb) {
        String res = "";
        String word = cleanup(s);

        if (endsWithWay(s)) {
            sb.append(s);
            return;
        }

        if (beginsWithVowel(word)) {
            res = withVowel(word);
        } else {
            res = withConsonant(word);
        }

        res = adjustCase(res, upperCaseIndices(s));
        res = adjustPunctuation(res, punctuationIndices(s));
        sb.append(res);
    }

    /**
     * Transform input @param s to lowercase and remove any non-alphanumeric character.
     * @param s input string
     * @return @param s to lower case
     */
    private static String cleanup(String s) {
        return s.replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    }

    private static boolean endsWithWay(String s) {
        return s.matches("\\w*way$") || s.matches("\\w*way.$");
    }

    private static boolean beginsWithVowel(String s) {
        char a = s.charAt(0);

        return a == 'a' || a == 'e' || a == 'i' || a == 'o' || a == 'u';
    }

    private static String withVowel(String s) {
        StringBuilder sb = new StringBuilder(s);
        sb.append("way");
        return sb.toString();
    }

    private static String withConsonant(String s) {
        StringBuilder sb = new StringBuilder();
        char a = s.charAt(0);
        for (int i = 1; i < s.length(); i++) {
            sb.append(s.charAt(i));
        }
        sb.append(a);
        sb.append("ay");
        return sb.toString();
    }

    private static Map<Integer, Character> punctuationIndices(String s) {
        Map<Integer, Character> indices = new HashMap<>();

        int n = s.length();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c) && !Character.isDigit(c))
                indices.put(n - i, c);
        }
        return indices;
    }

    private static String adjustPunctuation(String s, Map<Integer, Character> indices) {
        StringBuilder sb = new StringBuilder(s);
        int n = s.length();
        for (int idx : indices.keySet()) {
            sb.insert(n - idx + 1, indices.get(idx));
        }
        return sb.toString();
    }

    private static List<Integer> upperCaseIndices(String s) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isUpperCase(s.charAt(i)))
                indices.add(i);
        }
        return indices;
    }

    private static String adjustCase(String s, List<Integer> indices) {
        StringBuilder sb = new StringBuilder(s);
        for (int idx : indices) {
            sb.deleteCharAt(idx);
            sb.insert(idx, Character.toUpperCase(s.charAt(idx)));

        }
        return sb.toString();
    }

    
    public static void validate(String input, String expected) {
        String actual = pigLatin(input);
        final String VERDICT = (expected.equals(actual)) ? "PASS" : "FAIL";
        System.out.println("[" + VERDICT + "]\t- Result: " + actual + "\tExpected: " + expected);
    }

    public static void main(String[] args) {
        validate("Hello", "Ellohay");
        validate("apple", "appleway");
        validate("stairway", "stairway");
        validate("can’t", "antca’y");
        validate("end.", "endway.");
        validate("this-thing", "histay-hingtay");
        validate("Beach", "Eachbay");
        validate("McCloud", "CcLoudmay");

        validate("endway.", "endway.");
        validate("McClou'd", "CcLoudma'y");
        validate("Ernest-Hemingway.", "Ernestway-Hemingway.");
    }

}
