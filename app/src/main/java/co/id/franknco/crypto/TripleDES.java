package co.id.franknco.crypto;

import android.util.Base64;
import android.util.Log;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by e_er_de on 15/09/2017.
 */

public class TripleDES {

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public byte[] decrypt3DES(byte[] message, byte[] bkey) {
        try {
            if(message.length % 8 != 0){ //not a multiple of 8
                //create a new array with a size which is a multiple of 8
                byte[] padded = new byte[message.length + 8 - (message.length % 8)];

                //copy the old array into it
                System.arraycopy(message, 0, padded, 0, message.length);
                message = padded;
            }
            Security.addProvider(new BouncyCastleProvider());
            SecretKey keySpec = new SecretKeySpec(bkey, "DESede");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            Cipher d_cipher = Cipher.getInstance("DESede/CBC/NOPADDING", "BC");
            d_cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
            byte[] cipherText = d_cipher.doFinal(message);
            // System.out.println("Ciphertext: " + new
            // sun.misc.BASE64Encoder().encode(cipherText));
            //System.out.println("hasil dec 3des :" + bytesToHex(cipherText));
            return cipherText;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public byte[] encrypt3DES(byte[] message, byte[] bkey) {
        try {
            if(message.length % 8 != 0){ //not a multiple of 8
                //create a new array with a size which is a multiple of 8
                byte[] padded = new byte[message.length + 8 - (message.length % 8)];

                //copy the old array into it
                System.arraycopy(message, 0, padded, 0, message.length);
                message = padded;
            }

            Security.addProvider(new BouncyCastleProvider());
            SecretKey keySpec = new SecretKeySpec(bkey, "DESede");
            IvParameterSpec iv = new IvParameterSpec(new byte[8]);
            Cipher e_cipher = Cipher.getInstance("DESede/CBC/NOPADDING", "BC");
             e_cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
            byte[] cipherText = e_cipher.doFinal(message);
            // System.out.println("Ciphertext: " + new
            // sun.misc.BASE64Encoder().encode(cipherText));
            // System.out.println("hasil 3des :"+bytesToHex(cipherText));
            return cipherText;
        } catch (Exception e) {

        }
        return null;
    }


    /** SHA-2 PASSWORD */
    public String stringToSHA2(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        digest.reset();
        return String.format("%0" + (digest.digest(password.getBytes()).length * 2)
                + "X", new BigInteger(1, digest.digest(password.getBytes())));
    }

    /** CONVERT DECODE ENDCODE STRING TO BYTE[] */
    public String decodeUTF8(byte[] bytes) {
        return new String(bytes, UTF8_CHARSET);
    }

    public byte[] encodeUTF8(String string) {
        return string.getBytes(UTF8_CHARSET);
    }

    /** CONVERT STRING TO HEX */
    public String stringToHex2(String input) throws UnsupportedEncodingException {
        if (input == null) throw new NullPointerException();
        return asHex(input.getBytes());
    }

    public String asHex(byte[] buf) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i) {
            chars[2 * i] = hexArray[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = hexArray[buf[i] & 0x0F];
        }
        return new String(chars);
    }

    /** CONVERT BYTE TO HEX */
    public String bytesToHex(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public  byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    /** CONVERT HEX TO STRING */
    public String convertHexToString(String hex) {
        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < hex.length() - 1; i += 2) {
            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return sb.toString();
    }

    public String hexToBin(String s) {
        return new BigInteger(s, 16).toString(2);
    }
}
