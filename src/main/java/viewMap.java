import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * This class implements the functionality for viewing a map of a particular building on a particular floor.
 * It also includes various user interface components like a toolbar, a search bar, and a menu bar.
 * @author Yuting Pan
 * @author Yi Xiao
 * @version 1.0
 */
public class viewMap implements ActionListener,MouseMotionListener {

    public static JFrame frame;
    private static JScrollPane map;
    private static JPanel floor;
    private static JPanel toolBar;
    private JPanel textFiled;
    private static JMenuBar menuBar;
    private static JLabel showFloor;
    private static JLabel picture;
    private  static  JButton back;
    private static JLabel select;

    private static JMenu favoriteMenu;
    private static JMenu selectBuilding;
    private static JMenu help;
    private static JMenuItem helpGuide;
    private static JMenu about;
    private static JMenuItem aboutUs;


    private static JMenuItem middlesexCollege;
    private static JMenuItem alumniStadium;
    private static JMenuItem stagingBuilding;

    private static weather currentWeather;

    private static JLabel temp;

    private static JLabel icon;

    // add POI discovery
    public static JComboBox allPOIInThisMap;
    private static JLabel allPOIInThisMapLabel;
    private static JButton discoverPOIButton;
    private static JLabel discoveryPin;

    // add search bar
    private static JTextField searchBar;
    private static JButton searchButton;
    private static JLabel invalidInput;
    private static JLabel noMatchResult;

    // add layers
    private static JCheckBox classroom;
    private static JCheckBox lab;
    private static JCheckBox userDefine;
    private static JCheckBox userFav;
    private JCheckBox resturant;
    private JCheckBox collaborative;
    private JLabel restLabel;
    private JLabel collabLabel;
    private static JLabel accessibilityLabel;
    private static JLabel classroomLabel;
    private static JLabel washroomLabel;
    private static JLabel labLabel;
    private static JLabel userDefineLabel;
    private static JLabel userFavLabel;
    private static JLabel currentBuilding;
    private static JComboBox eachFloor;
    private static JButton go;
    public int numButton;
    public String whichBuilding;
    public int whichFLoor;
    public ArrayList<JScrollPane> mapList = new ArrayList<>();
    public JLabel X;
    public JLabel Y;

    public static ArrayList<JLabel> floorList= new ArrayList<>();
    private createPin allPin;
    public static ArrayList<JLabel> pinClassroom;
    public static ArrayList<JLabel> pinLab;
    public static ArrayList<JLabel> pinRestaurant;
    public static ArrayList<JLabel> pinCollaborative;
    private ArrayList<JLabel> totalPin = new ArrayList<>();
    private ArrayList<JLabel> allSearchPin;

    private ArrayList<JLabel> allFavPin;
    public static ArrayList<JLabel> allCreatedPin;
    private ArrayList<JLabel> allVisiablePin = new ArrayList<>();
    private JLabel theFavPinInMenuBar;
    private JLabel newName = new JLabel();
    private JLabel newNumber= new JLabel();
    private JLabel Description = new JLabel();
    private JButton favouriteOption = new JButton("Set as Favourite");
    private JButton removeOption =new JButton("Remove Favourite");

    private JButton customizeOption = new JButton("Remove Customize POI");

    private JButton edit = new JButton("Edit");

    private POI[] allFav;

    private POI tempFav;
    private POI tempDele;
    public static POI poiInSearch;

    private POI tempUserCreated;

    public static ArrayList<POI> allFavourite = new ArrayList<>();
    public static ArrayList<POI> allUserCreated = new ArrayList<>();
    public static userFavorite thisUserFavorite;
    private JButton userDefined;
    private JLabel userCreate;
    private int Xcoordinate;
    private int Ycoordinate;
    private userDefined newUD;
    private  createPin allUserPin;
    private Boolean editMode=false;
    private JButton changeCoor=new JButton("Change Coordinate");
    private  JButton completeCoor = new JButton("Complete");
    public static Connection conn;
    public static Connection conn1;
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "CS2212";
    private PreparedStatement ps=null;
    private String updateSQL=null;
    private ArrayList<MouseListener>listenEachFloor=new ArrayList<>();
    private int tempPinID=-1;
    private int tempPinIDSearch=-1;
    private int tempPinIDCreated=-1;
    private int tempPinIDFav=-1;
    private  JLabel tempMoving;
    private JLabel tempFloor;
    private MouseListener tempListerner;
    private boolean underSearch=false;
    private  boolean underCreated=false;
    private boolean underFav=false;
    private boolean underJumpToFav = false;
    private boolean changeUnderSearch=false;
    private  boolean changeUnderCreated=false;
    private boolean changeUnderFav=false;
    private boolean changeUnderOther=false;

    private HashSet<Integer> pinX = new HashSet<>();
    private HashSet<Integer> pinY = new HashSet<>();

    private Dimension dim;

