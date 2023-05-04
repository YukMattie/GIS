import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * The usersTest class contains JUnit test methods for the users class.
 */
class usersTest {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private Statement stmt = null;
    private static Connection conn;
    private static String usersSQL = "SELECT UserName,PassWord,OperatorType FROM login";

    private ResultSet resultSet;
    private users user = null;


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
        resultSet = stmt.executeQuery(usersSQL);

    }

    @Test
    void testConstructor() {
        users user = new users("testuser", "testpassword", "testtype");
        assertEquals("testuser", user.getUsername());
        assertEquals("testpassword", user.getPassword());
        assertEquals("testtype", user.getOperatorType());
    }

    @Test
    /** JUnit test for users class method getUsername().
     *  @throws SQLException if there is an error with the SQL query or connection.
     */
    void GetUserNameTest() throws SQLException {
        if(resultSet.next()){
            user = new users(resultSet);
            assertEquals("Alex@uwo.ca", user.getUsername());
        }
    }


    @Test
    /** JUnit test for users class method getPassword().
     * @throws SQLException if there is an error with the SQL query or connection.
     */
    void testPassword() throws SQLException {
        if(resultSet.next()){
            user = new users(resultSet);
            assertNotNull(user.getPassword());
        }
    }


    @Test
    /** JUnit test for users class method getOperatorType().
     * @throws SQLException if there is an error with the SQL query or connection.
     */
    void testType() throws SQLException {
        if (resultSet.next()){
            user = new users(resultSet);
            assertEquals("Developer", user.getOperatorType());
        }
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