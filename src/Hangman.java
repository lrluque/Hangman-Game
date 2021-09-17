import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Hangman {
    public static void main(String[] args) throws IOException {
        boolean play = false;
        int option;
        boolean menu = true;
        boolean menu2 = true;
        Scanner scan = new Scanner(System.in);


        while (menu) {
            System.out.println("************************************");
            System.out.println("Welcome to Hangman Game! \n1) Play\n2) Exit");
            System.out.println("************************************");
            menu2 = true;
            while(menu2) {
                option = scan.nextInt();
                if (option == 1) {
                    play = true;
                    menu2 = false;
                } else if (option == 2) {
                    play = false;
                    menu = false;
                    menu2 = false;
                } else {
                    System.out.println("************************************");
                    System.out.println("Invalid input. Please type 1 or 2.\n1) Play\n2) Exit");
                    System.out.println("************************************");
                }
            }


            while (play) {
                BufferedReader reader = new BufferedReader(new FileReader("words.txt"));
                String line = reader.readLine();
                List<String> words = new ArrayList<String>();
                while (line != null) {
                    String[] wordsLine = line.split(" ");
                    for (String word : wordsLine) {
                        words.add(word);
                    }
                    line = reader.readLine();
                }
                boolean correctWord = false;
                Random rand = new Random(System.currentTimeMillis());
                String word = words.get(rand.nextInt(words.size())).toUpperCase();
                int health = 6;
                char letter;
                String letter2;
                String wordProgress = "_";
                String usedLetters = "";


                for (int i = word.length() - 2; i >= 0; i--) {
                    wordProgress = wordProgress + "_";
                }

                System.out.println(wordProgress);

                while (!correctWord && health > 0 && play) {
                    Pattern p = Pattern.compile(".*[A-Z]");
                    System.out.println("Type a letter: ");
                    int success = 0;

                    letter2 = scan.next().toUpperCase();
                    letter = letter2.charAt(0);

                    if (letter2.length() > 1) {
                        if (letter2.equals(word)) {
                            System.out.println(word);
                            System.out.println("The word is correct!");
                            play = false;
                        } else {
                            health = 0;
                        }
                        break;
                    }

                    boolean isLetter = p.matcher(Character.toString(letter)).matches();
                    if (isLetter) {
                        for (int o = 0; o < word.length(); o++) {
                            if (letter == word.charAt(o)) {
                                wordProgress = wordProgress.substring(0, o) + letter + wordProgress.substring(o + 1);
                                success++;
                            }
                            if (wordProgress.equals(word)) {
                                System.out.println(word);
                                System.out.println("The word is correct!");
                                menu2 = true;
                                correctWord = true;
                                play = false;
                            }
                            if (o == word.length() - 1 && success == 0) {
                                System.out.println("The letter " + letter + " is not correct!");

                                if (!usedLetters.contains(Character.toString(letter))) {
                                    health--;
                                }
                                break;
                            }
                        }
                        if (!usedLetters.contains(Character.toString(letter))) {
                            usedLetters = usedLetters + letter + " ";
                        }

                    } else {
                        System.out.println("Invalid character!");
                    }
                    System.out.println("************************************");
                    System.out.println(wordProgress);
                    System.out.println("Lives: " + health);
                    System.out.println("Used letters: " + usedLetters);
                    System.out.println("************************************");
                }
                if (health == 0) {
                    System.out.println("You lose!");
                    System.out.println("The word was " + word);
                    play = false;

                }
            }
            if (!play) {
                menu2 = false;
            }
        }
    }
}








