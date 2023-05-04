import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


/**
 * The UserFavoriteTest class contains JUnit test methods for the userFavorite class.
 */
class userFavoriteTest {


    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private Statement stmt = null;
    private static Connection conn;
    private static String FavSQL = "SELECT * FROM userfavourite";

    private ResultSet resultSet;
    private ArrayList<POI> testAL = new ArrayList<>();
    private userFavorite fav;
    private POI add;



    @BeforeEach
    /** This is a JUnit test setup method that is executed before each test method in the class.
     * It creates a SQL connection object and Statement object for the test to use.
     * @throws SQLException if there is an error with the SQL connection or statement object
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     */
    void connectDB() throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
        resultSet = stmt.executeQuery(FavSQL);
        add = new POI(1, 1,"TestRoom","TestNumber","Classroom","TestBuilding",0,"Test Description");

    }

    @Test
    /** JUnit test for userFavorite class method DBFavPOI().
     * @throws SQLException if there is an error with the SQL query or connection.
     * @throws ClassNotFoundException if the MySQL driver class cannot be found.
     */
    void DBFavPOITest() throws SQLException, ClassNotFoundException {
        fav = new userFavorite();
        testAL = fav.DBFavPOI();
        assertNotNull(testAL);
    }

    @Test
    /** JUnit test for userFavorite class method addFavroite(String poiName).
     * @throws SQLException if there is an error with the SQL query or connection.
     * @throws ClassNotFoundException if the MySQL driver class cannot be found.
     */
    void addFavroiteTest() throws SQLException, ClassNotFoundException {
        fav = new userFavorite();
        assertAll(() -> fav.addFavroite(add));

    }

    @Test
    /** JUnit test for userFavorite class method deleteFav(String poiName).
     * @throws SQLException if there is an error with the SQL query or connection.
     * @throws ClassNotFoundException if the MySQL driver class cannot be found.
     */
    void deleteFavTest() throws SQLException, ClassNotFoundException {
        fav = new userFavorite();
        assertAll(() -> fav.deleteFav(add));
    }



    @AfterEach
    /** This is a JUnit method that is run after each test method in the class.
     * It is used to disconnect from the database by closing the connection and statement objects.
     * @throws SQLException if there is an error while interacting with the database
     */
    void disconnect() throws SQLException {
        resultSet.close();
        conn.close();
        stmt.close();
    }
}