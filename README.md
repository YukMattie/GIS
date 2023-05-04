## University of Western Ontario Map Systerm(R) Version 1.0 02/04/2023

### 1>  Description

The GIS map of UWO buildings is a software application designed to provide an interactive map of the University of Western Ontario's campus buildings. This software aims to solve the problem of navigating the campus efficiently by providing detailed and accurate location information for all the buildings.

The requirements of the system include accurate mapping of all the buildings, integration with other campus systems, and user-friendly interface. The testing documentation ensures that these requirements are met and that the system is fully functional and reliable.

To ensure the reliability and functionality of the software, a comprehensive testing documentation has been created. The testing documentation covers all aspects of the system, including functional, performance, security, and usability testing.

One of the issues that have been identified in the software is the inability to delete a user's point of interest (poi) and recreate it in the same location without having to log in again. To address this issue, functional testing has been conducted to ensure that the delete and recreate functionality works as expected, without requiring users to log in again.

Another issue identified is that the search results can only show one pin, and users cannot access the map without an internet connection. To ensure that users can access the map and receive accurate search results, performance testing has been conducted to test the software's ability to function with limited or no internet connectivity.

Furthermore, clicking the "add" button does not display the "delete" option in the interface. Usability testing has been conducted to ensure that the user interface is user-friendly and easy to navigate, with all necessary options visible and accessible.

Additionally, the login window does not close automatically, which could cause frustration for users. To address this issue, usability testing has been conducted to ensure that the login window automatically closes once the user has successfully logged in.

Lastly, to enhance the software's security, encryption of user names has been implemented. Security testing has been conducted to ensure that user information is securely encrypted and stored to protect user privacy.

Overall, the testing documentation for the GIS map of buildings software provides a robust testing framework that ensures the software's quality, reliability, and accuracy. This will enable users to navigate the campus with ease and confidence, improving the overall campus experience for all.

### 2 > Required external libriries

#### 2.1 java-jason.jar(Version: 20230227)

#### 2.2 java-simple-1.1.jar(Version: 1.1)

#### 2.3 mysql-connector-java-8.0.16.jar(Version: 8.0.16)

### 3> Complie from the source code(Based on IntelliJ IDEA Community Edition 2022.2.3)

Before run the source code, you should make sure you installed the mysql database. Go to the project folder, you will find a folder called install_database folder. Run the mysql-installer-community-8.0.31.0.msi file as Admin. During the installation, you just need to keep all of the setting as default and make sure the account name should be root and the password for the database should be CS2212. After DB installed, run the MySQL 8.0 Command Line Client - Unicode. It will ask you login to your database first. Type the password CS2212, it will login. Go to the install_database folder again, you will find a table.sql file, now we need to execute this .sql file in command line to create all tables you will use in this program. Type SOURCE absolute file path of this .sql file(e.g SOURCE C:/UwoMapSystem/install_database/table.sql) and hit enter, it will auto generate all the tables you need. After executing successfully, you can type USE mapdb and then type SHOW TABLES;, it will show all of the tables in you database. After setting the project environment, you can open IntelliJ IDEA.

First make sure the JDK version must be 19.0. Open IDEA and Click the file on the Menu bar.Select open, and find the project folder, then open that project. After loading the project,click file on the Menu bar again and select Project Structure, in the project structure, click dependencies, them click the + icon, select JAR or directories. Go to the project folder of this program, you will find three .jar file in the external_libraries directory. Import these three .jar files step by step. Then after importing, you will find them in the list of the dependencies, select the check box and click apply. These three external libraries will be installed successfully.Then you can just run the Main.java, the program will run. You will see a login window, if you want to creare your own account, you can hit the Don't have account button. We also provide you two account,go to section 6 to check the default account and password. After you type in the user name and password,you can click the login button, you will login the program sccessfully.

### 4> Run built (compiled) software.

Assume you have inistalled the mysql environment, if not, go back to section 3 to chekc how to install the mysql. Go to the project folder, in the root folder you will find a GIS.jar file.

Right click the jar file, select open with/choose another app. And then select look for another app on this PC, go to Program Files/jdk-19.0.2/bin, select javaw.exe and click OK.

It will open the project. Then you will see the login page with a bachground picture of middlesex college like this:

![image-20230405155149879](/Users/nono/Library/Application Support/typora-user-images/image-20230405155149879.png)

Then you can go to section 6 to check the default account and password we provided, and login to the system.

If your login page doesn't look like this or lose the background picture, you may choose the wrong JDK. You should follow the step 4 and try again.

### 5> Basic Fuctions of the program

After you login, you can see the current building is Middlesex College. If you go to Menu Bar in the top of the frame,You can choose to swich to different building. Then in the left panel, there is a combox which include all of the floors in this building, select the floor you want to browse and click go button, a map in a scroll panel will show up in the middle of the frame. In the right side, there is a search bar, couple checkbox to show and hide different layers in this map and the other combox which includes all poi in this map. First, you can type any information in the search bar, if the info you type matched any POI in this map, it will drop a pin in the map. Using the scroll panel to move to the location of the POI, you will see the pin and if you click the pin, it will show some information of this location under the scroll panel,if the POI is a builtin POI, you can set the POI as your own favourite. If the POI is the user defined POI, you can move this POI, edit the basic infor of this POI, set as favourite and move the POI to new location.

(Hint for changing the location:When youclick the change coordinate button, you can move the pin, but you could not create the new user defined POI. When you move the pinto the new place, you must click the complete button. Then the program will go back to the normal mode, you can click on the map again).

The checkbox can allow user to hide or show multi layer in this map, when the pin drop, you can also click the pin and do some modify for this POI. Then the combox called Discovery POI also can show the certaion POI in this map. If you want to create you own POI, just click any space in the map, it will ask you if you want to create a user defined POI in this map, if you click create button, it will ask you to enter the basic information and then create a new user defined POI. On the menu bar, there is also a favourite option, it will give you all your favourite POI which you save in this program, and if you select a favourite POI, it will jump to the building and floor which that POI located. If you still have somequestion for this program, you can also click the help option on the menu bar.

### 6> Default User Account

Normal User. User name: User , Password: User

Developer.   User name: Developer, Password: Developer

### 7> Develope Mode

If you have a develop account, when you are in login page, select the operator type to developer,and type the user name and password. The develop mode is quite similar with the user mode.On the menu bar, you can switch to different building. And on the left side, the combox include all floor in this building. The combox on the right side includes all builtin POI in this map. Also, when you click on the map, it will ask you if you want to create a builtin POI in this map. When you show a builtin POI, you can also click it and allow you to edit the information of the POI,delete the builtin POI change the location of this POI 

(Hint for changing the location:When you click the change coordinate button, you can move the pin, but you could not create the new user defined POI. When you move the pin to the new place, you must click the complete button. Then the program will go back to the normal mode, you can click on the map again).

### 8> Conatct

Program can be reached at:azheng45@uwo.ca,wli732@uwo.ca,yxiao442@uwo.ca,zyang382@uwo.ca,ypan377@uwo.ca.