    /**
     *
     * This class represents the view of a map for a given building.
     * It includes various UI components such as menus, buttons, checkboxes, and labels,
     * as well as a panel to display the map.
     * @param buildingName the name of the building for which to display the map
     * @throws SQLException if there is an error executing a database query
     * @throws ClassNotFoundException if the JDBC driver for the database cannot be found
     */
    public viewMap(String buildingName) throws Exception {
        whichBuilding = buildingName;
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        floor = new JPanel();
        floor.setBackground(new Color(88, 44, 131));
        floor.setBounds(0, 0, 150, (int)dim.getHeight());

        toolBar = new JPanel();
        toolBar.setBackground(new Color(88, 44, 131));
        toolBar.setBounds((int)dim.getWidth()-200, 0, 200, (int)dim.getHeight());

        textFiled = new JPanel();
        textFiled.setBounds(150,(int)dim.getHeight()-150,(int)dim.getWidth()-floor.getWidth()-toolBar.getWidth(),100);

        JLabel testLabel = new JLabel("Test");
        testLabel.setBounds(160,650,100,100);
        textFiled.setLayout(null);
        textFiled.add(testLabel);

        X = new JLabel();
        Y = new JLabel();
//        X.setBounds(1200, 300, 200, 100);
//        Y.setBounds(1200, 400, 200, 100);
        frame = new JFrame();

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(dim);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("View Map");
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setResizable(false);
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        selectBuilding = new JMenu("Building");
        thisUserFavorite = new userFavorite();
        favoriteMenu = thisUserFavorite.getUserFavorite();
        refreshFavorite();
        help = new JMenu("Help");

        helpGuide = new JMenuItem("Visit FAQ");
        helpGuide.addActionListener(this);

        about = new JMenu("About");
        aboutUs = new JMenuItem("About us");
        aboutUs.addActionListener(this);

        middlesexCollege = new JMenuItem("Middlesex College");
        alumniStadium = new JMenuItem("Alumni Stadium");
        stagingBuilding = new JMenuItem("Staging Building");
        middlesexCollege.addActionListener(this);
        alumniStadium.addActionListener(this);
        stagingBuilding.addActionListener(this);

        help.add(helpGuide);
        selectBuilding.add(middlesexCollege);
        selectBuilding.add(alumniStadium);
        selectBuilding.add(stagingBuilding);
        about.add(aboutUs);

        menuBar.add(selectBuilding);
        menuBar.add(favoriteMenu);
        menuBar.add(help);
        menuBar.add(about);

        back = new JButton("Logout");
        back.setBounds(10,550,80,40);
        back.addActionListener(this);
        frame.add(back);
        select = new JLabel("  Select Floor");
        select.setBounds(10,210,100,50);
        select.setForeground(new Color(255,255,255));
        frame.add(select);
        eachFloor = new JComboBox<>();
        eachFloor.setBounds(10,250,135,30);

        frame.add(eachFloor);
        go = new JButton("go");
        go.setBounds(10,300,70,30);
        go.addActionListener(this);
        frame.add(go);

        numButton = findFloor(whichBuilding);

        currentBuilding = new JLabel();
        currentBuilding.setForeground(Color.white);
        currentBuilding.setBounds(10, 180,135,30);
        currentBuilding.setText("  " + whichBuilding);
        frame.add(currentBuilding);

        // create a list of floor map for this building */
        addEachFloor(whichBuilding,numButton, true);

        // add search bar to frame */
        searchBar = new HintTextField("Search POI");
        searchBar.setBounds((int)dim.getWidth()-190,20,180,30);
        searchButton = new JButton("Search");
        searchButton.setBounds((int)dim.getWidth()-183,50,80,30);
        searchButton.addActionListener(this);
        frame.add(searchBar);
        frame.add(searchButton);

        // add two search result to frame */
        invalidInput = new JLabel("  Invalid Input!");
        invalidInput.setForeground(Color.WHITE);
        invalidInput.setBounds((int)dim.getWidth()-190,80,100,30);
        invalidInput.setVisible(false);
        frame.add(invalidInput);
        noMatchResult = new JLabel("  No result!");
        noMatchResult.setForeground(Color.WHITE);
        noMatchResult.setBounds((int)dim.getWidth()-190,80,100,30);
        noMatchResult.setVisible(false);
        frame.add(noMatchResult);


        // add discover POI to frame */
        String[] nullStr = new String[0];
        allPOIInThisMap= new JComboBox(nullStr);
        allPOIInThisMap.setBounds((int)dim.getWidth()-190, 530, 180,30);
        frame.add(allPOIInThisMap);
        allPOIInThisMapLabel = new JLabel();
        allPOIInThisMapLabel.setText("  Discover POI");
        allPOIInThisMapLabel.setBounds((int)dim.getWidth()-190, 500,150,30);
        allPOIInThisMapLabel.setForeground(new Color(255,255,255));
        frame.add(allPOIInThisMapLabel);
        discoverPOIButton = new JButton("Find");
        discoverPOIButton.setBounds((int)dim.getWidth()-181, 559, 80,30);
        discoverPOIButton.addActionListener(this);
        frame.add(discoverPOIButton);

        // add layers */
        classroom = new JCheckBox();
        classroom.setBounds((int)dim.getWidth()-190, 120,25,25);
        classroom.addActionListener(this);
        classroomLabel = new JLabel();
        classroomLabel.setText("Classroom");
        classroomLabel.setBounds((int)dim.getWidth()-150, 120, 200,25);
        classroomLabel.setForeground(Color.WHITE);

        lab = new JCheckBox();
        lab.setBounds((int)dim.getWidth()-190, 150,25,25);
        lab.addActionListener(this);
        labLabel = new JLabel();
        labLabel.setText("Lab");
        labLabel.setBounds((int)dim.getWidth()-150, 150, 200,25);
        labLabel.setForeground(Color.WHITE);

        resturant = new JCheckBox();
        resturant.setBounds((int)dim.getWidth()-190, 180,25,25);
        resturant.addActionListener(this);
        restLabel = new JLabel();
        restLabel.setText("Restaurant");
        restLabel.setBounds((int)dim.getWidth()-150, 180, 200,25);
        restLabel.setForeground(Color.WHITE);

        collaborative = new JCheckBox();
        collaborative.setBounds((int)dim.getWidth()-190, 210,25,25);
        collaborative.addActionListener(this);
        collabLabel = new JLabel();
        collabLabel.setText("Collaborative Room");
        collabLabel.setBounds((int)dim.getWidth()-150, 210, 200,25);
        collabLabel.setForeground(Color.WHITE);

        userDefine = new JCheckBox();
        userDefine.setBounds((int)dim.getWidth()-190, 240,25,25);
        userDefine.addActionListener(this);
        userDefineLabel = new JLabel();
        userDefineLabel.setText("User define");
        userDefineLabel.setBounds((int)dim.getWidth()-150, 240, 200,25);
        userDefineLabel.setForeground(Color.WHITE);

        userFav = new JCheckBox();
        userFav.setBounds((int)dim.getWidth()-190, 270,25,25);
        userFav.addActionListener(this);
        userFavLabel = new JLabel();
        userFavLabel.setText("User favorite");
        userFavLabel.setBounds((int)dim.getWidth()-150, 270, 200,25);
        userFavLabel.setForeground(Color.WHITE);


        washroomLabel = new JLabel();
        washroomLabel.setText("Washroom");
        washroomLabel.setBounds((int)dim.getWidth()-150, 300, 200,25);
        washroomLabel.setForeground(Color.WHITE);

        accessibilityLabel = new JLabel();
        accessibilityLabel.setText("Accessibility");
        accessibilityLabel.setBounds((int)dim.getWidth()-150, 330, 200,25);
        accessibilityLabel.setForeground(Color.WHITE);

        frame.add(classroom);
        frame.add(lab);
        frame.add(userDefine);
        frame.add(userFav);
        frame.add(resturant);
        frame.add(collaborative);

        frame.add(classroomLabel);
        frame.add(labLabel);
        frame.add(userDefineLabel);
        frame.add(userFavLabel);
        frame.add(washroomLabel);
        frame.add(accessibilityLabel);
        frame.add(restLabel);
        frame.add(collabLabel);

        // add logo
        picture=new JLabel();
        try {
            BufferedImage logo = ImageIO.read(new File("pictures/logo2.png"));
            picture = new JLabel(new ImageIcon(logo));
            picture.setBounds(0, 0, 150, 200);
            floor.add(picture);
        } catch (Exception e) {

        }


        // add temperature and weather icon */
        JLabel info=new JLabel("Current temperature");
        info.setForeground(new Color(255,255,255));
        info.setBounds(10,350,140,30);
        frame.add(info);
        currentWeather = new weather();
        temp=new JLabel(currentWeather.getTemp());
        if(temp.getText() == null){
            temp.setText("is not available");
        }
        temp.setForeground(new Color(255,255,255));
        temp.setBounds(10,370,120,30);
        frame.add(temp);

        try {
            icon = new JLabel(new ImageIcon(new URL(currentWeather.getImage())));
            icon.setBounds(10,400,70,70);
            frame.add(icon);
        } catch (MalformedURLException e) {

        }
        // add mouse listener to tool bar to clean selected
        toolBar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
                if(e.getComponent().equals(searchBar) == false){

                    clearSearch();

                    if(allSearchPin != null){
                        for(int i = 0; i < allSearchPin.size(); i++){
                            allSearchPin.get(i).setVisible(false);
                        }
                    }

                    if(!allPOIInThisMap.getSelectedItem().equals("Please select")){
                        clearSelectDiscovery();
                    }
                }
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
                hideMessage();
            }
        });


        // add all intem to frame
        frame.add(floor);
        frame.add(toolBar);
        frame.add(map);
        frame.add(textFiled);
        frame.setVisible(true);
        frame.requestFocusInWindow();

        removeOption.setBounds(300, 20, 150, 25);
        favouriteOption.setBounds(300, 20, 150, 25);
        removeOption.setVisible(false);
        favouriteOption.setVisible(false);
        removeOption.addActionListener(this);
        favouriteOption.addActionListener(this);
        textFiled.add(favouriteOption);
        textFiled.add(removeOption);

        customizeOption.setBounds(300,60,200,25);
        customizeOption.setVisible(false);
        customizeOption.addActionListener(this);
        textFiled.add(customizeOption);


        edit.setBounds(560,60,100,25);
        edit.setVisible(false);
        edit.addActionListener(this);
        textFiled.add(edit);

        changeCoor.setBounds(560,20,150,25);
        changeCoor.setVisible(false);
        changeCoor.addActionListener(this);
        textFiled.add(changeCoor);

        completeCoor.setBounds(560,20,150,25);
        completeCoor.setVisible(false);
        completeCoor.addActionListener(this);
        textFiled.add(completeCoor);

        userDefined = new JButton("Create");
        userDefined.setBounds(500,20,100,25);
        userDefined.setVisible(false);
        userDefined.addActionListener(this);
        textFiled.add(userDefined);

        userCreate = new JLabel("  Do you want to create customize POI?");
        userCreate.setBounds(0,10,350,20);
        userCreate.setVisible(false);
        textFiled.add(userCreate);

        allFavourite = thisUserFavorite.DBFavPOI();

        newUD = new userDefined();

        // add all user created poi
        for(POI a: login.allUserCreatePOI){
            allUserCreated.add(a);
        }


        allUserPin = new createPin(allUserCreated, allFavourite);
        allFavPin = new ArrayList<>();
        allCreatedPin = new ArrayList<>();
        allFavPin = allUserPin.getFav();
        allCreatedPin = allUserPin.getCreated();

        refreshAllLayer();

    }

    /**
     * function to take clicks from user
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {

        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();

        invalidInput.setVisible(false);
        noMatchResult.setVisible(false);

        // if clicked the back bottom
        if(e.getSource()==back)
        {
            login newLogin = new login();
            frame.dispose();
        }

        // if click the go button
        else if(e.getSource()==go)
        {
            forgotComplete();
            clearInfor();
            unselectAllLayer();

            String[] allPOIInThisMapStr = new String[0];
            // check to selected item
            for(int i=1;i<=numButton;i++)
            {
                if(eachFloor.getSelectedItem().equals("Please select")){

                    for(building other: Main.allBuildings){
                        for(int j = 1; j <= mapList.size(); j++){
                            if(mapList.get(j - 1).isVisible() ){
                                mapList.get(j - 1).setVisible(false);
                            }
                        }
                    }
                    break;
                }
                if(eachFloor.getSelectedItem().equals("Floor"+i))
                {
                    whichFLoor = i;
                    mapList.get(i-1).setVisible(true);
                    for (int j = 0; j < mapList.size(); j++){
                        if(j!=i-1&&mapList.get(j).isVisible()){
                            mapList.get(j).setVisible(false);
                        }
                    }
                    allPOIInThisMapStr = discoveryPOI(whichBuilding);
                }
            }

            allPOIInThisMap.removeAllItems();
            allPOIInThisMap.addItem("Please select");
            for(int i = 0; i < allPOIInThisMapStr.length; i++){
                String temp = allPOIInThisMapStr[i];
                allPOIInThisMap.addItem(temp);
            }
            clearFav();

        }

        // if open the help button
        else if(e.getSource() == helpGuide){
            forgotComplete();
            FAQPage faq = new FAQPage();

        }
        // if click the about us button
        else if(e.getSource() == aboutUs){
            moreInfo aboutPage = new moreInfo();
        }

        // if switch building to middlesex college
        else if(e.getSource() == middlesexCollege){
            forgotComplete();
            clearInfor();
            unselectAllLayer();
            clearFav();
            eachFloor.setSelectedIndex(0);
            currentBuilding.setText("  Middlesex College");
            try {
                addEachFloor("Middlesex College", 5,false);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            for(building aBuilding: Main.allBuildings){
                if(aBuilding.getName().equals("Middlesex College")){
                    numButton = aBuilding.getFloor();
                }
            }
            allPOIInThisMap.removeAllItems();
        }

        // if switch building to alumni stadium
        else if(e.getSource() == alumniStadium){
            forgotComplete();
            clearInfor();
            unselectAllLayer();
            clearFav();
            eachFloor.setSelectedIndex(0);
            currentBuilding.setText("  Alumni Stadium");
            try {
                addEachFloor("Alumni Stadium",2,false);
            } catch (Exception ex) {

            }
            for(building aBuilding: Main.allBuildings){
                if(aBuilding.getName().equals("Alumni Stadium")){
                    numButton = aBuilding.getFloor();
                }
            }
            allPOIInThisMap.removeAllItems();

        }
        // if switch building to staging building
        else if(e.getSource() == stagingBuilding){
            forgotComplete();
            clearInfor();
            unselectAllLayer();
            clearFav();
            eachFloor.setSelectedIndex(0);
            currentBuilding.setText("  Staging Building");
            try {
                addEachFloor("Staging Building",2,false);
            } catch (Exception ex) {
            }
            for(building aBuilding: Main.allBuildings){
                if(aBuilding.getName().equals("Staging Building")){
                    numButton = aBuilding.getFloor();
                }
            }
            allPOIInThisMap.removeAllItems();

        }

        // search POI
        else if(e.getSource() == searchButton){
            forgotComplete();

            clearFav();
            if(allSearchPin != null){
                for(int i = 0; i < allSearchPin.size(); i++){
                    allSearchPin.get(i).setVisible(false);
                }
            }

            unselectAllLayer();

            POI[] searchResult = checkSearchInput();
            ArrayList<POI> searchPOI = new ArrayList<>();

            // create pin for the search result
            if(searchResult == null){

            }
            else{
                for(POI a: searchResult){
                    searchPOI.add(a);
                }
                JLabel searchPinLabel= floorList.get(whichFLoor - 1);

                try {
                    createPin searchPin= new createPin(searchPOI);
                    allSearchPin = searchPin.getSearch();
                } catch (Exception ex) {

                }
                // set location and size of the pins
                for(int i = 0; i < allSearchPin.size(); i++){
                    allSearchPin.get(i).setLocation(searchResult[i].getX() - 30,searchResult[i].getY() - 35);
                    allSearchPin.get(i).setSize(50, 50);

                    searchPinLabel.add(allSearchPin.get(i));
                    allSearchPin.get(i).setVisible(true);
                }
                frame.repaint();
            }
            hideMessage();

        }

        // discover all POI in this map
        else if (e.getSource() == discoverPOIButton) {
            forgotComplete();

            clearFav();
            unselectAllLayer();

            if(allPOIInThisMap.getSelectedItem().toString().equals("Please select")){

            }
            else{
                if(discoveryPin != null && discoveryPin.isVisible()){
                    discoveryPin.removeAll();
                    discoveryPin.setVisible(false);

                }
                userCreate.setVisible(false);
                userDefined.setVisible(false);

                // create the discovery pin
                try {
                    BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                    discoveryPin = new JLabel(new ImageIcon(pin));
                } catch (Exception ex) {

                }
                int thisX = 0;
                int thisY = 0;

                // find the location of the discovery poi
                for(POI thisPOI : Main.allPOI){
                    if(thisPOI.getBuilding().equals(whichBuilding) && thisPOI.getFloor() == whichFLoor
                            && thisPOI.getName().equals(allPOIInThisMap.getSelectedItem().toString())){
                        thisX = thisPOI.getX();
                        thisY = thisPOI.getY();
                        customizeOption.setVisible(false);
                        edit.setVisible(false);
                        changeCoor.setVisible(false);
                        completeCoor.setVisible(false);
                    }
                }
                for(POI i: allUserCreated)
                {
                    if(i.getBuilding().equals(whichBuilding) && i.getFloor() == whichFLoor
                            && i.getName().equals(allPOIInThisMap.getSelectedItem().toString())){
                        thisX = i.getX();
                        thisY = i.getY();
                    }
                }
                discoveryPin.setLocation(thisX - 30,thisY - 35);
                discoveryPin.setSize(50, 50);

                discoveryPin.setVisible(true);
                showMessage(discoveryPin.getX(),discoveryPin.getY());
                floorList.get(whichFLoor - 1).add(discoveryPin);
                frame.repaint();
            }
        }

        // if clicked the remove favorite button
        else if (e.getSource()==removeOption)
        {
            hideMessage();

            tempFav=tempDele;
            if(theFavPinInMenuBar != null){
                theFavPinInMenuBar.setVisible(false);
            }

            // remove fav from this user arraylist
            try {
                thisUserFavorite.deleteFav(tempDele);
            }
            catch (Exception f)
            {

            }
            allFavourite.remove(tempDele);

            // remove fav from database
            try {
                allFavourite = thisUserFavorite.DBFavPOI();
            }
            catch (Exception ef)
            {

            }
            // remove that fav pin
            for(int i=0;i<allFavPin.size();i++)
            {

                if(allFavPin.get(i).getX()==tempDele.getX()-30&&allFavPin.get(i).getY()==tempDele.getY()-35)
                {
                    allFavPin.get(i).setVisible(false);
                    allFavPin.remove(i);

                }
            }

            // refresh fav in menu bar
            try{
                refreshFavorite();
                frame.repaint();
            }
            catch (Exception ex)
            {
            }

        }

        // if click the fav button
        else if (e.getSource()==favouriteOption)
        {
            favouriteOption.setVisible(false);
            removeOption.setVisible(true);
            tempDele=tempFav;
            if(theFavPinInMenuBar != null){
                theFavPinInMenuBar.setVisible(false);
            }

            // add this poi to this user fav arraylist
            try {
                thisUserFavorite.addFavroite(tempFav);
            }
            catch (Exception f)
            {

            }
            // add fav pin
            allFavourite.add(tempFav);
            try{
                BufferedImage pin = ImageIO.read(new File("pictures/pin.png"));
                JLabel tempIcon = new JLabel(new ImageIcon(pin));
                allFavPin.add(tempIcon);

            }
            catch(Exception m){

            }
            // refresh fav in menu bar
            try{
                refreshFavorite();
                frame.repaint();
            }
            catch (Exception ex)
            {
            }

        }

        // if clicked the remove user defined poi
        else if(e.getSource()==customizeOption)
        {
            hideMessage();

            boolean bothList=false;

            // check this poi is user fav or not
            for(int i=0;i<allFavourite.size();i++)
            {
                if(allFavourite.get(i).checkEqual(allFavourite.get(i),tempUserCreated))
                {
                    bothList=true;
                }
            }
            // if in both list, remove both
            if(bothList)
            {
                try {
                    thisUserFavorite.deleteFav(tempUserCreated);
                }
                catch (Exception f)
                {
                }
                allFavourite.remove(tempUserCreated);
                try {
                    allFavourite = thisUserFavorite.DBFavPOI();
                }
                catch (Exception ef)
                {

                }

                // delete user defined poi enter from fav menu bar
                for(int i = 0; i < allFavPin.size(); i++){
                    if(allFavPin.get(i).isVisible()){
                        int xtemp = (int) allFavPin.get(i).getLocation().getX();
                        int ytemp = (int) allFavPin.get(i).getLocation().getY();
                        if(xtemp == tempUserCreated.getX() - 30 && ytemp == tempUserCreated.getY() - 35){
                            mapList.get(whichFLoor-1).remove(allFavPin.get(i));
                            allFavPin.get(i).setVisible(false);
                        }
                    }
                }
                allFavPin.remove(allFavPin.size()-1);

                try{
                    refreshFavorite();
                    frame.repaint();
                }
                catch (Exception ex)
                {

                }

            }
            try {
                newUD.deleteCreate(tempUserCreated);

            }
            catch (Exception ud)
            {

            }
            allUserCreated.remove(tempUserCreated);

            // update database
            try {
                allUserCreated = newUD.DBDefPOI();
            }
            catch (Exception ef)
            {

            }
            // update discovery poi
            allPOIInThisMap.removeItem(tempUserCreated.getName());

            //refresh fav in menu bar
            try {
                refreshFavorite();
            } catch (SQLException ex) {

            } catch (ClassNotFoundException ex) {

            }

            // if type is classroom, remove from classroom layer
            if(tempUserCreated.getType().equals("Classroom"))
            {
                for(int i=0;i<pinClassroom.size();i++)
                {
                    if(pinClassroom.get(i).getX()==tempUserCreated.getX()-30&&pinClassroom.get(i).getY()==tempUserCreated.getY()-35)
                    {
                        pinClassroom.get(i).setVisible(false);
                        pinClassroom.remove(i);
                        allCreatedPin.remove(allCreatedPin.size()-1);
                    }
                }
            }
            // if type is restaurant, remove from restaurant layer
            else if(tempUserCreated.getType().equals("Restaurant"))
            {
                for(int i=0;i<pinRestaurant.size();i++)
                {
                    if(pinRestaurant.get(i).getX()==tempUserCreated.getX()-30&&pinRestaurant.get(i).getY()==tempUserCreated.getY()-35)
                    {
                        pinRestaurant.get(i).setVisible(false);
                        pinRestaurant.remove(i);
                        allCreatedPin.remove(allCreatedPin.size()-1);
                    }
                }
            }
            // if type is lab, remove from lab layer
            else if(tempUserCreated.getType().equals("Lab"))
            {
                for(int i=0;i<pinLab.size();i++)
                {
                    if(pinLab.get(i).getX()==tempUserCreated.getX()-30&&pinLab.get(i).getY()==tempUserCreated.getY()-35)
                    {
                        pinLab.get(i).setVisible(false);
                        pinLab.remove(i);
                        allCreatedPin.remove(allCreatedPin.size()-1);
                    }
                }
            }
            // if type is Collaborative Room, remove from Collaborative Room layer
            else if(tempUserCreated.getType().equals("Collaborative Room"))
            {
                for(int i=0;i<pinCollaborative.size();i++)
                {
                    if(pinCollaborative.get(i).getX()==tempUserCreated.getX()-30&&pinCollaborative.get(i).getY()==tempUserCreated.getY()-35)
                    {
                        pinCollaborative.get(i).setVisible(false);
                        pinCollaborative.remove(i);
                        allCreatedPin.remove(allCreatedPin.size()-1);
                    }
                }
            }


            // remove from userdefined pin
            for(int i=0;i<allCreatedPin.size();i++)
            {

                if(allCreatedPin.get(i).getX()==tempUserCreated.getX()-30&&allCreatedPin.get(i).getY()==tempUserCreated.getY()-35)
                {
                    allCreatedPin.get(i).setVisible(false);
                    allCreatedPin.remove(i);
                    if(tempUserCreated.getType().equals("Classroom"))
                    {
                        pinClassroom.remove(pinClassroom.size()-1);
                    }
                    else if(tempUserCreated.getType().equals("Restaurant"))
                    {
                        pinRestaurant.remove(pinRestaurant.size()-1);
                    }
                    else if(tempUserCreated.getType().equals("Lab"))
                    {
                        pinLab.remove(pinLab.size()-1);
                    }
                    else if(tempUserCreated.getType().equals("Collaborative Room"))
                    {
                        pinCollaborative.remove(pinCollaborative.size()-1);
                    }

                }
            }

            // refresh fav in menu bar
            try {
                refreshAllLayer();
            } catch (SQLException ex) {

            } catch (ClassNotFoundException ex) {

            }

            frame.repaint();

        }

        // if click the create new poi button
        else if(e.getSource()==userDefined)
        {

            customizePOI cp = new customizePOI(this,Xcoordinate,Ycoordinate,whichBuilding,whichFLoor);

        }
        // if click the edit poi button
        else if(e.getSource()==edit)
        {
            editUserDefined eu=new editUserDefined(this,tempUserCreated,whichBuilding,whichFLoor);

        }
        // if click the change coordinate button
        else if(e.getSource()==changeCoor)
        {

            hiddenSamePin();
            // confirm from which floor
            for(int i=0;i<mapList.size();i++)
            {
                if(mapList.get(i).isVisible())
                {

                    floorList.get(i).removeMouseListener(listenEachFloor.get(i));
                    tempListerner = listenEachFloor.get(i);
                    tempFloor = floorList.get(i);
                }
            }

            // this pin from discovery
            if(allPOIInThisMap.getSelectedItem().toString().equals("Please select") == false){
                hideAllLayerPin();
                discoveryPin.setVisible(true);
            }


            editMode=true;
            changeCoor.setVisible(false);
            completeCoor.setVisible(true);

            // change from search
            if(allSearchPin!=null) {
                for (int i = 0; i < allSearchPin.size(); i++) {
                    if (allSearchPin.get(i).getX() + 30 == tempUserCreated.getX() && allSearchPin.get(i).getY() + 35 == tempUserCreated.getY()) {
                        underSearch = true;

                        if(allSearchPin.get(i).isVisible()){
                            hideAllLayerPin();
                            changeUnderSearch = true;
                        }
                    }
                }
            }

            // change from user create
            for(int i=0;i<allCreatedPin.size();i++)
            {
                if (allCreatedPin.get(i).getX() + 30 == tempUserCreated.getX() && allCreatedPin.get(i).getY() + 35 == tempUserCreated.getY())
                {

                    underCreated=true;
                    if(allCreatedPin.get(i).isVisible()){
                        changeUnderCreated = true;
                    }

                }
            }

            // change from fav
            for(int i=0;i<allFavPin.size();i++)
            {
                if (allFavPin.get(i).getX() + 30 == tempUserCreated.getX() && allFavPin.get(i).getY() + 35 == tempUserCreated.getY())
                {
                    underFav=true;
                    if(allFavPin.get(i).isVisible()){
                        changeUnderFav = true;
                    }
                }
            }

            // change from other
            if(changeUnderFav == false && changeUnderCreated == false && changeUnderSearch == false ){
                if((discoveryPin != null && discoveryPin.isVisible() == false) || discoveryPin == null){
                    changeUnderOther = true;
                }

            }

            if(discoveryPin!=null&&discoveryPin.isVisible()){

                if(discoveryPin.getMouseMotionListeners().length == 0){
                    discoveryPin.addMouseMotionListener(this);
                }

                tempMoving = discoveryPin;
                tempUserCreated = new POI(discoveryPin.getX() + 30, discoveryPin.getY() + 35, tempUserCreated.getName(), tempUserCreated.getRoomNumber(), tempUserCreated.getType(), tempUserCreated.getBuilding(), tempUserCreated.getFloor(), tempUserCreated.getDescription() );

            }


                // find the change id from search
                if (underSearch) {
                    for (int i = 0; i < allSearchPin.size(); i++) {
                        if (allSearchPin.get(i).getX() + 30 == tempUserCreated.getX() && allSearchPin.get(i).getY() + 35 == tempUserCreated.getY()) {
                            if(allSearchPin.get(i).isVisible() &&  allSearchPin.get(i).getMouseMotionListeners().length == 0){
                                allSearchPin.get(i).addMouseMotionListener(this);
                            }

                            tempPinIDSearch = i;
                            if(allSearchPin.get(i).isVisible()){
                                changeUnderSearch = true;

                                tempMoving = allSearchPin.get(i);
                                if(tempUserCreated.getX() != tempMoving.getX() + 30 &&  tempUserCreated.getY() != tempMoving.getY() + 35){
                                    tempUserCreated = poiInSearch;
                                }

                            }
                            break;

                        }
                    }
                }
            // find the change id from created
                if (underCreated) {
                    for (int i = 0; i < allCreatedPin.size(); i++) {

                        if (allCreatedPin.get(i).getX() + 30 == tempUserCreated.getX() && allCreatedPin.get(i).getY() + 35 == tempUserCreated.getY()) {
                            if(allCreatedPin.get(i).isVisible() && allCreatedPin.get(i).getMouseMotionListeners().length == 0){
                                allCreatedPin.get(i).addMouseMotionListener(this);
                            }

                            tempPinIDCreated = i;
                            if(allCreatedPin.get(i).isVisible()){
                                changeUnderCreated = true;

                                tempMoving = allCreatedPin.get(i);
                            }

                            break;
                        }
                    }
                }

                // find the change id from fav
                if (underFav) {
                    for (int i = 0; i < allFavPin.size(); i++) {
                        if (allFavPin.get(i).getX() + 30 == tempUserCreated.getX() && allFavPin.get(i).getY() + 35 == tempUserCreated.getY()) {
                            if(allFavPin.get(i).isVisible() && allFavPin.get(i).getMouseMotionListeners().length == 0){
                                allFavPin.get(i).addMouseMotionListener(this);
                            }
                            tempPinIDFav = i;

                            if(allFavPin.get(i).isVisible()){
                                changeUnderFav = true;
                                tempMoving = allFavPin.get(i);
                            }
                            break;

                        }
                    }
                }

                    // find change id from other type
                    if (tempUserCreated.getType().equals("Classroom")) {

                        for (int i = 0; i < pinClassroom.size(); i++) {
                            if (pinClassroom.get(i).getX() + 30 == tempUserCreated.getX() && pinClassroom.get(i).getY() + 35 == tempUserCreated.getY()) {
                                if(pinClassroom.get(i).isVisible() && pinClassroom.get(i).getMouseMotionListeners().length == 0){
                                    pinClassroom.get(i).addMouseMotionListener(this);

                                }

                                    tempPinID = i;

                                    if(changeUnderOther && changeUnderCreated == false ){
                                        tempMoving = pinClassroom.get(i);
                                    }
                                    break;


                            }
                        }

                    }
                    // find change id from other type
                    else if (tempUserCreated.getType().equals("Restaurant")) {
                        for (int i = 0; i < pinRestaurant.size(); i++) {
                            if (pinRestaurant.get(i).getX() + 30 == tempUserCreated.getX() && pinRestaurant.get(i).getY() + 35 == tempUserCreated.getY()) {

                                if(pinRestaurant.get(i).isVisible() && pinRestaurant.get(i).getMouseMotionListeners().length == 0){
                                    pinRestaurant.get(i).addMouseMotionListener(this);
                                }
                                tempPinID = i;
                                if(changeUnderOther){
                                    tempMoving = pinRestaurant.get(i);
                                }
                                break;

                            }
                        }

                    }
                    // find change id from other type
                    else if (tempUserCreated.getType().equals("Lab")) {
                        for (int i = 0; i < pinLab.size(); i++) {
                            if (pinLab.get(i).getX() + 30 == tempUserCreated.getX() && pinLab.get(i).getY() + 35 == tempUserCreated.getY()) {

                                if(pinLab.get(i).isVisible() && pinLab.get(i).getMouseMotionListeners().length == 0){
                                    pinLab.get(i).addMouseMotionListener(this);;
                                }
                                tempPinID = i;
                                if(changeUnderOther){
                                    tempMoving = pinLab.get(i);
                                }
                                break;

                            }
                        }

                    }
                    // find change id from other type
                    else if (tempUserCreated.getType().equals("Collaborative Room")) {
                        for (int i = 0; i < pinCollaborative.size(); i++) {
                            if (pinCollaborative.get(i).getX() + 30 == tempUserCreated.getX() && pinCollaborative.get(i).getY() + 35 == tempUserCreated.getY()) {

                                if(pinCollaborative.get(i).isVisible() && pinCollaborative.get(i).getMouseMotionListeners().length == 0){
                                    pinCollaborative.get(i).addMouseMotionListener(this);;
                                }
                                tempPinID = i;
                                if(changeUnderOther){
                                    tempMoving = pinCollaborative.get(i);
                                }
                                break;

                            }
                        }

                    }

        }

        // if click the complete change coordinate
        else if(e.getSource()==completeCoor){
            editMode=false;
            completeCoor.setVisible(false);
            changeCoor.setVisible(true);
            int tempX=0;
            int tempY=0;
            if(discoveryPin!=null&&discoveryPin.isVisible()) {
                discoveryPin.removeMouseMotionListener(this);
                tempX = discoveryPin.getX() + 30;
                tempY = discoveryPin.getY() + 35;
                discoveryPin.setLocation(tempMoving.getX(),tempMoving.getY());
                tempUserCreated = new POI(discoveryPin.getX() + 30, discoveryPin.getY() + 35, tempUserCreated.getName(), tempUserCreated.getRoomNumber(), tempUserCreated.getType(), tempUserCreated.getBuilding(), tempUserCreated.getFloor(), tempUserCreated.getDescription() );
            }

                // if finish from search
                if(underSearch){

                    allSearchPin.get(tempPinIDSearch).setLocation(tempMoving.getX(),tempMoving.getY()) ;

                    if(changeUnderSearch){
                        allSearchPin.get(tempPinIDSearch).removeMouseMotionListener(this);
                        if(allSearchPin.get(tempPinIDSearch).isVisible()){
                            tempX = allSearchPin.get(tempPinIDSearch).getX() + 30;
                            tempY = allSearchPin.get(tempPinIDSearch).getY() + 35;
                            tempUserCreated = new POI(allSearchPin.get(tempPinIDSearch).getX() + 30, allSearchPin.get(tempPinIDSearch).getY() + 35, tempUserCreated.getName(), tempUserCreated.getRoomNumber(), tempUserCreated.getType(), tempUserCreated.getBuilding(), tempUserCreated.getFloor(), tempUserCreated.getDescription() );

                        }
                    }

                }
                // if finish form create
                if(underCreated){
                    allCreatedPin.get(tempPinIDCreated).setLocation(tempMoving.getX(),tempMoving.getY());

                    if(changeUnderCreated){
                        allCreatedPin.get(tempPinIDCreated).removeMouseMotionListener(this);
                        if(allCreatedPin.get(tempPinIDCreated).isVisible()){
                            tempX = allCreatedPin.get(tempPinIDCreated).getX() + 30;
                            tempY = allCreatedPin.get(tempPinIDCreated).getY() + 35;
                            tempUserCreated = new POI(allCreatedPin.get(tempPinIDCreated).getX() + 30, allCreatedPin.get(tempPinIDCreated).getY() + 35, tempUserCreated.getName(), tempUserCreated.getRoomNumber(), tempUserCreated.getType(), tempUserCreated.getBuilding(), tempUserCreated.getFloor(), tempUserCreated.getDescription() );

                        }
                    }

                }
                // if finish from fav
                if(underFav){
                    allFavPin.get(tempPinIDFav).setLocation(tempMoving.getX(),tempMoving.getY());

                    if(changeUnderFav){
                        allFavPin.get(tempPinIDFav).removeMouseMotionListener(this);
                        if(allFavPin.get(tempPinIDFav).isVisible()){
                            tempX = allFavPin.get(tempPinIDFav).getX() + 30;
                            tempY = allFavPin.get(tempPinIDFav).getY() + 35;
                            tempUserCreated = new POI(allFavPin.get(tempPinIDFav).getX() + 30, allFavPin.get(tempPinIDFav).getY() + 35, tempUserCreated.getName(), tempUserCreated.getRoomNumber(), tempUserCreated.getType(), tempUserCreated.getBuilding(), tempUserCreated.getFloor(), tempUserCreated.getDescription() );

                        }
                    }


                    try {
                        refreshFavorite();
                    } catch (SQLException ex) {

                    } catch (ClassNotFoundException ex) {

                    }
                }

                    // if finished from other type
                    if (tempUserCreated.getType().equals("Classroom")) {

                        pinClassroom.get(tempPinID).setLocation(tempMoving.getX(),tempMoving.getY());

                        if(changeUnderOther){
                            pinClassroom.get(tempPinID).removeMouseMotionListener(this);
                            tempX = pinClassroom.get(tempPinID).getX() + 30;
                            tempY = pinClassroom.get(tempPinID).getY() + 35;
                            tempUserCreated = new POI(pinClassroom.get(tempPinID).getX() + 30, pinClassroom.get(tempPinID).getY() + 35, tempUserCreated.getName(), tempUserCreated.getRoomNumber(), tempUserCreated.getType(), tempUserCreated.getBuilding(), tempUserCreated.getFloor(), tempUserCreated.getDescription() );

                        }


                    } else if (tempUserCreated.getType().equals("Restaurant")) {

                        pinRestaurant.get(tempPinID).setLocation(tempMoving.getX(),tempMoving.getY());

                        if(changeUnderOther){
                            pinRestaurant.get(tempPinID).removeMouseMotionListener(this);
                            tempX = pinRestaurant.get(tempPinID).getX() + 30;
                            tempY = pinRestaurant.get(tempPinID).getY() + 35;
                            tempUserCreated = new POI(pinRestaurant.get(tempPinID).getX() + 30, pinRestaurant.get(tempPinID).getY() + 35, tempUserCreated.getName(), tempUserCreated.getRoomNumber(), tempUserCreated.getType(), tempUserCreated.getBuilding(), tempUserCreated.getFloor(), tempUserCreated.getDescription() );

                        }


                    } else if (tempUserCreated.getType().equals("Lab")) {
                        pinLab.get(tempPinID).setLocation(tempMoving.getX(),tempMoving.getY());

                        if(changeUnderOther){
                            pinLab.get(tempPinID).removeMouseMotionListener(this);
                            tempX = pinLab.get(tempPinID).getX() + 30;
                            tempY = pinLab.get(tempPinID).getY() + 35;
                            tempUserCreated = new POI(pinLab.get(tempPinID).getX() + 30, pinLab.get(tempPinID).getY() + 35, tempUserCreated.getName(), tempUserCreated.getRoomNumber(), tempUserCreated.getType(), tempUserCreated.getBuilding(), tempUserCreated.getFloor(), tempUserCreated.getDescription() );

                        }


                    } else if (tempUserCreated.getType().equals("Collaborative Room")) {
                        pinCollaborative.get(tempPinID).setLocation(tempMoving.getX(),tempMoving.getY());

                        if(changeUnderOther){
                            pinCollaborative.get(tempPinID).removeMouseMotionListener(this);
                            tempX = pinCollaborative.get(tempPinID).getX() + 30;
                            tempY = pinCollaborative.get(tempPinID).getY() + 35;
                            tempUserCreated = new POI(pinCollaborative.get(tempPinID).getX() + 30, pinCollaborative.get(tempPinID).getY() + 35, tempUserCreated.getName(), tempUserCreated.getRoomNumber(), tempUserCreated.getType(), tempUserCreated.getBuilding(), tempUserCreated.getFloor(), tempUserCreated.getDescription() );


                        }

                    }


            // update the user create
            if(tempDele!=null) {
                tempDele = tempUserCreated;
                if (tempUserCreated.checkEqual(tempUserCreated, tempDele)) {
                    try {
                        editUserFavCoordinate(tempUserCreated, tempX, tempY);
                        allFavourite = thisUserFavorite.DBFavPOI();
                    } catch (Exception ed) {

                    }

                }
            }

            // update the database
            try {
                editUserCreatedCoordinate(tempUserCreated, tempX, tempY);
                allUserCreated=newUD.DBDefPOI();
            }
            catch (Exception ed)
            {

            }
            for(int i=0;i<mapList.size();i++)
            {
                if(mapList.get(i).isVisible())
                {
                    floorList.get(i).addMouseListener(listenEachFloor.get(i));
                }
            }

            // change to default
            tempPinID=-1;
            tempPinIDSearch = -1;
            tempPinIDFav=-1;
            tempPinIDCreated=-1;
            underFav=false;
            underCreated=false;
            underSearch=false;
            changeUnderFav = false;
            changeUnderCreated = false;
            changeUnderSearch = false;
            changeUnderOther = false;

        }
        else {
            check();
            frame.repaint();
        }


        // reset the search bar hint
        if(e.getSource() != searchButton && searchBar.getText().equals("Search POI") == false
                && e.getSource() != favouriteOption && e.getSource() != removeOption
                && e.getSource() != changeCoor && e.getSource() != completeCoor){
            clearSearch();
        }

        // reset the discovery item to default
        if(e.getSource() != discoverPOIButton && e.getSource() != favouriteOption
                && e.getSource() != favouriteOption && e.getSource() != removeOption
                && e.getSource() != changeCoor && e.getSource() != completeCoor){
            clearSelectDiscovery();

        }


        // jump to the favorite POI
        userFavorite thisUserFavorite = null;
        String[] thisUserFavArray;

        Object source = e.getSource();


        // if source menu bar
        if(source instanceof JMenuItem){
            // find parent level menu, confirm should click favorite POI or not
            JMenuItem sourceMenu = (JMenuItem) e.getSource();
            JPopupMenu parent = (JPopupMenu) sourceMenu.getParent();
            JMenuItem parentMenu = (JMenuItem) parent.getInvoker();

            // not click favorite
            if(parentMenu.getText().equals("Building") || parentMenu.getText().equals("Help") || parentMenu.getText().equals("About")){

            }
            else{

                JMenuItem clickedFav = (JMenuItem) e.getSource();
                String name = clickedFav.getText();

                JPopupMenu clickedFloorItem = (JPopupMenu) clickedFav.getParent();
                JMenuItem clickedFloor = (JMenuItem) clickedFloorItem.getInvoker();
                String floor = clickedFloor.getText();

                JPopupMenu clickedBuildingItem = (JPopupMenu) clickedFloor.getParent();
                JMenuItem clickedBuilding = (JMenuItem) clickedBuildingItem.getInvoker();
                String building = clickedBuilding.getText();


                try {
                    jumpToFav(name, floor, building);
                } catch (Exception ex) {

                }
            }
        }


        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();


    }


    /**
     * This method is used to update the map display based on the selected POI category and building/floor.
     * If the "classroom" checkbox is selected, it will display all classrooms on the current building/floor.
     * If the "restaurant" checkbox is selected, it will display all restaurants on the current building/floor.
     * If the "lab" checkbox is selected, it will display all labs on the current building/floor.
     * If the "collaborative" checkbox is selected, it will display all collaborative rooms on the current building/floor.
     * If the "userFav" checkbox is selected and there are favorite POIs added by the user, it will display all favorite POIs on the current building/floor.
     * If the "userDefine" checkbox is selected and there are user-defined POIs added by the user, it will display all user-defined POIs on the current building/floor.
     * It also hides any duplicated pins and sets the visibility of various UI elements based on the current checkbox selection.
     */
    private void check() {
        try {
            refreshAllLayer();
        } catch (Exception ex) {

        }
        // if classroom is select
        if (classroom.isSelected()) {
            forgotComplete();
            int tempNum = 0;
            // add all classroom pin to the map
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (POI eachPOI : Main.allPOI) {
                        if (eachPOI.getType().equals("Classroom") && eachPOI.getBuilding().equals(whichBuilding) && eachPOI.getFloor() == i + 1) {

                            pinClassroom.get(tempNum).setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                            pinClassroom.get(tempNum).setSize(50, 50);
                            pinClassroom.get(tempNum).setVisible(true);
                            tempLable.add(pinClassroom.get(tempNum));
                            tempNum++;
                        }
                    }
                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getType().equals("Classroom") && allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {

                            pinClassroom.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            pinClassroom.get(tempNum).setSize(50, 50);
                            tempLable.add(pinClassroom.get(tempNum));
                            tempNum++;
                        }
                    }
                }
            }
            for (int k = 0; k < tempNum; k++) {
                pinClassroom.get(k).setVisible(true);
            }
        } else {
            for (int i = 0; i < pinClassroom.size(); i++) {
                pinClassroom.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }

        // if restaurant is selected
        if (resturant.isSelected()) {
            forgotComplete();

            int tempNum = 0;
            for (int i = 0; i < mapList.size(); i++) {
                // add all restaurant pin to the map
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (POI eachPOI : Main.allPOI) {
                        if (eachPOI.getType().equals("Restaurant") && eachPOI.getBuilding().equals(whichBuilding) && eachPOI.getFloor() == i + 1) {
                            pinRestaurant.get(tempNum).setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                            pinRestaurant.get(tempNum).setSize(50, 50);
                            tempLable.add(pinRestaurant.get(tempNum));
                            tempNum++;

                        }
                    }

                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getType().equals("Restaurant") && allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {

                            pinRestaurant.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            pinRestaurant.get(tempNum).setSize(50, 50);
                            tempLable.add(pinRestaurant.get(tempNum));
                            tempNum++;
                        }
                    }
                }
            }
            for (int k = 0; k < tempNum; k++) {
                pinRestaurant.get(k).setVisible(true);
            }
        } else {
            for (int i = 0; i < pinRestaurant.size(); i++) {
                pinRestaurant.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }

        // if lab is select
        if (lab.isSelected()) {
            forgotComplete();
            int tempNum = 0;
            for (int i = 0; i < mapList.size(); i++) {
                // add all lab pin to the map
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (POI eachPOI : Main.allPOI) {
                        if (eachPOI.getType().equals("Lab") && eachPOI.getBuilding().equals(whichBuilding) && eachPOI.getFloor() == i + 1) {
                            pinLab.get(tempNum).setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                            pinLab.get(tempNum).setSize(50, 50);
                            tempLable.add(pinLab.get(tempNum));
                            tempNum++;
                        }
                    }
                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getType().equals("Lab") && allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {

                            pinLab.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            pinLab.get(tempNum).setSize(50, 50);
                            tempLable.add(pinLab.get(tempNum));
                            tempNum++;
                        }
                    }
                }
            }
            for (int k = 0; k < tempNum; k++) {
                pinLab.get(k).setVisible(true);
            }
        } else {
            for (int i = 0; i < pinLab.size(); i++) {
                pinLab.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }

        // if collaborative is select
        if (collaborative.isSelected()) {
            forgotComplete();
            int tempNum = 0;
            // add all collaborative to the mao
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (POI eachPOI : Main.allPOI) {
                        if (eachPOI.getType().equals("Collaborative Room") && eachPOI.getBuilding().equals(whichBuilding) && eachPOI.getFloor() == i + 1) {
                            pinCollaborative.get(tempNum).setLocation(eachPOI.getX() - 30, eachPOI.getY() - 35);
                            pinCollaborative.get(tempNum).setSize(50, 50);
                            tempLable.add(pinCollaborative.get(tempNum));
                            tempNum++;
                        }
                    }
                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getType().equals("Collaborative Room") && allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {

                            pinCollaborative.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            pinCollaborative.get(tempNum).setSize(50, 50);
                            tempLable.add(pinCollaborative.get(tempNum));
                            tempNum++;
                        }
                    }
                }
            }
            for (int k = 0; k < tempNum; k++) {
                pinCollaborative.get(k).setVisible(true);
            }

        } else {
            for (int i = 0; i < pinCollaborative.size(); i++) {
                pinCollaborative.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }

        // if user fav is select
        if (userFav.isSelected()&&allFavourite.size()>0) {
            forgotComplete();
            int tempNum = 0;
            // add all fav pin to the map
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (int j = 0; j < allFavourite.size(); j++) {
                        if (allFavourite.get(j).getBuilding().equals(whichBuilding) && allFavourite.get(j).getFloor() == i + 1) {
                            allFavPin.get(tempNum).setLocation(allFavourite.get(j).getX() - 30, allFavourite.get(j).getY() - 35);
                            allFavPin.get(tempNum).setSize(50, 50);
                            tempLable.add(allFavPin.get(tempNum));
                            tempNum++;
                        }
                    }

                }
                for (int k = 0; k < tempNum; k++) {
                    allFavPin.get(k).setVisible(true);
                }

            }
        } else {
            for (int i = 0; i < allFavPin.size(); i++) {
                allFavPin.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }

        // if user create is select
        if (userDefine.isSelected()) {
            forgotComplete();
            int tempNum = 0;
            // add all user create to mao
            for (int i = 0; i < mapList.size(); i++) {
                if (mapList.get(i).isVisible()) {
                    JLabel tempLable = floorList.get(i);
                    for (int j = 0; j < allUserCreated.size(); j++) {
                        if (allUserCreated.get(j).getBuilding().equals(whichBuilding) && allUserCreated.get(j).getFloor() == i + 1) {
                            allCreatedPin.get(tempNum).setLocation(allUserCreated.get(j).getX() - 30, allUserCreated.get(j).getY() - 35);
                            allCreatedPin.get(tempNum).setSize(50, 50);
                            tempLable.add(allCreatedPin.get(tempNum));
                            tempNum++;
                        }
                    }

                }
                for (int k = 0; k < tempNum; k++) {
                    allCreatedPin.get(k).setVisible(true);
                }

            }

        } else {
            for (int i = 0; i < allCreatedPin.size(); i++) {
                allCreatedPin.get(i).setVisible(false);
                newName.setText(" ");
                newNumber.setText(" ");
                Description.setText(" ");
                favouriteOption.setVisible(false);
                removeOption.setVisible(false);
                customizeOption.setVisible(false);
                edit.setVisible(false);
                changeCoor.setVisible(false);
                completeCoor.setVisible(false);

            }
        }

        hiddenSamePin();
    }


    /**
     * Returns an array of all Points of Interest (POI) in a given building and floor.
     * find the array of all POI's name in current map from all POI array
     * @param building the building where the POI is located
     * @return an array of all POIs in the specified building and floor
     */
    private String[] discoveryPOI(String building){
        ArrayList<String> temp = new ArrayList<>();
        // iterate all poi in this building and floor
        for(POI aPOI : Main.allPOI){
            if(aPOI.getBuilding().equals(building) &&
                    aPOI.getFloor() == whichFLoor){
                temp.add(aPOI.getName());
            }
        }
        for(POI i : allUserCreated)
        {
            if(i.getBuilding().equals(building) &&
                    i.getFloor() == whichFLoor)
                temp.add(i.getName());
        }
        String[] result = temp.toArray(new String[0]);
        return result;
    }

    /**
     * this function return how many floor of this building
     * Finds and returns the floor number of a building with a given name.
     * @param thisBuilding the name of the building to find the floor of
     * @return the floor number of the building, or 0 if the building is not found
     */
    private int findFloor(String thisBuilding){
        for(building aBuilding: Main.allBuildings){
            if(aBuilding.getName().equals(thisBuilding)){
                return aBuilding.getFloor();
            }
        }
        return 0;

    }

    /**
     * create a map list for each building
     * This method adds each floor item for a given building to the GUI. It also sets the previous map as not visible if a new map is shown.
     * @param current a String representing the current building
     * @param numFloor an int representing the number of floors in the current building
     * @param newMap a boolean indicating whether a new map is being shown
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the Java Virtual Machine (JVM) cannot load the specified class
     */
    private void addEachFloor(String current, int numFloor, boolean newMap) throws SQLException, ClassNotFoundException {

        //if have shown other map, set the previous map as not visible
        if(newMap == false){
            for(building other: Main.allBuildings){
                if(!other.getName().equals(whichBuilding)){
                    for(int i = 1; i <= mapList.size(); i++){
                        if(mapList.get(i - 1).isVisible() ){
                            mapList.get(i - 1).setVisible(false);
                        }
                    }
                }
            }
        }

        // add each floor item for this building
        floorList.clear();
        eachFloor.removeAllItems();
        eachFloor.addItem("Please select");
        eachFloor.setSelectedIndex(0);
        mapList.clear();
        whichBuilding = current;

        // create all layer pin
        if(newMap == true){
            allPin = new createPin(allUserCreated, allFavourite, Main.allPOI);
            pinClassroom = allPin.getClassroom();
            pinLab = allPin.getLab();
            pinRestaurant = allPin.getRestaurant();
            pinCollaborative = allPin.getCollaborative();
            totalPin = allPin.getTotal();
        }
        else{
            refreshAllLayer();
        }

        // add mao to the maofloor
        for (int i = 1; i <= numFloor; i++) {
            eachFloor.addItem("Floor" + i);
            try {
                BufferedImage differentFloor = ImageIO
                        .read(new File("pictures/" + whichBuilding + "/floor" + i + ".jpg"));
                showFloor = new JLabel(new ImageIcon(differentFloor));
                floorList.add(showFloor);
                map = new JScrollPane(showFloor, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
                map.setBounds(150, 0, (int)dim.getWidth()-floor.getWidth()-toolBar.getWidth(), (int)dim.getHeight()-150);
                map.setVisible(false);
                frame.add(map);
                mapList.add(map);

                MouseListener ml=new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        Xcoordinate = e.getX();
                        Ycoordinate = e.getY();
//                        X.setText("X=" + e.getX());
//                        Y.setText("Y=" + e.getY());
                        userDefined.setVisible(true);
                        userCreate.setVisible(true);
                        newName.setText(" ");
                        newNumber.setText(" ");
                        Description.setText(" ");
                        favouriteOption.setVisible(false);
                        removeOption.setVisible(false);
                        customizeOption.setVisible(false);
                        edit.setVisible(false);
                        completeCoor.setVisible(false);
                        changeCoor.setVisible(false);

                        // reset total pin to unvisible
                        for (int i = 0; i < totalPin.size(); i++) {
                            if (totalPin.get(i).isVisible()) {

                                if ((e.getX() >= totalPin.get(i).getX() && e.getX() <= totalPin.get(i).getX() + totalPin.get(i).getSize().getWidth()) && (e.getY() >= totalPin.get(i).getY() && e.getY() <= totalPin.get(i).getY() + totalPin.get(i).getSize().getHeight())) {
                                    textFiled.repaint();
                                    userDefined.setVisible(false);
                                    userCreate.setVisible(false);

                                    showMessage(totalPin.get(i).getX(), totalPin.get(i).getY());

                                }
                            }
                        }

                        // rest search to unvisible
                        if (allSearchPin != null) {
                            for (int j = 0; j < allSearchPin.size(); j++) {
                                if (allSearchPin.get(j).isVisible()) {
                                    if ((e.getX() >= allSearchPin.get(j).getX() && e.getX() <= allSearchPin.get(j).getX() + allSearchPin.get(j).getSize().getWidth()) && (e.getY() >= allSearchPin.get(j).getY() && e.getY() <= allSearchPin.get(j).getY() + allSearchPin.get(j).getSize().getHeight())) {
                                        textFiled.repaint();
                                        userDefined.setVisible(false);
                                        userCreate.setVisible(false);
                                        showMessage(allSearchPin.get(j).getX(), allSearchPin.get(j).getY());
                                        tempUserCreated = poiInSearch;

                                    }
                                }


                            }
                        }
                        // reset all discovery pin to unvisible
                        if (discoveryPin != null) {
                            if ((e.getX() >= discoveryPin.getX() && e.getX() <= discoveryPin.getX() + discoveryPin.getSize().getWidth()) && (e.getY() >= discoveryPin.getY() && e.getY() <= discoveryPin.getY() + discoveryPin.getSize().getHeight())) {
                                textFiled.repaint();
                                userDefined.setVisible(false);
                                userCreate.setVisible(false);
                                showMessage(discoveryPin.getX(), discoveryPin.getY());
                            }

                        }
                        // reset all fav pin to univisible
                        if (allFavPin.size() > 0) {

                            for (int j = 0; j < allFavPin.size(); j++) {
                                if (allFavPin.get(j).isVisible()) {

                                    if ((e.getX() >= allFavPin.get(j).getX() && e.getX() <= allFavPin.get(j).getX() + allFavPin.get(j).getSize().getWidth()) && (e.getY() >= allFavPin.get(j).getY() && e.getY() <= allFavPin.get(j).getY() + allFavPin.get(j).getSize().getHeight())) {
                                        textFiled.repaint();
                                        userDefined.setVisible(false);
                                        userCreate.setVisible(false);
                                        showMessage(allFavPin.get(j).getX(), allFavPin.get(j).getY());
                                    }
                                }
                            }

                        }

                        // reset all created pin to unvisible
                        if (allCreatedPin.size() > 0) {

                            for (int j = 0; j < allCreatedPin.size(); j++) {
                                if (allCreatedPin.get(j).isVisible()) {

                                    if ((e.getX() >= allCreatedPin.get(j).getX() && e.getX() <= allCreatedPin.get(j).getX() + allCreatedPin.get(j).getSize().getWidth()) && (e.getY() >= allCreatedPin.get(j).getY() && e.getY() <= allCreatedPin.get(j).getY() + allCreatedPin.get(j).getSize().getHeight())) {
                                        textFiled.repaint();
                                        userDefined.setVisible(false);
                                        userCreate.setVisible(false);
                                        showMessage(allCreatedPin.get(j).getX(), allCreatedPin.get(j).getY());
                                    }
                                }


                            }


                        }

                    }


                };
                showFloor.addMouseListener(ml);
                listenEachFloor.add(ml);


                frame.remove(toolBar);
                frame.add(X);
                frame.add(Y);
                frame.add(toolBar);

            } catch (Exception e) {

            }
        }
    }

    /**
     * check search bar input, return matched POI list, or null
     * This method checks the user's search input and returns an array of POI objects that match the search criteria.
     * @return an array of POI objects that match the search criteria, or null if no POI objects were found or the search input was invalid.
     */
    private POI[] checkSearchInput(){

        ArrayList<POI> temp = new ArrayList<>();
        String input = searchBar.getText();
        if(input.equals("") || input.equals("Search POI")){
            invalidInput.setVisible(true);
            return null;
        }
        // search each built in poi
        for(POI thisPOI:Main.allPOI){
            if(thisPOI.getBuilding().equals(whichBuilding)){
                if(thisPOI.getFloor() == whichFLoor){
                    if(thisPOI.getName().equalsIgnoreCase(input)
                            || thisPOI.getName().toUpperCase().contains(input.toUpperCase())
                            || thisPOI.getRoomNumber().equalsIgnoreCase(input)
                            || thisPOI.getDescription().equalsIgnoreCase(input)
                            || thisPOI.getDescription().toUpperCase().contains(input.toUpperCase())){
                        temp.add(thisPOI);

                    }
                }
            }

        }

        // search each user created poi
        for(POI thisPOI:allUserCreated){
            if(thisPOI.getBuilding().equals(whichBuilding)){
                if(thisPOI.getFloor() == whichFLoor){
                    if(thisPOI.getName().equalsIgnoreCase(input)
                            || thisPOI.getName().toUpperCase().contains(input.toUpperCase())
                            || thisPOI.getRoomNumber().equalsIgnoreCase(input)
                            || thisPOI.getDescription().equalsIgnoreCase(input)
                            || thisPOI.getDescription().toUpperCase().contains(input.toUpperCase())){
                        temp.add(thisPOI);

                    }
                }
            }

        }

        // if no match result
        if(temp.isEmpty()){
            noMatchResult.setVisible(true);
            return null;
        }
        POI[] result = new POI[temp.size()];
        for(int i =0; i < temp.size(); i++){
            result[i] = temp.get(i);
        }
        return result;

    }

    /**
     * Unselects all layers and hides all pins.
     */
    private void unselectAllLayer(){
        classroom.setSelected(false);
        lab.setSelected(false);
        resturant.setSelected(false);
        collaborative.setSelected(false);
        userDefine.setSelected(false);
        userFav.setSelected(false);

        for(JLabel aPin: pinClassroom){
            aPin.setVisible(false);
        }

        for(JLabel aPin: pinLab){
            aPin.setVisible(false);
        }

        for(JLabel aPin: pinRestaurant){
            aPin.setVisible(false);
        }

        for(JLabel aPin: pinCollaborative){
            aPin.setVisible(false);
        }

    }

    /**
     * clear search bar history
     */
    private void clearSearch(){
        searchBar.setText("Search POI");
        searchBar.setFont(new Font("Tahoma", Font.ITALIC, 11));
        searchBar.setForeground(Color.GRAY);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearFocusOwner();
        noMatchResult.setVisible(false);
        invalidInput.setVisible(false);

        if(allSearchPin != null){
            for(int i = 0; i < allSearchPin.size(); i++){
                allSearchPin.get(i).setVisible(false);
            }
        }

    }

    /**
     * clear selected discover POI pin, set select item to default
     */
    private void clearSelectDiscovery(){
        if(discoveryPin != null){
            discoveryPin.setVisible(false);
        }
        if(allPOIInThisMap != null && allPOIInThisMap.getItemCount() != 0 ){
            allPOIInThisMap.setSelectedIndex(0);
        }
    }

    /**
     * This method is used to display the name, room number and description of a Point of Interest (POI) or a user-created location
     * based on the given coordinates.
     * @param coordinateX the X-coordinate of the location on the map
     * @param coordinateY the Y-coordinate of the location on the map
     */
    public void showMessage(int coordinateX, int coordinateY){
        userCreate.setVisible(false);
        userDefined.setVisible(false);

        // find the building and floor
        for(int j=0;j<mapList.size();j++)
        {
            if(mapList.get(j).isVisible())
            {
                // find from built-in poi
                for(POI poi: Main.allPOI)
                {
                    if(poi.getFloor()==j+1 && poi.getBuilding().equals(whichBuilding) && poi.getX()-30==coordinateX && poi.getY()-35==coordinateY)
                    {
                        newName.setText("  Name: "+ poi.getName());
                        newNumber.setText("  Room Number: "+ poi.getRoomNumber());
                        Description.setText("  Descritpion: "+ poi.getDescription());
                        newName.setBounds(0,10,200,20);
                        newNumber.setBounds(0,40,200,20);
                        Description.setBounds(0,70,400,20);
                        poiInSearch = poi;

                        if(allFavourite.size()==0)
                        {
                            favouriteOption.setVisible(true);
                            removeOption.setVisible(false);
                            tempFav=poi;
                        }
                        // if it's fav, show the button
                        else {
                            boolean find=false;
                            for (int n = 0; n < allFavourite.size(); n++) {
                                if (allFavourite.get(n).checkEqual(allFavourite.get(n), poi)) {


                                    removeOption.setVisible(true);
                                    favouriteOption.setVisible(false);

                                    tempDele = poi;

                                    find=true;

                                }

                            }
                            if(find==false) {
                                favouriteOption.setVisible(true);
                                removeOption.setVisible(false);

                                tempFav = poi;
                            }
                        }
                        textFiled.add(newName);
                        textFiled.add(newNumber);
                        textFiled.add(Description);
                    }

                }
                // find from user created poi
                for(int k=0;k<allUserCreated.size();k++)
                {
                    if(allUserCreated.get(k).getFloor()==j+1&&allUserCreated.get(k).getBuilding().equals(whichBuilding)&&allUserCreated.get(k).getX()-30==coordinateX&&allUserCreated.get(k).getY()-35==coordinateY)
                    {
                        newName.setText("  Name: "+allUserCreated.get(k).getName());
                        newNumber.setText("  Room Number: "+allUserCreated.get(k).getRoomNumber());
                        Description.setText("  Descritpion: "+allUserCreated.get(k).getDescription());
                        newName.setBounds(0,10,200,20);
                        newNumber.setBounds(0,40,200,20);
                        Description.setBounds(0,70,400,20);
                        tempUserCreated=allUserCreated.get(k);

                        poiInSearch = allUserCreated.get(k);
                        if(allFavourite.size()==0)
                        {
                            favouriteOption.setVisible(true);
                            removeOption.setVisible(false);
                            customizeOption.setVisible(true);
                            edit.setVisible(true);
                            changeCoor.setVisible(true);

                            tempFav=allUserCreated.get(k);
                        }
                        else {
                            // find it's in fav or not
                            boolean find=false;
                            for (int n = 0; n < allFavourite.size(); n++) {
                                if (allFavourite.get(n).checkEqual(allFavourite.get(n), allUserCreated.get(k))) {

                                    removeOption.setVisible(true);
                                    favouriteOption.setVisible(false);
                                    customizeOption.setVisible(true);
                                    edit.setVisible(true);
                                    changeCoor.setVisible(true);
                                    tempDele = allUserCreated.get(k);

                                    find=true;

                                }

                            }
                            if(find==false) {
                                favouriteOption.setVisible(true);
                                removeOption.setVisible(false);
                                customizeOption.setVisible(true);
                                edit.setVisible(true);
                                changeCoor.setVisible(true);
                                tempFav = allUserCreated.get(k);
                            }
                        }

                        textFiled.add(newName);
                        textFiled.add(newNumber);
                        textFiled.add(Description);

                    }

                }
            }
        }
    }

    /**
     * Clears the information by hiding message and unselecting all layers.
     */
    private void clearInfor(){
        hideMessage();
        unselectAllLayer();
    }

    /**
     * This method hides all the information related to a contact.
     * Specifically, it clears the text fields for name, number and description,
     * and sets the visibility of several buttons and options to false,
     * so that they are not displayed to the user anymore.
     */
    private void hideMessage(){
        newName.setText(" ");
        newNumber.setText(" ");
        Description.setText(" ");
        favouriteOption.setVisible(false);
        removeOption.setVisible(false);
        userDefined.setVisible(false);
        userCreate.setVisible(false);
        customizeOption.setVisible(false);
        edit.setVisible(false);
        changeCoor.setVisible(false);
        completeCoor.setVisible(false);
    }

    /**
     * Refreshes the favorite list by getting the user's favorite list and adding it to the menu bar.
     * Also adds action listeners to each item in the favorite list.
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the database driver class is not found
     */
    public void refreshFavorite() throws SQLException, ClassNotFoundException {

        // refresh favorite list
        favoriteMenu = new JMenu("Favorite");
        favoriteMenu.removeAll();
        userFavorite thisUserFavorite = new userFavorite();
        favoriteMenu = thisUserFavorite.getUserFavorite();

        if(menuBar.getMenuCount() >= 3){
            menuBar.removeAll();
            menuBar.add(selectBuilding);
            menuBar.add(favoriteMenu);
            menuBar.add(help);
            menuBar.add(about);
        }
        frame.repaint();



        // add action listener to each item
        for(int k = 0; k < favoriteMenu.getMenuComponents().length; k++){
            JMenu buildingItem = (JMenu) favoriteMenu.getItem(k);

            for(int j = 0; j < buildingItem.getItemCount(); j++){
                JMenu floorItem = (JMenu) buildingItem.getItem(j);
                for(int l = 0; l < floorItem.getItemCount(); l++){
                    floorItem.getItem(l).addActionListener(this);
                }
            }
        }
    }

    /**
     * This method jumps to a specified favorite point of interest (POI) by setting the appropriate building, floor, and POI name. It also displays the map of the specified floor, selects the current floor on the floor selection combobox, and adds all POIs in the current map.
     * @param POIname a string representing the name of the favorite POI to jump to
     * @param floor a string representing the floor of the favorite POI to jump to
     * @param building a string representing the building of the favorite POI to jump to
     * @throws SQLException if there is an error executing an SQL statement
     * @throws ClassNotFoundException if the JDBC driver class cannot be found
     */
    private void jumpToFav(String POIname, String floor, String building) throws SQLException, ClassNotFoundException {

        forgotComplete();
        whichBuilding = building;
        floor = floor.replaceAll("[^0-9]", "");

        whichFLoor = Integer.parseInt(floor);

        clearInfor();
        unselectAllLayer();

        numButton = findFloor(whichBuilding);

        addEachFloor(whichBuilding,numButton, false);
        currentBuilding.setText("  " + whichBuilding);

        // change this map visible
        mapList.get(whichFLoor - 1).setVisible(true);

        // change select floor combobox to current floor
        eachFloor.setSelectedIndex(whichFLoor);

        // add all POI
        String[] allPOIInThisMapStr = new String[0];
        allPOIInThisMapStr = discoveryPOI(whichBuilding);
        allPOIInThisMap.removeAllItems();
        allPOIInThisMap.addItem("Please select");
        for(int i = 0; i < allPOIInThisMapStr.length; i++){
            String temp = allPOIInThisMapStr[i];
            allPOIInThisMap.addItem(temp);
        }


        // find the favorite POI
        int x = 0;
        int y = 0;
        for(POI thisPOI:allFavourite){
            if(thisPOI.getBuilding().equals(whichBuilding) &&thisPOI.getFloor() == whichFLoor&&thisPOI.getName().equals(POIname)){

                x = thisPOI.getX();
                y = thisPOI.getY();
                tempUserCreated = thisPOI;
                break;

            }
        }
        showMessage(x-30,y-35);


        for(int i=0;i<allFavPin.size();i++)
        {
            if(allFavPin.get(i).isVisible()==false){
                allFavPin.get(i).setLocation(x-30,y-35);
                allFavPin.get(i).setSize(50, 50);
                floorList.get(whichFLoor - 1).add(allFavPin.get(i));
                allFavPin.get(i).setVisible(true);
                break;
            }
        }
        underJumpToFav = true;


    }

    /**
     * Clears the favorite pins from the selected floor and hides the favorite pin in the menu bar.
     * If the selected floor is less than or equal to zero, the method does nothing.
     * Otherwise, it removes all favorite pins from the floor and updates the frame to reflect the changes.
     * Finally, if the favorite pin in the menu bar exists, it is set to be invisible.
     */
    private void clearFav(){
        if(whichFLoor <= 0){

        }
        else{
            floorList.get(whichFLoor - 1).removeAll();
            frame.repaint();
        }

        if(theFavPinInMenuBar != null){
            theFavPinInMenuBar.setVisible(false);
        }

    }


    /**
     * Refreshes all the pins on the map based on the current set of user-created labels and favourite labels.
     * Throws SQLException if there is an error executing a SQL statement and ClassNotFoundException if a
     * specified class cannot be found.
     * @throws SQLException if there is an error executing a SQL statement.
     * @throws ClassNotFoundException if a specified class cannot be found.
     */
    private void refreshAllLayer() throws SQLException, ClassNotFoundException {

        createPin newAllpin = new createPin(allUserCreated, allFavourite, Main.allPOI);

        // if add a new label, update the classroom pin
        for(POI a : newAllpin.getPOIClassroom()){
            for(POI b: allPin.getPOIClassroom()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }

            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            pinClassroom.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));

        }

        // if delete a label, update the classroom pin
        for(int i = 0; i < allPin.getPOIClassroom().size(); i++){
            JLabel temp = pinClassroom.get(i);
            for(POI b: newAllpin.getPOIClassroom()){
                if(allPin.getPOIClassroom().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOIClassroom().get(i).getFloor() == b.getFloor()
                        && allPin.getPOIClassroom().get(i).getX() == b.getX() && allPin.getPOIClassroom().get(i).getY() == b.getY()){
                    break;
                }

            }
            temp.setVisible(false);
            pinClassroom.remove(temp);
            totalPin.remove(temp);

        }

        // if add a new label, update the lab pin
        for(POI a : newAllpin.getPOILab()){
            for(POI b: allPin.getPOILab()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }
            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            pinLab.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));
        }


        /** if delete a label, update the lab pin */
        for(int i = 0; i < allPin.getPOILab().size(); i++){
            JLabel temp = pinLab.get(i);
            for(POI b: newAllpin.getPOILab()){
                if(allPin.getPOILab().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOILab().get(i).getFloor() == b.getFloor()
                        && allPin.getPOILab().get(i).getX() == b.getX() && allPin.getPOILab().get(i).getY() == b.getY()){
                    break;
                }

            }
            temp.setVisible(false);
            pinLab.remove(temp);
            totalPin.remove(temp);

        }


        // if add a new label, update the restaurant pin/
        for(POI a : newAllpin.getPOIRestaurant()){
            for(POI b: allPin.getPOIRestaurant()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }

            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            pinRestaurant.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));

        }

        // if delete a label, update the restaurant pin
        for(int i = 0; i < allPin.getPOIRestaurant().size(); i++){
            JLabel temp = pinRestaurant.get(i);
            for(POI b: newAllpin.getPOIRestaurant()){
                if(allPin.getPOIRestaurant().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOIRestaurant().get(i).getFloor() == b.getFloor()
                        && allPin.getPOIRestaurant().get(i).getX() == b.getX() && allPin.getPOIRestaurant().get(i).getY() == b.getY()){
                    break;
                }

            }
            temp.setVisible(false);
            pinRestaurant.remove(temp);
            totalPin.remove(temp);
        }


        // if add a new label, update the collaborative pin
        for(POI a : newAllpin.getPOICollab()){
            for(POI b: allPin.getPOICollab()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }

            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            pinCollaborative.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));
        }

        // if delete a label, update the collaborative pin
        for(int i = 0; i < allPin.getPOICollab().size(); i++){
            JLabel temp = pinCollaborative.get(i);
            for(POI b: newAllpin.getPOICollab()){
                if(allPin.getPOICollab().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOICollab().get(i).getFloor() == b.getFloor()
                        && allPin.getPOICollab().get(i).getX() == b.getX() && allPin.getPOICollab().get(i).getY() == b.getY()){
                    break;
                }
            }
            temp.setVisible(false);
            pinCollaborative.remove(temp);
            totalPin.remove(temp);
        }


        // if add a new label, update the defined pin
        for(POI a : newAllpin.getPOIUserDefine()){
            for(POI b: allPin.getPOIUserDefine()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }

            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            allCreatedPin.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));
        }

        // if delete a label, update the collaborative pin
        for(int i = 0; i < allPin.getPOIUserDefine().size(); i++){
            JLabel temp = allCreatedPin.get(i);
            for(POI b: newAllpin.getPOIUserDefine()){
                if(allPin.getPOIUserDefine().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOIUserDefine().get(i).getFloor() == b.getFloor()
                        && allPin.getPOIUserDefine().get(i).getX() == b.getX() && allPin.getPOIUserDefine().get(i).getY() == b.getY()){
                    break;
                }

            }
            temp.setVisible(false);
            allCreatedPin.remove(temp);
            totalPin.remove(temp);
        }

        // if add a new label, update the fav pin
        for(POI a : newAllpin.getPOIFav()){
            for(POI b: allPin.getPOIFav()){
                if(a.getBuilding().equals(b.getBuilding()) && a.getFloor() == b.getFloor()
                        && a.getX() == b.getX() && a.getY() == b.getY()){
                    break;
                }

            }
            ArrayList<POI> temp = new ArrayList<>();
            temp.add(a);
            createPin tempNewPin = new createPin(temp);
            allFavPin.add(tempNewPin.getSearch().get(0));
            totalPin.add(tempNewPin.getSearch().get(0));
        }

        // if delete a label, update the collaborative pin
        for(int i = 0; i < allPin.getPOIFav().size(); i++){
            JLabel temp = allFavPin.get(i);
            for(POI b: newAllpin.getPOIFav()){
                if(allPin.getPOIFav().get(i).getBuilding().equals(b.getBuilding()) && allPin.getPOIFav().get(i).getFloor() == b.getFloor()
                        && allPin.getPOIFav().get(i).getX() == b.getX() && allPin.getPOIFav().get(i).getY() == b.getY()){
                    break;
                }

            }
            temp.setVisible(false);
            allFavPin.remove(temp);
            totalPin.remove(temp);
        }
    }

    /**
     * This method is called when the mouse is moved while over the component that has registered this listener.
     * @param me the MouseEvent that triggered this method
     */
    public void mouseMoved(MouseEvent me){

    }

    /**
     * Called when a mouse button is pressed on the component and then dragged.
     * This method updates the position of a component that is being dragged by the user.
     * @param me the MouseEvent that occurred
     */
    public void mouseDragged(MouseEvent me){

        tempMoving.setBounds( tempMoving.getX()+me.getX()-25, tempMoving.getY()+me.getY()-25, 50, 50);
    }

    /**
     * Updates the coordinates of a point of interest created by the user in the database.
     * @param a the point of interest to be edited
     * @param newX the new X coordinate of the point of interest
     * @param newY the new Y coordinate of the point of interest
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    private void editUserCreatedCoordinate(POI a,int newX,int newY)throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        updateSQL = "UPDATE usercreated SET Xcoordinate="+"'"+newX+"'"+",Ycoordinate ="+"'"+newY+"'"+"WHERE building =" + "'" + a.getBuilding() + "'" + "AND Floor =" + "'" + a.getFloor() + "'" + "AND Name =" + "'" + a.getName() + "'"+"AND userAccount ="+"'"+login.thisUsername + "'";
        ps = conn.prepareStatement(updateSQL);
        ps.executeUpdate();
        ps.close();
        stmt.close();
        conn.close();

    }

    /**
     * Updates the coordinates of a point of interest saved as a favorite by the user in the database.
     * @param a the point of interest to be edited
     * @param newX the new X coordinate of the point of interest
     * @param newY the new Y coordinate of the point of interest
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    private void editUserFavCoordinate(POI a,int newX,int newY)throws SQLException, ClassNotFoundException{
        Class.forName(JDBC_DRIVER);
        conn1 = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn1.createStatement();
        updateSQL = "UPDATE userfavourite SET Xcoordinate="+"'"+newX+"'"+",Ycoordinate ="+"'"+newY+"'"+"WHERE building =" + "'" + a.getBuilding() + "'" + "AND Floor =" + "'" + a.getFloor() + "'" + "AND Name =" + "'" + a.getName() + "'"+"AND userAccount ="+"'"+login.thisUsername + "'";
        ps = conn1.prepareStatement(updateSQL);
        ps.executeUpdate();
        ps.close();
        stmt.close();
        conn1.close();

    }

    /**
     * This method is used to hide all the overlapping pins of the same location on the map by removing them from the visible pin list.
     * It then checks for all the visible pins of different categories such as favorite, created, classroom, lab, restaurant, collaborative and
     * adds them to the visible pin list. It then checks for overlapping pins and hides the duplicates.
     */
    private void hiddenSamePin(){

        int rmSize = allVisiablePin.size();
        if(allVisiablePin.size() > 0){
            for(int i = rmSize; i > 0 ; i--){
                allVisiablePin.remove(i-1);

            }
        }

        for(JLabel a: allFavPin){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        for(JLabel a: allCreatedPin){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }

        for(JLabel a: pinClassroom){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        for(JLabel a: pinLab){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        for(JLabel a: pinRestaurant){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        for(JLabel a: pinCollaborative){
            if(a.isVisible() && a.getX() != 0 && a.getY() != 0){
                allVisiablePin.add(a);
            }
        }
        if(discoveryPin != null){
            allVisiablePin.add(discoveryPin);
        }


        pinX.clear();
        pinY.clear();

        for(JLabel thisPin : allVisiablePin){

            if(pinX.contains(thisPin.getX()) == true){
                if(pinY.contains(thisPin.getY())){
                    thisPin.setVisible(false);
                }
                else{
                    pinX.add(thisPin.getX());
                    pinY.add(thisPin.getY());
                }

            }
            else{
                pinX.add(thisPin.getX());
                pinY.add(thisPin.getY());
            }
        }
    }


    /**
     * This method is used to hide all the JLabels representing pins in the map.
     * It iterates through all the JLabels of each category and sets their visibility to false.
     * Categories include favorites, created, classroom, lab, restaurant, and collaborative pins.
     */
    private void hideAllLayerPin(){

        for(JLabel a: allFavPin){
            a.setVisible(false);
        }
        for(JLabel a: allCreatedPin){
            a.setVisible(false);
        }

        for(JLabel a: pinClassroom){
            a.setVisible(false);
        }
        for(JLabel a: pinLab){
            a.setVisible(false);
        }
        for(JLabel a: pinRestaurant){
            a.setVisible(false);
        }
        for(JLabel a: pinCollaborative){
            a.setVisible(false);
        }
    }


    /**
     * This method is called when the user completes the "forgot" action.
     * If both the temporary moving object and temporary floor object are not null,
     * it removes the mouse motion listener from the temporary moving object and adds
     * the mouse listener to the temporary floor object.
     */
    private void forgotComplete(){
        if(tempMoving!=null&&tempFloor!=null) {
            tempMoving.removeMouseMotionListener(this);
            tempFloor.addMouseListener(tempListerner);
        }
    }

}

