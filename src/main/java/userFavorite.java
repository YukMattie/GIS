import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
/**
 *  This class represents the user's favorite point of interest (POI).
 *  It connects to a MySQL database and retrieves user's favorite POI.
 *  It also allows the user to add and delete POIs from their favorites.
 *  @author Yuting Pan
 *  @version 1.0
 */

public class userFavorite {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS ="CS2212";
    private static Connection conn;
    private PreparedStatement ps=null;
    private String insertSQL="INSERT INTO userFavourite VALUES(?,?,?,?,?,?,?,?,?)";
    private static String selectFavSQL = "SELECT * FROM userfavourite WHERE userAccount = ";
    private ArrayList<POI> userFavoritePOI;
    private static ResultSet rsFavoritePOI;
    private JMenu fav;
    /**
     * Constructor for the userFavorite class.
     * Retrieves the user's favorite POIs from the database.
     * @throws SQLException if there is a problem with the database connection
     * @throws ClassNotFoundException if the JDBC driver is not found
     */

    public userFavorite() throws SQLException, ClassNotFoundException {
        this.userFavoritePOI = DBFavPOI();
        fav = new JMenu("Favorite");
    }

    /**
     * Returns the JMenu object containing the user's favorite POIs.
     * @return JMenu object containing the user's favorite POIs
     */
    public JMenu getUserFavorite(){

        for(POI eachFav: userFavoritePOI){
            // building of this favorite item is not in the menu
            if(haveThisBuildingItem(eachFav.getBuilding()) == false){
                JMenu tempBuilding = new JMenu(eachFav.getBuilding());
                JMenu tempFloor = new JMenu("Floor" + eachFav.getFloor());
                JMenuItem tempPOI = new JMenuItem(eachFav.getName());
                tempFloor.add(tempPOI);
                tempBuilding.add(tempFloor);
                fav.add(tempBuilding);
            }
            else{
                // have this building item in menu bar
                String tempBuilding = eachFav.getBuilding();
                if(haveThisFloorItem(tempBuilding,eachFav.getFloor()) == false){
                    for(int i = 0; i < fav.getItemCount(); i++){
                        if(fav.getItem(i).getText().equals(tempBuilding)){
                            JMenuItem tempPOI = new JMenuItem(eachFav.getName());
                            JMenu tempFloor = new JMenu("Floor" + eachFav.getFloor());
                            tempFloor.add(tempPOI);

                            fav.getItem(i).add(tempFloor);
                        }
                    }
                }
                // have this building and floor in menu bar
                else{
                    for(int i = 0; i < fav.getItemCount(); i++){
                        if(fav.getItem(i).getText().equals(tempBuilding)){
                            JMenu temp = (JMenu) fav.getItem(i);
                            for(int j = 0; j < temp.getItemCount(); j++){
                                if(temp.getItem(j).getText().equals("Floor" + eachFav.getFloor())){
                                    JMenuItem tempPOI = new JMenuItem(eachFav.getName());
                                    temp.getItem(j).add(tempPOI);
                                }
                            }
                        }
                    }

                }
            }

        }
        return fav;
    }


    /** find favorite string array of this user */
    private String[] findFavoriteArray(){
        ArrayList<String> temp = new ArrayList<>();
        for(POI eachFav: userFavoritePOI){
            temp.add(eachFav.getName());
        }
        String[] result = temp.toArray(new String[0]);
        return result;
    }

    /**
     * Checks if the JMenu object already contains an item for the given building.
     * @param thisBuilding String representing the building to be checked
     * @return true if the JMenu object contains an item for the given building, false otherwise
     */
    private boolean haveThisBuildingItem(String thisBuilding){
        if(fav.getItemCount() > 0){
            for(int i = 0; i < fav.getItemCount();i++){
                JMenuItem temp = fav.getItem(i);
                if(thisBuilding.equals(fav.getItem(i).getText())){
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }

    /**
     * Checks if the JMenu object already contains an item for the given building and floor.
     * @param thisBuilding String representing the building to be checked
     * @param floor int representing the floor to be checked
     * @return true if the JMenu object contains an item for the given building and floor, false otherwise
     */
    private boolean haveThisFloorItem(String thisBuilding,int floor){
        for(int i = 0; i < fav.getItemCount(); i++){
            if(fav.getItem(i).getText().equals(thisBuilding)){
                JMenu temp = (JMenu) fav.getItem(i);
                for(int j = 0; j < temp.getItemCount(); j++){
                    if(temp.getItem(j).getText().equals("Floor" + floor)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Retrieves the user's favorite POIs from the database and returns an ArrayList of POI objects.
     * @return ArrayList of POI objects representing the user's favorite POIs
     * @throws SQLException if there is a problem with the database connection
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public ArrayList<POI> DBFavPOI() throws SQLException, ClassNotFoundException {
        ArrayList<POI> temp = new ArrayList<>();
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        String query = selectFavSQL + "'" + login.thisUsername + "'";
        rsFavoritePOI = stmt.executeQuery(query);

        while (rsFavoritePOI.next()) {
            POI thisFav = new POI(rsFavoritePOI);
            temp.add(thisFav);
        }

        rsFavoritePOI.close();
        conn.close();
        stmt.close();

        return temp;
    }

    /**
     * Adds a POI to the user's favorites in the database.
     * @param temp POI object to be added to the user's favorites
     * @throws SQLException if there is a problem with the database connection
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void addFavroite(POI temp)throws ClassNotFoundException,SQLException{

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt=null;
        stmt = conn.createStatement();
        ps=conn.prepareStatement(insertSQL);
        ps.setInt(1,temp.getX());
        ps.setInt(2,temp.getY());
        ps.setString(3,temp.getName());
        ps.setString(4,temp.getRoomNumber());
        ps.setString(5,temp.getType());
        ps.setString(6,temp.getBuilding());
        ps.setInt(7,temp.getFloor());
        ps.setString(8,temp.getDescription());
        ps.setString(9,login.thisUsername);

        ps.execute();
        ps.close();
        stmt.close();
        conn.close();

    }
    /**
     * Delete a POI from the user's favorites in the database.
     * @param temp Temporary POI object to be delete from the user's favorites
     * @throws SQLException if there is a problem with the database connection
     * @throws ClassNotFoundException if the JDBC driver is not found
     */
    public void deleteFav(POI temp)throws ClassNotFoundException,SQLException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        Statement stmt=null;
        stmt = conn.createStatement();
        String query = "DELETE FROM userfavourite WHERE Xcoordinate ="+"'"+temp.getX()+"'"+"AND Ycoordinate ="+"'"+temp.getY()+"'"+"AND RoomNumber ="+"'"+temp.getRoomNumber()+"'"+"AND Type ="+"'"+temp.getType()+"'"+"AND building ="+"'"+temp.getBuilding()+"'"+"AND Floor ="+"'"+temp.getFloor()+"'"+"AND userAccount ="+"'"+login.thisUsername+"'";
        stmt.executeUpdate(query);
//        ps.close();

    }

}

