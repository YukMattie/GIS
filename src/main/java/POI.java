import java.sql.*;

/**
 * The POI class represents a POI with Xcoordinate, Ycoordinate, name, RoomNumber, Type, building, floor, description
 * @anthor Yi Xiao
 * @version 1.0
 */
public class POI {
    private int Xcoordinate;
    private int Ycoordinate;
    private String name;
    private String RoomNumber;
    private String Type;
    private String building;
    private int floor;
    private String description;

    /**
     * Constructs a POI object using a ResultSet.
     * @param rs the ResultSet used to initialize the POI object
     * @throws ClassNotFoundException if the JDBC driver class is not found
     * @throws SQLException if a database access error occurs
     */

    public POI(ResultSet rs) throws ClassNotFoundException, SQLException{
        this.Xcoordinate = rs.getInt("Xcoordinate");
        this.Ycoordinate = rs.getInt("Ycoordinate");
        this.name = rs.getString("Name");
        this.RoomNumber = rs.getString("RoomNumber");
        this.Type = rs.getString("Type");
        this.building = rs.getString("building");
        this.floor = rs.getInt("Floor");
        this.description = rs.getString("description");


/**
 * Constructs a POI object using individual attributes.
 * @param newX the X coordinate of the POI
 * @param newY the Y coordinate of the POI
 * @param newName the name of the POI
 * @param newNumber the room number of the POI
 * @param newType the type of the POI
 * @param newBuilding the building of the POI
 * @param newFloor the floor of the POI
 * @param newDescription the description of the POI
 */
    }
    public POI(int newX,int newY,String newName,String newNumber,String newType,String newBuilding,int newFloor,String newDescription)
    {
        this.Xcoordinate = newX;
        this.Ycoordinate=newY;
        this.name=newName;
        this.RoomNumber = newNumber;
        this.Type = newType;
        this.building=newBuilding;
        this.floor=newFloor;
        this.description=newDescription;
    }

    /**
     * Returns the X coordinate of the POI.
     * @return the X coordinate of the POI
     */
    public int getX(){
        return this.Xcoordinate;
    }
    /**
     * Returns the Y coordinate of the POI.
     * @return the Y coordinate of the POI
     */
    public int getY(){
        return this.Ycoordinate;
    }
    /**
     * Returns the name of the POI.
     * @return the name of the POI
     */
    public String getName(){
        return this.name;
    }
    /**
     * Returns the room number of the POI.
     * @return the room number of the POI
     */
    public String getRoomNumber(){
        return this.RoomNumber;
    }
    /**
     * Returns the type of the POI.
     * @return the type of the POI
     */
    public String getType(){
        return this.Type;
    }

    /**
     * Returns the building of the POI.
     * @return the building of the POI
     */
    public String getBuilding(){
        return this.building;
    }
    /**
     * Returns the floor of the POI.
     * @return the floor of the POI
     */
    public int getFloor(){
        return this.floor;
    }
    /**
     * Returns the description of the POI.
     * @return the description of the POI
     */
    public String getDescription(){
        return this.description;
    }
    /**
     * Checks if two POI objects are equal by comparing their attributes.
     * @param A the first POI object
     * @param B the second POI object
     * @return true if the two POI objects are equal, false otherwise
     */
    public Boolean checkEqual(POI A,POI B)
    {
        if(A.getX()==B.getX()&&A.getY()==B.getY()&&A.getName().equals(B.getName())&&A.getRoomNumber().equals(B.getRoomNumber())&&A.getType().equals(B.getType())&&A.getFloor()==B.getFloor()&&A.getBuilding().equals(B.getBuilding())&&A.getDescription().equals(B.getDescription()))
        {
            return true;
        }
        else
            return false;
    }

}
