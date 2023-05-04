import org.junit.jupiter.api.Test;

import java.net.URL;
import java.net.URLConnection;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


/**
 * The weatherTest class contains JUnit test methods for the weather class.
 */
class weatherTest {

    private boolean internet = false;

    /** This test is testing the weather Class when have no internet
     *  if internet connect, it will not go to  assertThrows function
     *  if no internet, assertThrows should catch the exception the weather throws
     *  and will pass the test*/
    @Test
    void NoInternet(){

        try {
            URL url = new URL("https://google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();
            internet = true;
        }
        catch (Exception e) {
            assertThrows(Exception.class,
                    () -> {
                        weather testWeather = new weather();
                    });
            System.out.println("No Internet connection.");
        }
    }


    @Test
    /** This JUnit test for weather class method getImage().
     * It should return a url which is not null*/
    void getImage() throws Exception {
        if(internet){
            weather testWeather = new weather();
            assertNotNull(testWeather.getImage());
        }

    }



    @Test
    /** This JUnit test for weather class method getTemp().
     * It should return a temperature which is not null*/
    void getTemp() throws Exception {
        if(internet){
            weather testWeather = new weather();
            assertNotNull(testWeather.getTemp());
        }

    }

}