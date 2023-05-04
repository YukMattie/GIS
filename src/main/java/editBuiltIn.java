import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

/**
 * The editBuiltIn class allows the user to edit a point of interest (POI) that is built into the system.
 * It includes the graphical user interface (GUI) for the edit screen and the functionality to save changes made to the POI.
 * The class implements the ActionListener interface to handle button clicks and uses the POI class to store the information
 * of the POI being edited. It also uses the developerMode class to display messages and the java.sql package to interact with the database.
 * The GUI includes labels for the building, floor, name, room number, type, and description of the POI.
 * It also includes text fields and a combo box for the user to enter or select information, and a button to save changes made to the POI.
 * @author Yuting Pan
 * @version 1.0
 */

public class editBuiltIn implements ActionListener{
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
    private developerMode currentDM;
    private static Connection conn;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";
    private static String builtInSQL = "SELECT * FROM poi";
    private String updateSQL=null;
    private PreparedStatement ps=null;
    /**
     * Constructor for the editBuiltIn class. Initializes the GUI for the edit screen with labels, text fields, and a combo box to display and edit the POI information.
     * @param a The developerMode object that is used to display messages.
     * @param editTemp The POI object that is being edited.
     * @param tempBuilding A string representing the building that the POI is in.
     * @param tempFloor An integer representing the floor that the POI is on.
     */
    public editBuiltIn(developerMode a,POI editTemp,String tempBuilding,int tempFloor){

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
        currentDM=a;


    }
    /**
     * Handles the action performed when the "Save" button is clicked.
     * @param e The action event triggered by the button click.
     */
    public void actionPerformed(ActionEvent e) {

        try {
            edit(tempEdit);
            editFav(tempEdit);

        } catch (Exception a){

        }
        POI finishEdit=new POI(tempEdit.getX(),tempEdit.getY(),nameText.getText(),numberText.getText(),typeText.getSelectedItem().toString(),tempEdit.getBuilding(),tempEdit.getFloor(),descriptionText.getText());
        Main.allPOI.remove(tempEdit);
        Main.allPOI.add(finishEdit);
        developerMode.allPOIInThisMap.removeItem(tempEdit.getName());
        developerMode.allPOIInThisMap.addItem(finishEdit.getName());
        currentDM.showMessage(finishEdit.getX()-30,finishEdit.getY()-35);

        frame.dispose();

    }


    /**
     * Updates the database with the changes made to the POI.
     * @param a The POI object being edited.
     * @throws SQLException If there is an error with the SQL statement or connection.
     * @throws ClassNotFoundException If the JDBC driver cannot be found.
     */
    private void edit(POI a)throws SQLException, ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        updateSQL = "UPDATE poi SET Name="+"'"+nameText.getText()+"'"+",RoomNumber ="+"'"+numberText.getText()+"'"+",Type ="+"'"+typeText.getSelectedItem().toString()+"'"+",Description ="+"'"+descriptionText.getText()+"'"+"WHERE building =" + "'" + a.getBuilding() + "'" + "AND Floor =" + "'" + a.getFloor() + "'" + "AND Name =" + "'" + a.getName() + "'";
        ps = conn.prepareStatement(updateSQL);
        ps.executeUpdate();
        ps.close();
        stmt.close();
        conn.close();
    }

    /**
     * Updates the userfavourite table in the database with the changes made to the POI.
     * @param a The POI object being edited.
     * @throws SQLException If there is an error with the SQL statement or connection.
     * @throws ClassNotFoundException If the JDBC driver cannot be found.
     */
    private void editFav(POI a)throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        updateSQL = "UPDATE userfavourite SET Name="+"'"+nameText.getText()+"'"+",RoomNumber ="+"'"+numberText.getText()+"'"+",Type ="+"'"+typeText.getSelectedItem().toString()+"'"+",Description ="+"'"+descriptionText.getText()+"'"+"WHERE building =" + "'" + a.getBuilding() + "'" + "AND Floor =" + "'" + a.getFloor() + "'" + "AND Name =" + "'" + a.getName() + "'";
        ps = conn.prepareStatement(updateSQL);
        ps.executeUpdate();
        ps.close();
        stmt.close();
        conn.close();

    }

}
