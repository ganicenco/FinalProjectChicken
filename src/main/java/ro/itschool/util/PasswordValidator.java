package ro.itschool.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final int MIN_LENGTH = 6;

    public static boolean isPasswordValid(String password){
        if(password == null || password.length()<MIN_LENGTH){
            return false;
        }
        // Check for uppercase, lowercase, digit, and special character

        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&*]).*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        // Ensure the password doesn't contain common words or patterns

        boolean noCommonWords = !password.matches(".*(password|123456|admin1|user12).*");
        return matcher.matches() && noCommonWords;
    }

    public static void main(String[] args) {
        String password = "SecureP@ss123";

        if(isPasswordValid(password)){
            System.out.println("Password registered");
        } else {
            System.out.println("Password must contain at least six characters and have at least one uppercase, one lowercase, one digit, and one special character. /n" +
                    "Please try again!");
        }
    }
}
