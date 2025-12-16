INSERT INTO Permissions (Name, Description)
VALUES("View", "Gives the user the right to view a project");
INSERT INTO Permissions (Name, Description)
VALUES("Add tasks", "Gives the user the right to add tasks");
INSERT INTO Permissions (Name, Description)
VALUES("Edit tasks", "Gives the user the right to Edit a task");
INSERT INTO Permissions (Name, Description)
VALUES("Delete tasks", "Gives the user the right to delete a task");
INSERT INTO Permissions (Name, Description)
VALUES("Add projects", "Gives the user the right to add a project");
INSERT INTO Permissions (Name, Description)
VALUES("Edit Project", "Gives the user the right to edit a project");
INSERT INTO Permissions (Name, Description)
VALUES("Delete projects", "Gives the user the right to delete a project");
INSERT INTO Permissions (Name, Description)
VALUES("Grant permissions", "Gives the user the right to grant permissions");
INSERT INTO Roles (Name, Description)
VALUES("Scrum master", "Gives the user the rights to modify tasks within a project");
INSERT INTO Roles (Name, Description)
VALUES("Project leader", "Gives the user the rights to modify a project and everything within");
INSERT INTO Roles (Name, Description)
VALUES("Admin", "Gives the user all rights");
INSERT INTO Roles (Name, Description)
VALUES("Developer", "Gives the user the rights to view a project and its tasks"),
      ("Marketing expert", "Gives the user the right to view, add and edit tasks");
INSERT INTO RolePermissions
VAlUES (1,1);
INSERT INTO RolePermissions
VAlUES (1,2);
INSERT INTO RolePermissions
VAlUES (1,3);
INSERT INTO RolePermissions
VAlUES (1,4);
INSERT INTO RolePermissions
VAlUES (2,1);
INSERT INTO RolePermissions
VAlUES (2,2);
INSERT INTO RolePermissions
VAlUES (2,3);
INSERT INTO RolePermissions
VAlUES (2,4);
INSERT INTO RolePermissions
VAlUES (2,5);
INSERT INTO RolePermissions
VAlUES (2,6);
INSERT INTO RolePermissions
VAlUES (2,7);
INSERT INTO RolePermissions
VAlUES (3,1);
INSERT INTO RolePermissions
VAlUES (3,2);
INSERT INTO RolePermissions
VAlUES (3,3);
INSERT INTO RolePermissions
VAlUES (3,4);
INSERT INTO RolePermissions
VAlUES (3,5);
INSERT INTO RolePermissions
VAlUES (3,6);
INSERT INTO RolePermissions
VAlUES (3,7);
INSERT INTO RolePermissions
VAlUES (3,8);
INSERT INTO RolePermissions
VAlUES (4,1),
       (5,1),
       (5,2),
       (5,3);

INSERT INTO Accounts (Name, Email,Birthday,Number, WeeklyHours, Password, RoleID)
VALUES ("Markus Addington", "addington@icloud.com",DATE("1925-12-24"), '+4592348348',42, "asdj3(Fsah2", 4),
       ("Tannie Offinlux", "rose89312@gmail.com",DATE("1999-12-31"),'+4542348343', 37, "12347", 2),
       ("Hjalte Jernhåndsen", "JernFE@jernhaendsen.dk",DATE("2000-01-01"),'+4572348443', 12, "KDSA+!nas", 4),
       ("Sine Gejlsverk", "fritz111221@icloud.com",DATE("1948-09-22"),'+4502348342', 37, "aasd!42FG*adwFV,<>", 1),
       ("Chalotte Chilkolding", "kolding48@gmail.com",DATE("1977-08-18"),'+4511348343', 38, "CC123", 4),
       ("Henrik Sjulsted", "FreeChesseNation@hotmail.com",DATE("1983-09-24"),'+4534348343', 15, "aD)9faf1cz", 1),
       ("Silas AllStart", "BurntPizza@gmail.com",DATE("1955-11-12"),'+4502048340', 37, "di124.43>fa", 4),
       ("DavID kooningssen", "Kooningssen@icloud.com",DATE("1999-03-24"),'+4593857328', 37, "OC.a,,a.ff*as", 4),
       ("Dennis tuldgårdstræde", "vigtigAdresse2000009@gmail.com",DATE("1984-06-29"),'+4543857322', 42, "AKKD)#JD", 4),
       ("Yrsa ydrasillius", "ydrasilyrsa@gmail.com",DATE("2006-12-12"),'+4513357327', 32, "as,.hNomPN", 5),
       ("ignus ultinias", "SaxophoneLancing@icloud.com",DATE("2007-02-02"),'+4502847362', 37, "421G", 5),
       ("Sussane baeveglser", "baeveglser126@gmail.com",DATE("1944-01-22"),'+4594848372', 12, "mAn2.-1a.", 3),
       ("Niels Jonathansen", "HallIO89@icloud.com",DATE("1992-03-25"),'+4593939393', 42, "KHsd8)dspf0.", 3);

