package bullscows;

import java.util.*;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static String SECRET_CODE = "";
    static int numberOfTurn = 1;

    public static boolean check(String number) {

        boolean gameEnd = true;
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < number.length(); i++) {
            for (int j = 0; j < SECRET_CODE.length(); j++) {
                if (number.charAt(i) == SECRET_CODE.charAt(j)) {
                    if (i == j) {
                        bulls++;
                    } else {
                        cows++;
                    }
                }
            }
        }
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
        if (bulls == SECRET_CODE.length()) {
            System.out.println("Congratulations! You guessed the secret code.");
            gameEnd = false;
        }
        return gameEnd;
    }

    public static String readNumber() {

        int lengthOfCode = SECRET_CODE.length();

        String number = scanner.next();

        if (number.length() != lengthOfCode || number.replaceAll("[^0-9a-z]", "").length() != lengthOfCode) {
            System.out.println("Неверный ввод");
            readNumber();
        }
        return number;
    }

    public static String randomGenerator(int length, int number) {

        String set = "0123456789abcdefghijklmnopqrstuvwxyz";
        String possibleSymbols = set.substring(0, number);

        List<String> randomList = Arrays.asList(possibleSymbols.split(""));
        Collections.shuffle(randomList);
        StringBuilder result = new StringBuilder();
        for (var ch : randomList.subList(0, length)) {
            result.append(ch);
        }
        StringBuilder hiddenSecretCod = new StringBuilder();
        hiddenSecretCod.append("*".repeat(Math.max(0, length)));

        if (number > 10 && number != 11) {
            System.out.printf("The secret is prepared: %s (0-9, a-%s).\n", hiddenSecretCod, possibleSymbols.charAt(number - 1));
        } else if (number == 11) {
            System.out.printf("The secret is prepared: %s (0-9, %s).\n", hiddenSecretCod, possibleSymbols.charAt(number - 1));
        } else {
            System.out.printf("The secret is prepared: %s (0-%s).\n", hiddenSecretCod, possibleSymbols.charAt(number - 1));
        }
        return result.toString();
    }

    public static void secretCode() {

        System.out.print("Input the length of the secret code:");
        String length1 = scanner.nextLine();
        if (length1.replaceAll("\\d", "").length() != 0) {
            System.out.printf("Error: %s isn't a valid number.\n", length1);
            System.exit(0);
        }
        int length = Integer.parseInt(length1);

        System.out.print("Input the number of possible symbols in the code:");
        String possibleSymbols1 = scanner.nextLine();
        if (possibleSymbols1.replaceAll("\\d", "").length() != 0) {
            System.out.printf("Error: %s isn't a valid number.\n", possibleSymbols1);
            System.exit(0);
        }
        int possibleSymbols = Integer.parseInt(possibleSymbols1);

        if (possibleSymbols < length) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", length, possibleSymbols);
            System.exit(0);
        } else if (length <= 0 || length > 36 || possibleSymbols > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.\n", length);
            System.exit(0);
        } else {
            SECRET_CODE = randomGenerator(length, possibleSymbols);
            System.out.println("Okay, let's start a game!");
        }
    }

    public static void main(String[] args) {
        secretCode();
        do {
            System.out.printf("Turn %d:", numberOfTurn);
            numberOfTurn++;
        } while (check(readNumber()));
    }
}
