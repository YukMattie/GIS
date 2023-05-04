import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;


/**
 * This is the login page to the map system
 * Inputs Data base from MYSQL
 * verify user password
 * create new account
 * portal to open user mode and developer mode
 * @author Anny Zheng
 * @author  Yi Xiao
 * @version 1.0
 */
public class login implements ActionListener {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/MAPDB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "CS2212";

    private static Connection conn;
    private Statement stmt = null;
    private PreparedStatement ps = null;
    private static ResultSet rsPOI;
    private static String userCreateSQL = "SELECT * FROM userCreated WHERE userAccount = ";
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel label;
    private static JLabel pw;
    private static JTextField accountText;
    private static JTextField passwordText;
    private static JLabel wrongLogin;
    private static JLabel picture;
    private static JButton create;
    private static JLabel operatorType;
    private static JComboBox operator;
    private static JButton login;

    public static ArrayList<POI> allUserCreatePOI  = new ArrayList<>();

    //the user's username
    public static String thisUsername;

    /**
     * constructor for login class, creates a new login object
     */
    public login() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        /** frame initialize */
        frame = new JFrame();
        panel = new JPanel();
        frame.setSize(760, 500);// setup frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Western Map System");
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        /** panel initialize */
        panel.setBounds(210, 90, 320, 320);
        panel.setBackground(Color.white);
        frame.add(panel);
        frame.setResizable(false);
        panel.setLayout(null);

        label = new JLabel("Account");
        label.setBounds(35, 20, 60, 25);
        accountText = new JTextField();
        accountText.setBounds(35, 45, 250, 25);
        pw = new JLabel("Password");
        pw.setBounds(35, 80, 60, 25);
        passwordText = new JPasswordField();
        passwordText.setBounds(35, 105, 250, 25);
        /** drop down box */
        operatorType = new JLabel("Select Operator");
        operatorType.setBounds(35, 140, 125, 25);
        operator = new JComboBox<>();
        operator.addItem("User");
        operator.addItem("Developer");
        operator.setBounds(35, 165, 250, 25);
        /** don't have an account button */
        create = new JButton("Don't have an account?");
        create.setForeground(Color.blue);
        create.setBorderPainted(false);
        create.setBackground(Color.WHITE);
        create.setBounds(50, 280, 230, 15);
        create.addActionListener(this);
        /** login button */
        login = new JButton("Login");
        login.setForeground(Color.WHITE);
        login.setFont(new Font("Helvetica", Font.BOLD, 17));
        login.setBounds(100, 220, 120, 40);
        login.setBackground(new Color(79, 38, 131));
        login.addActionListener(this);
        login.setVisible(true);
        login.setOpaque(true);
        login.setBorderPainted(false);
        login.setFocusPainted(false);

        /** login info is wrong */
        wrongLogin = new JLabel("Username or Password is incorrect");
        wrongLogin.setBounds(35, 65, 250, 25);
        wrongLogin.setForeground(Color.red);
        wrongLogin.setVisible(false);
        /** add everything to the panel */

        panel.add(wrongLogin);
        panel.add(label);
        panel.add(accountText);
        panel.add(passwordText);
        panel.add(pw);
        panel.add(login);
        panel.add(create);
        panel.add(operatorType);
        panel.add(operator);

        /** read the logo file from the pictures folder */
        try {
            BufferedImage logo = ImageIO.read(new File("pictures/mc10.png"));
            picture = new JLabel(new ImageIcon(logo));
            picture.setBounds(0, 0, 760, 500);
            frame.add(picture);
        } catch (Exception e) {

        }
        frame.setVisible(true);


    }

    /**
     * uses actionlistener interface
     * take input actions from user and link to different function
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        // if user click the login button
        if (e.getSource() == login) {
            // get the username,password and operator type from the textfield
            thisUsername = accountText.getText();
            String u = accountText.getText();
            String p = passwordText.getText();
            String o = (String) operator.getSelectedItem();
            if (o.equals("User")) {
                try {
                    updatePOI();
                    UserLogin(u, p, o);
                } catch (Exception k) {

                }
            } else if (o.equals("Developer")) {
                try {
                    updatePOI();
                    developerLogin(u, p, o);
                } catch (Exception k) {

                }
            }

        } else if (e.getSource() == create) {
            wrongLogin.setVisible(false);
            createAccount ac = new createAccount();
        }

    }

    /**
     * developer login
     * if username/password is correct, log in to developermode;
     * if username/password is wrong, invoke wrongLogin
     * @param developerAccount developer account string
     * @param password  password string
     * @param type      operator type
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void developerLogin(String developerAccount, String password, String type) throws Exception {
        boolean match = false;
        // check if this username and password exists in the database
        for (users thisUser : Main.allUsers) {
            if (thisUser.getUsername().equals(developerAccount)
                    && AES.decryptPW(thisUser.getPassword()).equals(password)
                    && thisUser.getOperatorType().equals(type)) {
                frame.dispose();
                developerMode developerTool = new developerMode("Middlesex College");
                match = true;
            }
        }
        // password/username is wrong
        if (match == false) {
            wrongLogin.setVisible(true);
            //wrongLogin wl = new wrongLogin();
        }


    }

    /**
     * login for normal user
     * if username/password is correct, log in to usermode;
     * if username/password is wrong, invoke wrongLogin
     * @param userName user account string
     * @param passWord  user password string
     * @param typeOperator operator type
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private void UserLogin(String userName, String passWord, String typeOperator) throws Exception {
        boolean match = false;
        // go through all the user info in database, look for the same username and password
        for (users thisUser : Main.allUsers) {
            if (thisUser.getUsername().equals(userName)
                    && AES.decryptPW(thisUser.getPassword()).equals(passWord)
                    && thisUser.getOperatorType().equals(typeOperator)) {
                frame.dispose();
                viewMap vm = new viewMap("Middlesex College");
                match = true;
            }
        }

        if (match == false) {
            wrongLogin.setVisible(true);
            //wrongLogin wl = new wrongLogin();
        }

    }

    /**
     * updatePOI() function updates the user defined POI to all POI
     */
    private void updatePOI() throws ClassNotFoundException, SQLException {

        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        Statement stmt = null;
        stmt = conn.createStatement();
        String finalSQL = userCreateSQL+ "'" + accountText.getText() + "'";
        rsPOI = stmt.executeQuery(finalSQL);

        while (rsPOI.next()) {
            POI thisPOI = new POI(rsPOI);
            allUserCreatePOI.add(thisPOI);

        }
        rsPOI.close();
        conn.close();
        stmt.close();

    }
}



