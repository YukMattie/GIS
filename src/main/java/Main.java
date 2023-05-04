import java.sql.*;
import java.util.ArrayList;

/**
 * This class represents the main class of the application,
 * which is responsible for connecting to the database,
 * executing queries and initializing the different ArrayLists used in the program.
 * @auther  Yi Xiao
 * @version 1.0
 */
public class Main {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private static Connection conn;
    private Statement stmt = null;
    private PreparedStatement ps = null;
    private static ResultSet rsPOI;
    private static ResultSet rsBuilding;
    private static ResultSet rsLogin;
    private static String POISQL = "SELECT * FROM poi ";
    private static String buildingSQL = "SELECT Name,Floor FROM building";
    private static String loginSQL = "SELECT UserName,PassWord,OperatorType FROM login";
    public static ArrayList<POI> allPOI;
    public static ArrayList<building> allBuildings;
    public static ArrayList<users> allUsers= new ArrayList<>();
    /**
     * The main method of the program.
     * @param args The command-line arguments for the program.
     * @throws SQLException If there's an error in the SQL query execution.
     * @throws ClassNotFoundException If the JDBC driver class cannot be found.
     */

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        allPOI = dbPOI();
        allBuildings = dbBuilding();
        allUsers = dbUsers();
        login userLogin = new login();
    }
    /**
     * This method connects to the database and retrieves all the Points of Interest (POIs) from the 'poi' table.
     * @return An ArrayList of POI objects.
     * @throws ClassNotFoundException If the JDBC driver class cannot be found.
     * @throws SQLException If there's an error in the SQL query execution.
     */
    public static ArrayList<POI> dbPOI() throws ClassNotFoundException, SQLException {
        ArrayList<POI> temp = new ArrayList<>();

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        rsPOI = stmt.executeQuery(POISQL);
        while (rsPOI.next()) {
            POI thisPOI = new POI(rsPOI);
            temp.add(thisPOI);

        }
        rsPOI.close();
        conn.close();
        stmt.close();

        return temp;
    }

    /**
     * This method connects to the database and retrieves all the buildings' names and floors from the 'building' table.
     * @return An ArrayList of building objects.
     * @throws ClassNotFoundException If the JDBC driver class cannot be found.
     * @throws SQLException If there's an error in the SQL query execution.
     */
    public static ArrayList<building> dbBuilding() throws ClassNotFoundException, SQLException {
        ArrayList<building> temp = new ArrayList<>();
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        rsBuilding = stmt.executeQuery(buildingSQL);
        while (rsBuilding.next()) {
            building thisBuilding = new building(rsBuilding);
            temp.add(thisBuilding);

        }

        rsBuilding.close();
        conn.close();
        stmt.close();
        return temp;



    }


/**
 * This method connects to the database and retrieves all the user credentials from the 'login' table.
 * @return An ArrayList of users objects.
 * @throws ClassNotFoundException If the JDBC driver class cannot be found.
 *  @throws SQLException If there's an error in the SQL query execution.
 */
    public static ArrayList<users> dbUsers()
            throws ClassNotFoundException, SQLException {
        ArrayList<users> temp = new ArrayList<>();
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();

        rsLogin = stmt.executeQuery(loginSQL);
        while (rsLogin.next()) {
            users thisUser = new users(rsLogin);
            temp.add(thisUser);
        }

        rsLogin.close();
        conn.close();
        stmt.close();

        return temp;

    }


}
