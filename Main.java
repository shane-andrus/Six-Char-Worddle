import java.io.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ArrayList<Character> word = new ArrayList<Character>();
        File wordFile = new File("src/words.txt");
        String line = getRandomLine(wordFile.toString());
        for(Character letter: line.toCharArray()) {
            word.add(letter);
        }
        int tries = 0;
        final int maxTries = 8;
        System.out.println("x = incorrect\n! = out of position\n$ = correct");
        Scanner input = new Scanner(System.in);
        System.out.println("Guess the word (6 chars long): ");
        while (tries < maxTries) {
            System.out.print(tries+1 + ")");
            String guess = input.nextLine();
            while (!isValidGuess(guess, wordFile)) {
                System.out.println("Invalid attempt, try again.");
                System.out.print(tries+1 + ")");
                guess = input.nextLine();
            }
            printResults(guess, word);
            if (guess.equals(word)) {
                break;
            }
            tries++;
        }
        if (tries == 6) {
            System.out.print("The word is ");
            for(Character letter: word) {
                System.out.print(letter);
            }
            System.out.println();
        }
    }

    private static String getRandomLine(String path) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Random random = new Random();
        return lines.get(random.nextInt(lines.size()));
    }

    public static boolean isValidGuess(String guess, File file){
        return guess.length() == 6 && isRealWord(guess, file);
    }

    public static void printResults(String guess, ArrayList<Character> word) {
        ArrayList<Character> results = new ArrayList<Character>();
        for(int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == word.get(i)) {
                results.add('$');
            } else if (letterInWord(guess.charAt(i), word)) {
                results.add('!');
            } else {
                results.add('x');
            }
        }
        System.out.print("  ");
        for(Character result: results) {
            System.out.print(result);
        }
        System.out.println();
    }

    public static boolean isRealWord(String guess, File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            if(guess.equals(line)) {
                return true;
            }
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static boolean letterInWord(Character letter, ArrayList<Character> word) {
        for(int i = 0; i < word.size(); i++) {
            if (word.get(i) == letter) {
                return true;
            }
        }
        return false;
    }
}
