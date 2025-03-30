package bullscows;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String validSymbols = "0123456789abcdefghijklmnopqrstuvwxyzz";
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");
        if (!sc.hasNextInt()) {
            String error = sc.nextLine();
            System.out.printf("Error: \"%s\" isn\'t a valid number.", error);
            System.exit(0);
        }
        int secretCodeLength = sc.nextInt();
        StringBuilder secretCodeLengthInStars = new StringBuilder();
        for (int i = 0; i < secretCodeLength; i++) {
            secretCodeLengthInStars.append("*");
        }
        System.out.println("Input the number of possible symbols in the code:");
        int numberOfPossibleSymbols = sc.nextInt();
        if (numberOfPossibleSymbols > validSymbols.length()) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            System.exit(0);
        }
        if (secretCodeLength > numberOfPossibleSymbols) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", secretCodeLength, numberOfPossibleSymbols);
            System.exit(0);

        }
        char[] secret = generateUniqueSecretCode(secretCodeLength, numberOfPossibleSymbols, validSymbols);
        if (numberOfPossibleSymbols <= 10) {
            System.out.printf("The secret is prepared: %s (0-%s).\n", secretCodeLengthInStars, validSymbols.charAt(numberOfPossibleSymbols - 1));
        } else {
            System.out.printf("The secret is prepared: %s (0-9, a-%s).\n", secretCodeLengthInStars, validSymbols.charAt(numberOfPossibleSymbols - 1));
        }
        System.out.println("Okay, let's start a game!");
        boolean isGameActive = true;
        int gameTurn = 0;
        while (isGameActive) {
            gameTurn++;
            System.out.printf("Turn %d:\n", gameTurn);
            isGameActive = gameRound(secret, sc);
        }
    }

    public static char[] generateUniqueSecretCode(int length, int numberOfPossibleSymbols, String validSymbols) {
        StringBuilder secret = new StringBuilder(length);
        for (int i = 0; i < length * 100; i++) {
            secret.append(validSymbols.charAt(new Random().nextInt(numberOfPossibleSymbols + 1)));
        }
        String verificationForUniqueCode = String.valueOf(secret);
        char[] secretCode = new char[length];
        int j = 0;
        boolean isGoodToGo = true;
        for (int i = 0; i < verificationForUniqueCode.length() - 1; i++) {
            isGoodToGo = true;
            for (int k = 0; k < secretCode.length - 1; k++) {
                if (verificationForUniqueCode.charAt(i) == secretCode[k]) {
                    isGoodToGo = false;
                    break;
                }
            }
            if (verificationForUniqueCode.charAt(i) != secretCode[j] && isGoodToGo) {
                secretCode[j] = verificationForUniqueCode.charAt(i);
                if (j < secretCode.length - 1) j++;
            }
        }
        return secretCode;
    }

    public static boolean gameRound(char[] secret, Scanner sc) {
        String guess = sc.nextLine();
        if (guess.isEmpty()) guess = sc.nextLine();
        int cowCounter = 0;
        int bullCounter = 0;
        for (int i = 0; i < secret.length; i++) {
            if (secret[i] == guess.charAt(i)) {
                bullCounter++;
            }
            for (int j = 0; j < secret.length; j++) {
                if (secret[i] == guess.charAt(j) && i != j) {
                    cowCounter++;
                }
            }
        }

        System.out.printf("Grade: %d bull and %d cow\n", bullCounter, cowCounter);
        if (bullCounter == secret.length) {
            System.out.println("Congratulations! You guessed the secret code.");
            return false;
        }
        return true;
    }
}
