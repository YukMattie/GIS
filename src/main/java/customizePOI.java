import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * The customizePOI class allows users to create a new point of interest (POI)
 * by specifying its building, floor, name, room number, type, and description.
 * It provides a GUI interface for users to input the required information and
 * creates a new POI object when the "Create" button is clicked.
 * @author Yi Xiao
 * @version 1.0
 */
public class customizePOI implements ActionListener {
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
    private JButton create;
    private POI newPOI;
    private String newPOI_building;
    private int newPOI_floor;
    private int newPOI_X;
    private int newPOI_Y;
    private viewMap temp;


    /**
     * Constructs a customizePOI object, which provides a GUI interface for users
     * to create a new POI.
     *
     * @param a      the viewMap object that displays the map
     * @param x      the x-coordinate of the new POI
     * @param y      the y-coordinate of the new POI
     * @param tempBuilding the building of the new POI
     * @param tempFloor    the floor of the new POI
     */
    public customizePOI(viewMap a,int x,int y,String tempBuilding, int tempFloor){


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
        numberText=new JTextField();
        numberText.setBounds(120,200,200,25);
        typeText=new JComboBox<>();
        typeText.setBounds(120,260,200,25);
        typeText.addItem("Classroom");
        typeText.addItem("Lab");
        typeText.addItem("Restaurant");
        typeText.addItem("Collaborative Room");
        typeText.addItem("Other");
        descriptionText=new JTextArea();
        descriptionText.setLineWrap(true);
        descriptionText.setBorder(new LineBorder(Color.black,2));

        descriptionText.setBounds(120,320,300,100);
        panel.add(buildingText);
        panel.add(floorText);
        panel.add(nameText);
        panel.add(numberText);
        panel.add(typeText);
        panel.add(descriptionText);

        create = new JButton("Create");
        create.setBounds(200,430,80,25);
        create.addActionListener(this);
        panel.add(create);

        newPOI_building=tempBuilding;
        newPOI_floor=tempFloor;
        newPOI_X=x;
        newPOI_Y=y;

        temp=a;

    }
    /**
     * Handles the user's click on the "Create" button and creates a new POI object
     * based on the user's inputs. The new POI object is then added to the list of all
     * user-created POIs and the GUI displays the new POI on the map.
     *
     * @param e the ActionEvent object that triggers the method
     */
    public void actionPerformed(ActionEvent e){

        newPOI = new POI(newPOI_X,newPOI_Y,nameText.getText(),numberText.getText(),(String)typeText.getSelectedItem(),newPOI_building,newPOI_floor,descriptionText.getText());

        viewMap.allUserCreated.add(newPOI);
        viewMap.poiInSearch = newPOI;

        // add the new poi to arraylist
        try{
            BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
            JLabel tempIcon = new JLabel(new ImageIcon(pin));
            viewMap.allCreatedPin.add(tempIcon);
            viewMap.allCreatedPin.get(viewMap.allCreatedPin.size()-1).setLocation(newPOI.getX()-30,newPOI.getY()-35);
            viewMap.allCreatedPin.get(viewMap.allCreatedPin.size()-1).setSize(50, 50);
            viewMap.floorList.get(newPOI.getFloor() - 1).add(viewMap.allCreatedPin.get(viewMap.allCreatedPin.size()-1));
            viewMap.allCreatedPin.get(viewMap.allCreatedPin.size()-1).setVisible(true);
            viewMap.frame.repaint();
            tempIcon.setLocation(newPOI.getX()-30,newPOI.getY()-35);

            if(newPOI.getType().equals("Classroom")){
                viewMap.pinClassroom.add(tempIcon);
            }else if(newPOI.getType().equals("Restaurant")){
                viewMap.pinRestaurant.add(tempIcon);
            }else if(newPOI.getType().equals("Lab")){
                viewMap.pinLab.add(tempIcon);
            }else if(newPOI.getType().equals("Collaborative Room")){
                viewMap.pinCollaborative.add(tempIcon);
            }


        }
        catch (Exception n)
        {

        }
        viewMap.allPOIInThisMap.addItem(newPOI.getName());
        temp.showMessage(newPOI.getX()-30,newPOI.getY()-35);
        userDefined ud = new userDefined(newPOI);
        frame.dispose();

    }
}
