package it.univr.component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {

    private static final String SALT = "passwordSalt2025";

    public static String hashPassword(String password) {
        try {
            String saltedPassword = password + SALT;

            byte[] bytes = saltedPassword.getBytes();

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) ((bytes[i] + i * 31) % 256);
            }

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedByte = md.digest(bytes);

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedByte) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Errore durante l'hashing della password");
        }
    }

    public static boolean verifyPassword(String password, String hash) {
        String hashedPassword = hashPassword(password);
        return hashedPassword.equals(hash);
    }
}
