package bullscows;

import java.util.Random;
import java.util.Scanner;

public class BullsAndCows {

    static int turnCount = 1;
    static int cows;
    static int bulls;
    static String secret_number = "";

    static void checkCowsBulls(int[] number, int[] secret) {
        cows = 0;
        bulls = 0;
        for (int i = 0; i < secret.length; i++) {
            if (secret[i] == number[i]) {
                bulls++;
                continue;
            }
            for (int k : number) {
                if (secret[i] == k) {
                    cows++;
                }
            }
        }
    }

    static void printResult() {

        if (bulls > 1 && cows > 1) {
            System.out.printf("Grade: %d bulls and %d cows\n", bulls, cows);
        } else if (bulls == 0 && cows > 1) {
            System.out.printf("Grade: %d cows\n", cows);
        } else if (bulls > 1 && cows == 0) {
            System.out.printf("Grade: %d bulls\n", bulls);
        } else if (bulls == 1 && cows == 1) {
            System.out.printf("Grade: %d bull and %d cow\n", bulls, cows);
        } else if (bulls == 0 && cows == 1) {
            System.out.printf("Grade: %d cow\n", cows);
        } else if (bulls == 1 && cows == 0) {
            System.out.printf("Grade: %d bull\n", bulls);
        } else {
            System.out.printf("Grade: %d bull and %d cow\n", bulls, cows);
        }
        if (secret_number.length() == bulls) {
            System.out.println("Congratulations! You guessed the secret code.");
            System.exit(0);
        }
    }

    static int[] strintToArray(String line) {
        int[] array = new int[line.length()];
        for (int i = 0; i < array.length; i++) {
            array[i] = line.charAt(i) - 48;
        }
        return array;
    }

    static String playerNumber() {
        String number = new Scanner(System.in).next();
        if (number.length() < secret_number.length()) {
            playerNumber();
        }
        return number;
    }

    static void secretCodeGenerate() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Input the length of the secret code:");
        String lengthCheck = scanner.nextLine();
        if (!lengthCheck.replaceAll("\\d", "").isEmpty()) {
            System.out.printf("Error: %s isn't a valid number.\n", lengthCheck);
            System.exit(0);
        }
        int length = Integer.parseInt(lengthCheck);

        System.out.print("Input the number of possible symbols in the code:");
        String numberOfSymbolsCheck = scanner.nextLine();
        if (!numberOfSymbolsCheck.replaceAll("\\d", "").isEmpty()) {
            System.out.printf("Error: %s isn't a valid number.\n", numberOfSymbolsCheck);
            System.exit(0);
        }
        int possibleSymbols = Integer.parseInt(numberOfSymbolsCheck);

        if (possibleSymbols < length) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, possibleSymbols);
            System.exit(0);
        } else if (length <= 0 || length > 36 || possibleSymbols > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", length);
            System.exit(0);
        } else {
            secret_number = randomGenerator(length, possibleSymbols).toString();
            System.out.println("Okay, let's start a game!");
        }
    }

    static StringBuilder randomGenerator(int lenght, int numberOfSymbols) {
        String set = "0123456789abcdefghijklmnopqrstuvwxyz";
        char possibleSymbols = set.substring(0, numberOfSymbols).charAt(numberOfSymbols - 1);

        StringBuilder stringBuilder = new StringBuilder(set);

        StringBuilder secret = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < lenght; i++) {
            int ran = random.nextInt(numberOfSymbols);
            secret.append(stringBuilder.charAt(ran));
            stringBuilder.replace(ran, ran + 1, "");
            numberOfSymbols--;
        }
        StringBuilder hiddenSecretCod = new StringBuilder();
        hiddenSecretCod.append("*".repeat(Math.max(0, lenght)));

        if (numberOfSymbols > 10 && numberOfSymbols != 11) {
            System.out.printf("The secret is prepared: %s (0-9, a-%s).\n", hiddenSecretCod, possibleSymbols);
        } else if (numberOfSymbols == 11) {
            System.out.printf("The secret is prepared: %s (0-9, %s).\n", hiddenSecretCod, possibleSymbols);
        } else {
            System.out.printf("The secret is prepared: %s (0-%s).\n", hiddenSecretCod, possibleSymbols);
        }
        return secret;
    }

    public static void main(String[] args) {

        secretCodeGenerate();
        while (true) {
            System.out.println("Turn " + turnCount++);
            String number = playerNumber();
            checkCowsBulls(strintToArray(number), strintToArray(secret_number));
            printResult();
        }
    }
}
