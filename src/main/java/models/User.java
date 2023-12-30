package models;

import java.util.Date;

public class User {

    int studentID;
    Date birthDate;
    String studentName;
    String subject;
    int phoneNumber;

    public User(int studentID,String studentName, Date birthDate, String subject, int phoneNumber) {
        this.studentID = studentID;
        this.birthDate = birthDate;
        this.studentName = studentName;
        this.subject = subject;
        this.phoneNumber = phoneNumber;
    }
    public User(){

    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
