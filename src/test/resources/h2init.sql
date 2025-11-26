DROP ALL OBJECTS;

CREATE TABLE Permissions(
                             PermissionID    INT NOT NULL AUTO_INCREMENT,
                             Name VARCHAR(255) UNIQUE NOT NULL,
                             Description VARCHAR(255),
                             PRIMARY KEY (PermissionID)
);

CREATE TABLE Roles (
                       RoleID    INT NOT NULL AUTO_INCREMENT,
                       Name VARCHAR(255) UNIQUE NOT NULL,
                       Description VARCHAR(255),
                       PRIMARY KEY (RoleID)
);


CREATE TABLE RolePermissions (
                                 RoleID         INT NOT NULL,
                                 PermissionID       INT NOT NULL,
                                 PRIMARY KEY (RoleID, PermissionID),
                                 FOREIGN KEY (RoleID) REFERENCES Roles (RoleID)
                                     ON DELETE CASCADE,
                                 FOREIGN KEY (PermissionID) REFERENCES Permissions (PermissionID)
                                     ON DELETE CASCADE
);

CREATE TABLE Projects (
                          ProjectID INT NOT NULL AUTO_INCREMENT,
                          Name VARCHAR(255) UNIQUE NOT NULL,
                          Description VARCHAR(255),
                          IsActive BOOLEAN NOT NULL,
                          StartDate DATE NOT NULL,
                          Deadline DATE NOT NULL,
                          PRIMARY KEY (ProjectID)
);

CREATE TABLE Accounts (
                          AccountID INT NOT NULL AUTO_INCREMENT,
                          Name VARCHAR(255) NOT NULL,
                          Email VARCHAR(255),
                          Birthday DATE NOT NULL default ('02-02-02'),
                          Number VARCHAR(24) NOT NULL default('12345678'),
                          WeeklyHours INT NOT NULL,
                          Password VARCHAR(255) NOT NULL,
                          RoleID INT NOT NULL,
                          PRIMARY KEY (AccountID),
                          FOREIGN KEY (RoleID) REFERENCES Roles (RoleID)
                              ON DELETE CASCADE
);


CREATE TABLE ProjectMembers (
                                ProjectID INT NOT NULL,
                                AccountID INT NOT NULL,
                                PRIMARY KEY (ProjectID, AccountID),
                                FOREIGN KEY (ProjectID) REFERENCES Projects (ProjectID)
                                    ON DELETE CASCADE,
                                FOREIGN KEY (AccountID) REFERENCES Accounts (AccountID)
                                    ON DELETE CASCADE
);

CREATE TABLE Tasks (
                       TaskID INT NOT NULL AUTO_INCREMENT,
                       Name VARCHAR(255) NOT NULL,
                       Description VARCHAR(255),
                       HourEstimate INT,
                       Completed BOOLEAN NOT NULL,
                       Deadline DATE,
                       StartDate DATE,
                       ProjectID INT NOT NULL,
                       ParentID INT,
                       UNIQUE(Name, ProjectID),
                       PRIMARY KEY (TaskID),
                       FOREIGN KEY (ProjectID) REFERENCES Projects (ProjectID)
                           ON DELETE CASCADE
);

CREATE TABLE TaskList (
                          AccountID INT NOT NULL,
                          TaskID INT NOT NULL,
                          PRIMARY KEY (AccountID, TaskID),
                          FOREIGN KEY (AccountID) REFERENCES Accounts (AccountID)
                              ON DELETE CASCADE,
                          FOREIGN KEY (TaskID) REFERENCES Tasks (TaskID)
                              ON DELETE CASCADE
);


INSERT INTO Permissions (Name, Description) VALUES('View', 'Gives the user the right to view a project');
INSERT INTO Roles (Name, Description)
VALUES('Scrum master', 'Gives the user the rights to modify tasks within a project');
INSERT INTO RolePermissions
VAlUES (1,1);
INSERT INTO Accounts (Name, Email, WeeklyHours, Password, RoleID)
VALUES ('Markus Addington', 'addington@icloud.com', 42, 'asdj3(Fsah2', 1);
INSERT INTO Accounts (Name, Email, WeeklyHours, Password, RoleID)
VALUES ('Markus Substractington', 'substractington@icloud.com', 42, 'asdj3(Fsah2', 1);
INSERT INTO Projects (Name, Description, isActive, StartDate, Deadline)
VALUES ('Ecommerce platform for _Adaptive Problemsolvers_', '_Adaptive Probelmsolvers_ are in need of a brand new platform to run ecommerce. They have re...', true, '2025-12-06', '2026-03-12');
INSERT INTO Tasks (Name, Description,HourEstimate, Completed, StartDate, Deadline, ProjectID, ParentID)
VALUES ('Web application', '_Adaptive Probelmsolvers_ need a new webplatform. It nee...',50, true, '2025-12-06', '2026-01-28', 1, 0);
INSERT INTO Tasks (Name, Description,HourEstimate, Completed, StartDate, Deadline, ProjectID, ParentID)
VALUES ('Web application Part 2', '_Adaptive Probelmsolvers_ need a new webplatform. It nee...',50, true, '2025-12-06', '2026-01-28', 1, 0);
INSERT INTO tasklist
VALUES (1, 1),
       (1, 2),
       (2,1);
INSERT INTO Tasks (Name, Description,HourEstimate, Completed, StartDate, Deadline, ProjectID, ParentID)
VALUES ('Web application Part 2 Part 1', '_Adaptive Probelmsolvers_ need a new webplatform. It nee...',50, true, '2025-12-06', '2026-01-28', 1, 2);
INSERT INTO Tasks (Name, Description,HourEstimate, Completed, StartDate, Deadline, ProjectID, ParentID)
VALUES ('Web application Part 2 Part 2', '_Adaptive Probelmsolvers_ need a new webplatform. It nee...',50, true, '2025-12-06', '2026-01-28', 1, 2);
INSERT INTO Tasks (Name, Description,HourEstimate, Completed, StartDate, Deadline, ProjectID, ParentID)
VALUES ('Web application Part 1 Part 1', '_Adaptive Probelmsolvers_ need a new webplatform. It nee...',50, true, '2025-12-06', '2026-01-28', 1, 1);