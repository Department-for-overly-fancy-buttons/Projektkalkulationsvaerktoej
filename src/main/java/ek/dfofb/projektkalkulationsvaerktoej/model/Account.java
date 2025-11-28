package ek.dfofb.projektkalkulationsvaerktoej.model;

import java.util.Date;

public class Account {
    private int accountID;
    private String name;
    private String email;
    private Date birthday;
    private String number;
    private int weeklyHours;
    private String password;
    private int roleID;

    public Account() {}

    public Account(int accountID, String name, String email, Date birthday, String number, int weeklyHours, String password, int roleID) {
        this.accountID = accountID;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.number = number;
        this.weeklyHours = weeklyHours;
        this.password = password;
        this.roleID = roleID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(int weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
