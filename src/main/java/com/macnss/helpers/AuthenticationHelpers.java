package com.macnss.helpers;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationHelpers {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates a random code with the specified number of digits.
     *
     * @param numDigits The number of digits in the generated code.
     * @return The generated random code.
     */
    public static int generateRandomCode(int numDigits) {
        if (numDigits <= 0) {
            throw new IllegalArgumentException("Number of digits must be greater than 0");
        }
        int min = (int) Math.pow(10, numDigits - 1);
        int max = (int) Math.pow(10, numDigits) - 1;
        return min + random.nextInt(max - min + 1);
    }


    /**
     * Checks if the input string is a valid phone number with 10 digits.
     *
     * @param input The input string to validate.
     * @return True if the input is a valid phone number; otherwise, false.
     */
    public static boolean isPhoneNumber(String input) {
        return input.matches("\\d{10}");
    }

    /**
     * Hashes a plain text password using BCrypt.
     *
     * @param plainTextPassword The plain text password to hash.
     * @return The hashed password.
     */
    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(plainTextPassword, salt);
    }

    /**
     * Checks if a plain text password matches a hashed password.
     *
     * @param plainTextPassword The plain text password to check.
     * @param hashedPassword    The hashed password to compare against.
     * @return True if the plain text password matches the hashed password; otherwise, false.
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

    /**
     * Checks if the input string is a valid email address.
     *
     * @param input The input string to validate.
     * @return True if the input is a valid email address; otherwise, false.
     */
    public static boolean isEmail(String input) {
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
}