package com.project.batacademy.domain;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student")
public class Student implements java.io.Serializable {

    private int studentId;
    private String firstName;
    private String lastName;
    private String gender;
    private String phone;
    private float cgpa;
    private boolean registered;
    private String password;

    public Student() {
    }
    
    public Student(Student studToCopy) {
    	this.studentId = studToCopy.studentId;
        this.firstName = studToCopy.firstName;
        this.lastName = studToCopy.lastName;
        this.gender = studToCopy.gender;
        this.phone = studToCopy.phone;
        this.cgpa = studToCopy.cgpa;
        this.registered = studToCopy.registered;
        this.password = studToCopy.password;
	}


    public int getStudentId() {
        return this.studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public float getCgpa() {
        return this.cgpa;
    }

    public void setCgpa(float cgpa) {
        this.cgpa = cgpa;
    }

    public boolean isRegistered() {
        return this.registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", firstName=" + firstName + ", lastName=" + lastName + ", gender="
				+ gender + ", phone=" + phone + ", cgpa=" + cgpa + ", registered=" + registered + ", password="
				+ password + "]";
	}

}
