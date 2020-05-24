/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validations_and_methods;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static private_school.PrivateSchoolDataBase.sc;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */

/*
Method to validate that user types letters , not numbers or symbols
 */
public class UserInputValidations {

    public static boolean isNoNumbersValid(String s) {
        String regex = "^[a-zA-Z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence) s);
        return matcher.matches();
    }

    /*
     Method to validate that user types a double number, not letters or symbols
     */
    public static double validDouble() {

        double nextDouble;
        do {
            if (sc.hasNextDouble()) {
                nextDouble = sc.nextDouble();
                break;
            } else {
                System.out.print("\nWrong input. Please enter a valid input: ");
                sc.next();
            }
        } while (true);
        return nextDouble;
    }

    /*
         Method to validate that user types integer number and that this number is existable in user's choices
     */
    public static int validInt(HashMap hm) {

        int nextInt = 0;
        do {
            if (sc.hasNextInt()) {
                nextInt = sc.nextInt();
                if (!hm.containsKey(nextInt)) {
                    System.out.print("\nWrong input. Please enter a valid input: ");
                } else {
                    break;
                }
            } else {
                System.out.print("\nWrong input. Please enter a valid input: ");
                sc.next();
            }
        } while (true);
        return nextInt;
    }

    /*
         Method to validate that user types integer number, not letters or symbols
     */
    public static int validInt() {

        int nextInt = 0;
        do {
            if (sc.hasNextInt()) {
                nextInt = sc.nextInt();
                break;
            } else {
                System.out.print("\nWrong input. Please enter a valid input: ");
                sc.next();
            }
        } while (true);
        return nextInt;
    }

    /*
yes or no validation for user's input
     */
    public static boolean yesOrNo() {
        do {
            String answer = sc.next();
            if (answer.trim().toLowerCase().equals("yes")) {
                return true;
            } else if (answer.trim().toLowerCase().equals("no")) {
                return false;
            } else {
                System.out.println("\nWrong Input. Type yes for affirmation or no for refusal: ");
            }
        } while (true);
    }
}
