package com.project.batacademy.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "faculty")
public class Faculty implements java.io.Serializable {

    private int facultyId;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private String designation;
    private boolean enable;
    private String password;

    public Faculty() {
    }
    
    public Faculty(Faculty facultyToCopy) {
    	this.facultyId = facultyToCopy.facultyId;
        this.firstName = facultyToCopy.firstName;
        this.lastName = facultyToCopy.lastName;
        this.gender = facultyToCopy.gender;
        this.phone = facultyToCopy.phone;
        this.designation = facultyToCopy.designation;
        this.enable = facultyToCopy.enable;
        this.password = facultyToCopy.password;
	}
    
    public Faculty(int facultyId, String firstName, String lastName, String gender, String phone, String designation, boolean enable, String password) {
        this.facultyId = facultyId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phone = phone;
        this.designation = designation;
        this.enable = enable;
        this.password = password;
    }

    public int getFacultyId() {
        return this.facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phno) {
        this.phone = phno;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "Faculty [facultyId=" + facultyId + ", firstName=" + firstName + ", lastName=" + lastName + ", gender="
				+ gender + ", phone=" + phone + ", designation=" + designation + ", enable=" + enable + ", password="
				+ password + "]";
	}

}
