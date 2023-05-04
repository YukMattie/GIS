import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The builtInManageTest class contains JUnit test methods for the builtInManage class.
 */
class builtInManageTest {


    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private Statement stmt = null;
    private static Connection conn;

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
        add = new POI(1, 1,"TestBuiltInRoom","TestBuiltInNumber","BuiltInClassroom","TestBuiltInBuilding",0,"Test Define Description");

    }

    @Test
    /** Tests the deleteBuiltIn() method of the builtInManage class.
     * Creates a new instance of the builtInManage class and passes in an address.
     * The method then calls the deleteBuiltIn() method of the builtInManage class with the same address as an argument.
     * The assertAll() method is then used to assert that the deleteBuiltIn() method was successful
     * and throw no exception.*/
    void deleteBuiltIn() {
        builtInManage bm = new builtInManage(add);
        assertAll(() -> bm.deleteBuiltIn(add));
    }

    @Test
    /** Tests the deleteBuiltInFav() method of the builtInManage class.
     * Creates a new instance of the builtInManage class and passes in an address.
     * The method then calls the deleteBuiltInFav() method of the builtInManage class with the same address as an argument.
     * The assertAll() method is then used to assert that the deleteBuiltInFav() method was successful.*/
    void deleteBuiltInFav() {
        builtInManage bm = new builtInManage(add);
        assertAll(() -> bm.deleteBuiltIn(add));
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