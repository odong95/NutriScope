package edu.utdallas.csdesign.spring17.nutriscope.login;


import android.os.AsyncTask;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class LoginModelSecurity {


    private LoginModel model;
    private boolean isLoggedIn;
    private byte[] pepper = {8, -52, -61, 86, -55, -75, -94, 14, 99, -36, 100, 118, 74, 20, 101, 9, 49, 118, -62, 27, 121, -14, -97, -24, 45, -113, 107, 126, 94, -48, -81, 36, -55, -92, -34, -11};

    public LoginModelSecurity(LoginModel model) {
        this.model = model;
    }


    public void attemptLogin(final String username, final String password) {
        isLoggedIn = false;
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean performRegister(String username, String password) {
        try {
            byte[] salt = generateSalt();
            byte[] hash = hashPassword(password, salt);


            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isValidInput(String username, String password) {
        if (!isValidLogin(username) || !isValidPassword(password)) {
            return false;
        }
        return true;
    }

    public boolean isValidLogin(String login) {
        Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
        Matcher m = p.matcher(login);
        boolean b = m.find();
        if (b) {
            return false;
        }
        return true;
    }

    public boolean isValidPassword(String password) {
        Pattern p = Pattern.compile("['$<>^]");
        Matcher m = p.matcher(password);

        if (password.length() < 8 || password.length() > 40) {
            return false;
        } else if (m.find()) {
            return false;
        }
        return true;
    }


    public byte[] generateSalt() {
        SecureRandom rand = new SecureRandom();
        byte[] salt = new byte[36];
        rand.nextBytes(salt);
        return salt;
    }

    public byte[] hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(salt);
        outputStream.write(pepper);

        byte saltPepper[] = outputStream.toByteArray();
        PBEKeySpec key = new PBEKeySpec(password.toCharArray(), saltPepper, 100, 256);
        SecretKeyFactory keyGen = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return keyGen.generateSecret(key).getEncoded();
    }

    public boolean checkPassword(String password, String hash, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        byte[] hashPass = hashPassword(password, salt);
        boolean result = new String(hashPass, "ISO-8859-1").equals(hash);
        return result;
    }

}
