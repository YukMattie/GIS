import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/**
 * This class represents a GUI for editing a user-defined POI. It extends the ActionListener class
 * to listen for button clicks and handle them appropriately.
 * @author Yi Xiao
 * @version 1.0
 */
public class editUserDefined implements ActionListener{
    private JFrame frame;
    private JPanel panel;

    private JLabel building;

    private JLabel floor;

    private JLabel name;

    private JLabel number;

    private JLabel type;

    private JLabel description;

    private JLabel buildingText;

    private JLabel floorText;

    private JTextField nameText;

    private JTextField numberText;

    private JComboBox typeText;

    private JTextArea descriptionText;

    private  JButton edit;

    private POI tempEdit;

    private viewMap currentView;

    private static Connection conn;

    private static Connection conn1;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private String updateSQL=null;
    private PreparedStatement ps=null;
    /**
     * Constructor for the editUserDefined class. Initializes the GUI components and displays the frame.
     *
     * @param a The viewMap object that this GUI is associated with.
     * @param editTemp The POI object that is being edited.
     * @param tempBuilding The building of the POI being edited.
     * @param tempFloor The floor of the POI being edited.
     */

    public editUserDefined(viewMap a,POI editTemp,String tempBuilding,int tempFloor){

        Dimension dim=Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(500,500);
        frame.setTitle("User Defined POI");
        frame.setLocation(dim.width/2-frame.getSize().width/2,dim.height/2-frame.getSize().height/2);
        frame.add(panel);
        frame.setResizable(false);
        panel.setLayout(null);
        frame.setVisible(true);

        building=new JLabel("Building");
        building.setBounds(10,20,100,25);
        floor = new JLabel("Floor");
        floor.setBounds(10,80,100,25);
        name = new JLabel("Name");
        name.setBounds(10,140,100,25);
        number=new JLabel("Room Number");
        number.setBounds(10,200,100,25);
        type=new JLabel("Type");
        type.setBounds(10,260,100,25);
        description=new JLabel("Description");
        description.setBounds(10,320,100,25);
        panel.add(building);
        panel.add(floor);
        panel.add(name);
        panel.add(number);
        panel.add(type);
        panel.add(description);

        buildingText = new JLabel();
        buildingText.setBounds(120,20,200,25);
        buildingText.setText(tempBuilding);
        floorText=new JLabel();
        floorText.setBounds(120,80,200,25);
        floorText.setText(Integer.toString(tempFloor));
        nameText=new JTextField();
        nameText.setBounds(120,140,200,25);
        nameText.setText(editTemp.getName());
        numberText=new JTextField();
        numberText.setBounds(120,200,200,25);
        numberText.setText(editTemp.getRoomNumber());
        typeText=new JComboBox<>();
        typeText.setBounds(120,260,200,25);
        typeText.addItem("Classroom");
        typeText.addItem("Lab");
        typeText.addItem("Restaurant");
        typeText.addItem("Collaborative Room");
        typeText.addItem("Other");
        typeText.setSelectedItem(editTemp.getType());
        descriptionText=new JTextArea();
        descriptionText.setLineWrap(true);
        descriptionText.setBorder(new LineBorder(Color.black,2));
        descriptionText.setBounds(120,320,300,100);
        descriptionText.setText(editTemp.getDescription());
        panel.add(buildingText);
        panel.add(floorText);
        panel.add(nameText);
        panel.add(numberText);
        panel.add(typeText);
        panel.add(descriptionText);

        edit = new JButton("Save");
        edit.setBounds(200,430,80,25);
        edit.addActionListener(this);
        panel.add(edit);
        tempEdit=editTemp;
        currentView=a;


    }

    /**
     * Handles the user's click on the Save button by updating the database with the edited POI's information.
     * Also updates the viewMap object with the edited POI and refreshes the view.
     *
     * @param e The ActionEvent object representing the user's click on the Save button.
     */
    public void actionPerformed(ActionEvent e) {

        try {
            editDef(tempEdit);

        } catch (Exception a){

        }
        POI finishEdit=new POI(tempEdit.getX(),tempEdit.getY(),nameText.getText(),numberText.getText(),typeText.getSelectedItem().toString(),tempEdit.getBuilding(),tempEdit.getFloor(),descriptionText.getText());
        viewMap.allUserCreated.remove(tempEdit);
        viewMap.allUserCreated.add(finishEdit);
        viewMap.allPOIInThisMap.removeItem(tempEdit.getName());
        viewMap.allPOIInThisMap.addItem(finishEdit.getName());
        viewMap.poiInSearch = finishEdit;
        currentView.showMessage(finishEdit.getX()-30,finishEdit.getY()-35);

        for(int i=0;i<viewMap.allFavourite.size();i++)
        {
            if(viewMap.allFavourite.get(i).checkEqual(viewMap.allFavourite.get(i),tempEdit)){
                try{
                     editFav(tempEdit);
                      viewMap.allFavourite=viewMap.thisUserFavorite.DBFavPOI();
                      currentView.refreshFavorite();
                }
                catch (Exception f){

                }

            }
        }

        frame.dispose();

    }
    /**
     * Updates the database with the edited POI's information.
     *
     * @param a The POI object being edited.
     */
    private void editDef(POI a)throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        updateSQL = "UPDATE usercreated SET Name="+"'"+nameText.getText()+"'"+",RoomNumber ="+"'"+numberText.getText()+"'"+",Type ="+"'"+typeText.getSelectedItem().toString()+"'"+",Description ="+"'"+descriptionText.getText()+"'"+"WHERE building =" + "'" + a.getBuilding() + "'" + "AND Floor =" + "'" + a.getFloor() + "'" + "AND Name =" + "'" + a.getName() + "'"+"AND userAccount ="+"'"+login.thisUsername + "'";
        ps = conn.prepareStatement(updateSQL);
        ps.executeUpdate();
        ps.close();
        stmt.close();
        conn.close();
    }
    /**
     * Updates the database with the edited POI's information in the user's favorites.
     *
     * @param a The POI object being edited.
     */
    private void editFav(POI a)throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        updateSQL = "UPDATE userfavourite SET Name="+"'"+nameText.getText()+"'"+",RoomNumber ="+"'"+numberText.getText()+"'"+",Type ="+"'"+typeText.getSelectedItem().toString()+"'"+",Description ="+"'"+descriptionText.getText()+"'"+"WHERE building =" + "'" + a.getBuilding() + "'" + "AND Floor =" + "'" + a.getFloor() + "'" + "AND Name =" + "'" + a.getName() + "'"+"AND userAccount ="+"'"+login.thisUsername + "'";
        ps = conn.prepareStatement(updateSQL);
        ps.executeUpdate();
        ps.close();
        stmt.close();
        conn.close();

    }

}
