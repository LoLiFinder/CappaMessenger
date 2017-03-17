package com.cappamessenger.two;

/**
 * Created by Dkflbc on 16.03.17.
 */

public class BaseUserInfo {
    private String First_Name;
    private String Last_Name;
    private String Group;
    private String Status;
    private Boolean UserLine;

    public BaseUserInfo(String first_Name, String last_Name, String group, String status,Boolean userLine) {
        First_Name = first_Name;
        Last_Name = last_Name;
        Group = group;
        Status = status;
        UserLine = userLine;
    }

    public BaseUserInfo() {
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public String getGroup() {
        return Group;
    }

    public String getStatus() {
        return Status;
    }



    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public void setGroup(String group) {
        Group = group;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Boolean getUserLine() {
        return UserLine;
    }

    public void setUserLine(Boolean userLine) {
        UserLine = userLine;
    }
}
