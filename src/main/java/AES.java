import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * This class provides encryption and decryption of passwords using AES algorithm
 * @author Yi Xiao
 * @version 1.0
 */

public class AES {
    private static final String SEC_KEY="CS2212MAPSYSTEM";
    private static SecretKeySpec secrekey;
    private static byte[] key;
    /**
     * Converts the input string into bytes and hashes it using SHA-1 algorithm to create a 128-bit AES key.
     * @param newKey The input string to be converted to the key.
     */
    private static void convToByte(String newKey)
    {
        try{
            key=newKey.getBytes("UTF-8");
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            key=sha.digest(key);
            key=Arrays.copyOf(key, 16);
            secrekey= new SecretKeySpec(key, "AES");
        }catch(Exception e)
        {

        }
    }


    /**
     * Encrypts the input password using AES algorithm with ECB block mode and PKCS5 padding.
     * @param encPW The password to be encrypted.
     * @return The encrypted password as a Base64-encoded string.
     */
    public static String encryptPW(String encPW) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {

            convToByte(SEC_KEY);
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secrekey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(encPW.getBytes("UTF-8")));


    }
    /**
     * Decrypts the input password using AES algorithm with ECB block mode and PKCS5 padding.
     * @param decPW The password to be decrypted, as a Base64-encoded string.
     * @return The decrypted password as a string.
     */
    public static String decryptPW(String decPW) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

            convToByte(SEC_KEY);
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secrekey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(decPW)));

    }
}
