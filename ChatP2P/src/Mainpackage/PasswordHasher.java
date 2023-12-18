package Mainpackage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Matteo
 */
public class PasswordHasher {
    
    public static String hashPassword(String password) {
        try {
            // Choose a strong hashing algorithm, such as SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Convert the password to bytes and hash it
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle the exception appropriately
            e.printStackTrace();
            return null;
        }
    }
    
}
