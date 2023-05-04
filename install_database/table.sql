CREATE DATABASE mapdb;
USE mapdb;
CREATE TABLE poi(Xcoordinate INT,
                 Ycoordinate INT,
                 Name        TEXT,
                 RoomNumber  TEXT,
                 Type        TEXT,
                 isBuiltin   TEXT,
                 building    TEXT,
                 Floor       INT,
                 description TEXT);
CREATE TABLE userfavourite
				 (Xcoordinate INT,
                 Ycoordinate INT,
                 Name        TEXT,
                 RoomNumber  TEXT,
                 Type        TEXT,
                 building    TEXT,
                 Floor       INT,
                 description TEXT,
                 userAccount TEXT);
CREATE TABLE usercreated
				 (Xcoordinate INT,
                 Ycoordinate INT,
                 Name        TEXT,
                 RoomNumber  TEXT,
                 Type        TEXT,
                 building    TEXT,
                 Floor       INT,
                 description TEXT,
                 userAccount TEXT);

CREATE TABLE login(UserName TEXT,
				   PassWord TEXT,
                   OperatorType TEXT);
CREATE TABLE building(Name TEXT,
				   Floor INT);
                   
INSERT INTO building(Name,Floor) 
VALUES('Alumni Stadium',2 ),
      ('Middlesex College' ,5),
      ('Staging Building',2);

INSERT INTO poi(Xcoordinate,Ycoordinate,Name,RoomNumber,Type,isBuiltin,building,Floor,description)
VALUES(1318,1029,'Elevator','EL.A','Elevator','Yes','Alumni Stadium',1,'Elevator on ground floor of Alumni Stadium'),
      (1057,206,'Exit','EX.1','Exit','Yes','Alumni Stadium',1,'Exit of Alumini Stadiumm'),
      (1467,927,'Women''s Washroom','109','Washroom','Yes','Alumni Stadium',2,'Woman washroon of Alumni Stadium'),
      (1065,1127,'Roof Terrace','111','Other','Yes','Alumni Stadium',2,'Roof terrace of Alumni Stadium'),
      (1286,511,'Grad Club','19','Restaurant','Yes','Middlesex College',1,'Grad Club of Middlesex College'),
      (1481,727,'Elevator A','EL.A','Elevator','Yes','Middlesex College',1,'Elevator A of Middlesex College'),
      (1359,620,'Classroom 110','110','Classroom','Yes','Middlesex College',2,'Classroom 110 of Middlesex College'),
      (1117,825,'Men''s Washroom','100','Washroom','Yes','Middlesex College',2,'Men''s Washroon of Middlesex College'),
      (718,751,'Lab 240','240','Lab','Yes','Middlesex College',3,'Lab 240 of Middlesex College'),
      (510,1073,'Study Room 220','220','Collaborative Room','Yes','Middlesex College',3,'Room 240 of Middlesex College'),
      (1349,623,'Server Room','351','Other','Yes','Middlesex College',4,'Sever Room of Middlesex College'),
      (473,1126,'Media Room 325','325','Collaborative Room','Yes','Middlesex College',4,'Collaborative Room 325 of Middlesex College'),
      (2087,1014,'Elevator C','ELEV.C','Elevator','Yes','Middlesex College',5,'Elevator C of Middlesex College'),
      (701,832,'Lab 101','101','Lab','Yes','Staging Building',1,'Lab 101 of Staging Building'),
      (1265,1146,'Exit 1','EX.1','Exit','Yes','Staging Building',1,'Exit 1 of Staging Building'),
      (1178,1036,'Lab 253','253','Lab','Yes','Staging Building',2,'Lab 253 of Staging Building'),
      (1519,948,'Seminar','250','Collaborative Room','Yes','Staging Building',2,'Seminar of Staging Building'),
      (1627, 637, 'Classroom 21', '21', 'Classroom', 'Yes', 'Middlesex College', 1, 'Classroom 21 in the ground floor '),
      (742, 965, 'Lab 10', '10', 'Lab', 'Yes', 'Middlesex College', 1, 'Lab 10 in the ground floor'),
      (601, 805, 'Media Room 16B', '16B', 'Collaborative Room', 'Yes', 'Middlesex College', 1, 'Media Room 16B in the ground floor'),
      (1615, 635, 'Restaurant  112', '112', 'Restaurant', 'Yes', 'Middlesex College', 2, 'Restaurant 112 in the first floor'),
      (932, 917, 'Media Room 103C', '103C', 'Collaborative Room', 'Yes', 'Middlesex College', 2, 'Media room 103C in the first floor'),
      (1938, 959, 'Lab 123', '123', 'Lab', 'Yes', 'Middlesex College', 2, 'Lab 123 in the first floor'),
      (1257, 503, 'Restaurant 254', '254', 'Restaurant', 'Yes', 'Middlesex College', 3, 'Restaurant 254 in the second floor'),
      (1790, 880, 'Classroom 284', '284', 'Classroom', 'Yes', 'Middlesex College', 3, 'Classroom 284 in the second floor'),
      (1688, 663, 'Classroom 361', '361', 'Classroom', 'Yes', 'Middlesex College', 4, 'Classroom 361 in the third floor'),
      (955, 843, 'Lab 312', '312', 'Lab', 'Yes', 'Middlesex College', 4, 'Lab 312 in the third floor'),
      (1874, 940, 'Restaurant 385', '385', 'Restaurant', 'Yes', 'Middlesex College', 4, 'Restaurant 385 in the third floor'),
      (1239, 750, 'Classroom 10', '10', 'Classroom', 'Yes', 'Alumni Stadium', 1, 'Classroom 10 in the first floor'),
      (1472, 919, 'Tims', '8', 'Restaurant', 'Yes', 'Alumni Stadium', 1, 'Tims in the first floor'),
      (607, 347, 'Lab 31', '31', 'Lab', 'Yes', 'Alumni Stadium', 1, 'Lab 31 in the first floor'),
      (634, 972, 'Media Room 23', '23', 'Collaborative Room', 'Yes', 'Alumni Stadium', 1, 'Media room 23 in the first floor'),
      (678, 827, 'Classroom 103', '103', 'Classroom', 'Yes', 'Alumni Stadium', 2, 'Classroom 103 in the second floor'),
      (738, 960, 'Tims', '105', 'Restaurant', 'Yes', 'Alumni Stadium', 2, 'Tims in the second floor'),
      (966, 745, 'Media Room 107', '107', 'Collaborative Room', 'Yes', 'Alumni Stadium', 2, 'Media room 107 in the second floor'),
      (1000, 1166, 'Classroom 123', '123', 'Classroom', 'Yes', 'Staging Building', 1, 'Classroom 123 in the first floor'),
      (1347, 1165, 'Tims', '121', 'Restaurant', 'Yes', 'Staging Building', 1, 'Tims in the first floor'),
      (358, 1165, 'Media Room 126', '126', 'Collaborative Room', 'Yes', 'Staging Building', 1, 'Media room 126 in the first floor'),
      (2010, 1357, 'Classroom 213', '213', 'Classroom', 'Yes', 'Staging Building', 2, 'Classroom 213 in the second floor'),
      (970, 1015, 'Tims', '257', 'Restaurant', 'Yes', 'Staging Building', 2, 'Tims in the second floor'),
      (906, 647, 'Media Room 204', '204', 'Collaborative Room', 'Yes', 'Staging Building', 2, 'Media Room 204 in the second floor');
      

