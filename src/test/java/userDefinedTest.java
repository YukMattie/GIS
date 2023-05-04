import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The userDefinedTest class contains JUnit test methods for the userDefined class.
 */
class userDefinedTest {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS ="CS2212";
    private PreparedStatement ps=null;
    private static Connection conn;
    private Statement stmt = null;


    private String insert="INSERT INTO usercreated VALUES(?,?,?,?,?,?,?,?,?)";
    private static String selectDefSQL = "SELECT * FROM usercreated WHERE userAccount = ";
    private static ResultSet rsUserCreated;
    private static String userCreatedSQL = "SELECT * FROM usercreated ";
    private static ResultSet rsDefinePOI;
    private ArrayList<POI> testCreated = new ArrayList<>();
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
        add = new POI(1, 1,"TestDefineRoom","TestDefineNumber","DefineClassroom","TestDefineBuilding",0,"Test Define Description");
    }

    @Test
    /** This is a JUnit method that sets up the database connection and creates a POI object
     * to be used for testing the addPOI() method in the Main class.
     * @throws SQLException if there is an error while connecting to the database
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     */
    void addUserDefinedTest() throws SQLException, ClassNotFoundException {
        userDefined ud = new userDefined();
        assertAll(() -> ud.addUserDefined(add));
    }

    @Test
    /** This is a JUnit method to test the deleteCreate() method in the userDefined class.
     * It creates a userDefined object and uses it to delete a POI from the database, then adds it back.
     * The test passes if the POI can be successfully deleted and re-added without any errors.
     * @throws SQLException if there is an error while interacting with the database
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     */
    void deleteCreateTest() throws SQLException, ClassNotFoundException {
        userDefined ud = new userDefined();
        assertAll(() -> ud.deleteCreate(add));
    }

    @Test
    /** This is a JUnit method to test the DBDefPOI() method in the userDefined class.
     * It creates a userDefined object and uses it to retrieve a list of default POIs from the database.
     * The test passes if the list is not null.
     * @throws SQLException if there is an error while interacting with the database
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     */
    void DBDefPOITest() throws SQLException, ClassNotFoundException {
        userDefined ud = new userDefined();
        testCreated = ud.DBDefPOI();
        assertNotNull(testCreated);
    }


    @AfterEach
    /** This is a JUnit method that is run after each test method in the class.
     * It is used to disconnect from the database by closing the connection and statement objects.
     * @throws SQLException if there is an error while interacting with the database
     */
    void disconnect() throws SQLException {
        conn.close();
        stmt.close();
    }
}