import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The users class represents a user with a username, password and operator type
 * @anthor Yi Xiao
 * @version 1.0
 */
public class users {

    private String UserName;
    private String PassWord;
    private String OperatorType;
    /**
     * Constructs a new user object by retrieving data from a ResultSet object
     *
     * @param rs the ResultSet object to retrieve data from
     * @throws SQLException if a database access error occurs or the column label is not valid
     */

    public users(ResultSet rs) throws SQLException {
        this.UserName = rs.getString("UserName");
        this.PassWord = rs.getString("PassWord");
        this.OperatorType = rs.getString("OperatorType");
    }
    /**
     * Constructs a new user object with a given username, password and operator type
     *
     * @param name the username of the user
     * @param password the password of the user
     * @param type the operator type of the user
     */
    public users(String name,String password,String type){
        UserName=name;
        PassWord=password;
        OperatorType = type;
    }
    /**
     * Returns the username of the user
     *
     * @return the username of the user
     */
    public String getUsername(){
        return this.UserName;
    }
    /**
     * Returns the password of the user
     *
     * @return the password of the user
     */
    public String getPassword(){
        return this.PassWord;
    }
    /**
     * Returns the operator type of the user
     *
     * @return the operator type of the user
     */
    public String getOperatorType (){
        return this.OperatorType;
    }
}
