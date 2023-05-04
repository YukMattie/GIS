import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The building class represents a building object that has a name and floor.
 * It can be constructed by taking a ResultSet object as a parameter.
 * @author Yuting Pan
 * @version 1.0
 */
public class building {
    /**
     * The name of the building.
     */
    private String name;
    /**
     * The number of floors in the building.
     */
    private int floor;
    /**
     * Constructs a building object using the data from the ResultSet object.
     *
     * @param building a ResultSet object containing the building data
     * @throws SQLException if there is an error accessing the ResultSet
     */
    public building(ResultSet building) throws SQLException {
        this.name = building.getString("Name");
        this.floor = building.getInt("Floor");
    }
    /**
     * Returns the name of the building.
     *
     * @return the name of the building
     */
    public String getName(){
        return this.name;
    }
    /**
     * Returns the number of floors in the building.
     *
     * @return the number of floors in the building
     */
    public int getFloor(){
        return this.floor;
    }
}
