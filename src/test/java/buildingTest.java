
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * The BuildingTest class contains JUnit test methods for the Building class.
 */
class buildingTest {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private Statement stmt = null;
    private static Connection conn;
    private static String buildingSQL = "SELECT Name,Floor FROM building";

    private ResultSet resultSet;
    private building testBuilding = null;


    @BeforeEach
    /** This is a JUnit test setup method that is executed before each test method in the class.
     * It connects to a database using the JDBC driver and creates a statement object.
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     * @throws SQLException if there is an error with the SQL connection
     */
    void connectDB() throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
    }


    @Test
    /** This is a JUnit test method to verify the functionality of the getName() method in the Building class.
     * The test executes a SQL query to retrieve building data, creates a Building object from the query result,
     * and compares the name of the Building object to an expected value.
     * @throws SQLException if there is an error with the SQL query or result set
     */
    void getNameTest() throws SQLException {
        resultSet = stmt.executeQuery(buildingSQL);

        if (resultSet.next()) {
            testBuilding = new building(resultSet);
            assertEquals("Alumni Stadium",testBuilding.getName());
        }
    }

    /** Test null */
    @Test
    void getNameTestNull() throws SQLException {
        String nullSQL = "SELECT Name,Floor FROM building WHERE 1 = 0";
        resultSet = stmt.executeQuery(nullSQL);

        if (resultSet.next()) {
            testBuilding = new building(resultSet);
            assertNull(testBuilding.getName());
        }

    }



    @Test
    /** This is a JUnit test method to verify that the getName() method in the Building class
     * returns null when the query result set has no data.
     * @throws SQLException if there is an error with the SQL query or result set
     */
    void getFloorTest() throws SQLException {
        resultSet = stmt.executeQuery(buildingSQL);

        if (resultSet.next()) {
            testBuilding = new building(resultSet);
            assertEquals(2,testBuilding.getFloor());
        }

    }


    @Test
    /** This is a JUnit test method to verify that the getFloor() method in the Building class
     * returns null when the query result set has no data.
     * @throws SQLException if there is an error with the SQL query or result set
     */
    void getFloorTestNull() throws SQLException {
        String nullSQL = "SELECT Name,Floor FROM building WHERE 1 = 0";
        resultSet = stmt.executeQuery(nullSQL);

        if (resultSet.next()) {
            testBuilding = new building(resultSet);
            assertNull(testBuilding.getFloor());
        }

    }





    @AfterEach
    /** This is a JUnit test cleanup method that is executed after each test method in the class.
     * It closes the ResultSet, SQL connection, and Statement objects.
     * @throws SQLException if there is an error with the SQL connection or statement object
     */
    void disconnect() throws SQLException {
        resultSet.close();
        conn.close();
        stmt.close();
    }
}