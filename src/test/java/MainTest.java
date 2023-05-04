import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The MainTest class contains JUnit test methods for the Main class.
 */
class MainTest {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS ="CS2212";
    private PreparedStatement ps=null;
    private static Connection conn;
    private Statement stmt = null;
    private ArrayList<POI> testPOI = new ArrayList<>();
    private ArrayList<building> testBuilding = new ArrayList<>();
    private ArrayList<users> testUser = new ArrayList<>();


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
    }

    @Test
    /** This is a JUnit test method to verify that the dbPOI() method in the Main class returns a non-null POI object.
     * @throws SQLException if there is an error with the SQL query or result set
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     */
    void dbPOITest() throws SQLException, ClassNotFoundException {
        testPOI = Main.dbPOI();
        assertNotNull(testPOI);
    }


    @Test
    /** This is a JUnit test method to verify that the dbBuilding() method in the Main class
     * returns a non-null building object.
     * @throws SQLException if there is an error with the SQL query or result set
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     */
    void dbBuildingTest() throws SQLException, ClassNotFoundException {
        testBuilding = Main.dbBuilding();
        assertNotNull(testBuilding);
    }


    @Test
    /** This is a JUnit test method to verify that the dbUsers() method in the Main class
     * returns a non-null User object.
     * @throws SQLException if there is an error with the SQL query or result set
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     */
    void dbUsersTest() throws SQLException, ClassNotFoundException {
        testUser = Main.dbUsers();
        assertNotNull(testUser);
    }

    @AfterEach
    /** This is a JUnit method to disconnect from the database after each test.
     * @throws SQLException if there is an error while disconnecting from the database
     */
    void disconnect() throws SQLException {
        conn.close();
        stmt.close();
    }
}