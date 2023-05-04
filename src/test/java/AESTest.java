import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;


/**
 * The AESTest class contains JUnit test methods for the AES class.
 */
class AESTest {

    private String Text;
    private String result;


    @Test
    /** This is a JUnit test method to verify the functionality of the encryptPW() method in the AES class.
     * The test encrypts a given password string and compares the resulting encrypted string to an expected value.
     * @throws Exception if there is any error during the encryption process */

    void encryptPWTest1() throws Exception {
        Text = AES.encryptPW("test encryptPW");
        assertEquals("W5cP6NiDFwAiPZTmEbS6KA==", Text);

    }



    @Test
    /** This is a JUnit test method to verify the functionality of the encryptPW() method in the AES class.
     * The test encrypts a given password string and compares the resulting encrypted string to an expected value.
     * @throws Exception if there is any error during the encryption process
     */
    void encryptPWTest2() throws Exception {
        Text = AES.encryptPW("CS2212");
        assertEquals("AyA0tDtxgdEU40He4KDz4A==", Text);
    }

    @Test
    /** Test encryptPW throws exception when have null value */
    void encryptPWTest3(){

        assertThrows(Exception.class,
                () -> {
                    Text = AES.encryptPW(null);
                });
    }


    @Test

    /** This is a JUnit test method to verify that the encryptPW() method in the AES class throws an exception
     * when passed a null value as the password parameter.
     * @throws Exception if there is any error during the encryption process
     */
    void decryptPWTest1() throws Exception {
        result = AES.decryptPW("W5cP6NiDFwAiPZTmEbS6KA==");
        assertEquals("test encryptPW",result);

    }


    @Test

    /** This is a JUnit test method to verify the functionality of the decryptPW() method in the AES class.
     * The test decrypts a given encrypted password string and compares the resulting decrypted string to an expected value.
     * @throws Exception if there is any error during the decryption process
     */
    void decryptPWTest2() throws Exception {
        result = AES.decryptPW("AyA0tDtxgdEU40He4KDz4A==");
        assertEquals("CS2212",result);
    }


    @Test
    /** This is a JUnit test method to verify that the decryptPW() method in the AES class throws an exception
     * when passed a null value as the encrypted password parameter.
     * @throws Exception if there is any error during the decryption process
     */
    void decryptPWTest3() {
        assertThrows(Exception.class,
                () -> {
                    Text = AES.decryptPW(null);
                });
    }

}