INSERT INTO login(UserName,PassWord,OperatorType)
VALUES('User', 'E3hi7Ck3vYlozpPaeVgRfg==','User'),
('Developer','IH3lwUyu5cQ/GidfkfHzBg==','Developer');

INSERT INTO usercreated(Xcoordinate,Ycoordinate,Name,RoomNumber,Type,building,Floor,description,userAccount)
VALUES(490, 1149, 'Lab 13', '13', 'Classroom', 'Middlesex College', 1, 'Lab 13 in the ground floor', 'User'),
	  (290, 1173, 'Classroom 105A', '105A', 'Classroom', 'Middlesex College', 2, 'Classroom 105A in the first floor', 'User'),
      (372, 1143, 'Media Room 222', '222', 'Collaborative Room', 'Middlesex College', 3, 'Media Room 222 in the second floor', 'User'),
      (294, 1172, 'Lab 326A', '326A', 'Classroom', 'Middlesex College', 4, 'Lab 326A in the third floor', 'User'),
      (625, 978, 'Bar', '23', 'Restaurant', 'Alumni Stadium', 1, 'Bar in the first floor', 'User'),
      (1497, 974, 'Bar', '110', 'Restaurant', 'Alumni Stadium', 2, 'Bar in the second floor', 'User'),
      (975, 853, 'Bar', '102B', 'Restaurant', 'Staging Building', 1, 'Bar in the first floor', 'User'),
      (408, 1184, 'Bar', '261', 'Restaurant', 'Staging Building', 2, 'Bar in the second floor', 'User');
      
INSERT INTO userfavourite(Xcoordinate,Ycoordinate,Name,RoomNumber,Type,building,Floor,description,userAccount)
VALUES('742', '965', 'Lab 10', '10', 'Lab', 'Middlesex College', '1', 'Lab 10 in the ground floor', 'User'),
      ('1359', '620', 'Classroom 110', '110', 'Classroom', 'Middlesex College', '2', 'Classroom 110 of Middlesex College', 'User'),
      ('718', '751', 'Lab 240', '240', 'Lab', 'Middlesex College', '3', 'Lab 240 of Middlesex College', 'User'),
      ('473', '1126', 'Media Room 325', '325', 'Collaborative Room', 'Middlesex College', '4', 'Collaborative Room 325 of Middlesex College', 'User'),
      ('1239', '750', 'Classroom 10', '10', 'Classroom', 'Alumni Stadium', '1', 'Classroom 10 in the first floor', 'User'),
      ('678', '827', 'Classroom 103', '103', 'Classroom', 'Alumni Stadium', '2', 'Classroom 103 in the second floor', 'User'),
      ('701', '832', 'Lab 101', '101', 'Lab', 'Staging Building', '1', 'Lab 101 of Staging Building', 'User'),
      ('970', '1015', 'Tims', '257', 'Restaurant', 'Staging Building', '2', 'Tims in the second floor', 'User');