INSERT INTO Projects (Name, Description, isActive, StartDate, Deadline)
VALUES ("Ecommerce platform for _Adaptive Problemsolvers_", "_Adaptive Probelmsolvers_ are in need of a brand new platform to run ecommerce. They have re...", true, DATE("2025-12-06"), DATE("2026-03-18")),
       ("Data analytics for _Solution INC 28_", "_Solution INC 28_ has payed for consult on...", true, DATE("2025-03-25"), DATE("2025-12-04")),
       ("Update mobile portal for _Tangible value_", "_Tangible value_ has requested an update to their current onli...", false, DATE("2025-11-11"), DATE("2026-03-5")),
       ("Set up of Language model AI at _Smaller corpo firm_", "_Smaller corpo firm_ is launching an AI, and has aksed to collabo...", false, DATE("2025-07-09"), DATE("2026-01-27")),
       ("Advertising campaign for ECOMCON", "We are in need of a campaign for the semi Annuel Ecommerce conventi...", false, DATE("2025-09-18"), DATE("2026-04-19"));

INSERT INTO ProjectMembers
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 5),
       (2, 6),
       (2, 7),
       (2, 8),
       (2, 9),
       (3, 1),
       (3, 7),
       (3, 9),
       (4, 10),
       (4, 11),
       (4, 12),
       (1, 13),
       (2, 13),
       (3, 13);

INSERT INTO Tasks (Name, Description, Completed, StartDate, Deadline,HourEstimate, ProjectID, ParentID)
VALUES ("Web application", "_Adaptive Probelmsolvers_ need a new webplatform. It nee...", false, DATE("2025-12-06"), DATE("2026-01-28"),34, 1, 0),
       ("Mobile application", "_Adaptive Probelmsolvers_ needs a matching", false, DATE("2026-01-28"), DATE("2026-03-18"),94, 1, 0),
       ("Data collection", "Investigate what data _Solution INC 28_ currently has, and what the...", false, DATE("2025-03-25"), DATE("2025-05-01"),43, 2, 0),
       ("Data analysis", "Complete data analysis of _Solution INC 28_ data", false, DATE("2025-05-2"), DATE("2025-12-04"),944, 2, 0),
       ("Mobile application2", "_Tangible value_ has requested an update to their current onli...", false, DATE("2025-11-11"), DATE("2026-03-5"),19, 3, 0),
       ("AI language model", "_Smaller corpo firm_ is launching an AI, and has aksed to collabo...", false, DATE("2025-07-09"), DATE("2026-01-27"),99, 4, 0),
       ("Customer analysis", "IDentify our customers and thei...", false, DATE("2025-09-18"), DATE("2025-10-29"),84, 5, 0),
       ("Campaign", "build a campaign for the semi Annuel Ecommerce conventi...", false, DATE("2025-11-01"), DATE("2026-04-19"),47, 5, 0);

INSERT INTO Tasks (Name, Description, Completed, StartDate, Deadline,HourEstimate, ProjectID, ParentID)
VALUES ("Front-end", "The webpage is in need of a new look. It requires a new and simpler design, than the current webpage. However responisble parties at _Adaptive Probelmsolvers_, require the page to look similar in layout, to ensure current users are not scared away. Therefore an extensive analysis of the current users and the webpage relationship needs to be investiga...", true, DATE("2025-12-08"), DATE("2026-01-25"), 29, 1, 1),
       ("Back-end", "The website requires extensive changes to current functionallity. It requires a new database design, that simultaneously discourages duplicants but also can handle large workloads at fast speeds. A more effecient method of handling _Adaptive Probelmsolvers_ large datasets is required and the...", false, DATE("2025-12-09"), DATE("2026-01-27"), 36, 1, 1);

INSERT INTO tasklist
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5);
INSERT INTO tasklist
VALUES (2, 6),
       (3, 7),
       (2, 1),
       (2, 2),
       (2, 3);