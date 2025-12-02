DROP DATABASE IF EXISTS `project_database_dept:ofb`;
CREATE DATABASE `project_database_dept:ofb`;
  DEFAULT CHARACTER SET utf8mb4;
USE `project_database_dept:ofb`;



CREATE TABLE Permissions (
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
                    Birthday DATE NOT NULL,
                    Number VARCHAR(24) NOT NULL,
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

