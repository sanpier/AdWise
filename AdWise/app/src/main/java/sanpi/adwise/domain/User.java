package sanpi.adwise.domain;

import java.util.Date;

/**
 * Created by San-Pi on 24.09.2016.
 */

public class User
{
    //Variables
    int id;
    String name;
    String surname;
    String gender;
    String email;
    int department;
    int city;
    Date birthdate;

    //Constructor
    public User(int giveId, String giveName, String giveSurname, String giveGender
            , String giveEmail, int giveDepart, int giveCity, Date giveBirth)
    {
        id = giveId;
        name = giveName;
        surname = giveSurname;
        gender = giveGender;
        email = giveEmail;
        department = giveDepart;
        city = giveCity;
        birthdate = giveBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }
}
