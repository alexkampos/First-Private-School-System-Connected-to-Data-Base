/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validations_and_methods;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alex K (alexkambos@gmail.com)
 */
public class DateMethods {

    /*
    Validation for date
     */
    public static boolean isValidDate(String s) {
        String regex = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence) s);
        return matcher.matches();
    }

    public static String dateToString(LocalDate ld) {
        if (ld != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String dateToString = ld.format(dtf);
            return dateToString;
        } else {
            return null;
        }
    }

    public static String dateTimeToString(LocalDateTime ldt) {
        if (ldt != null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            String dateTimeToString = ldt.format(dtf);
            return dateTimeToString;
        } else {
            return null;
        }
    }

    public static boolean isValidDateTime(String s) {
        String regex = "^(3[01]|[12][0-9]|0[1-9])-(1[0-2]|0[1-9])-[0-9]{4} (00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9])$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher((CharSequence) s);
        return matcher.matches();
    }
}
