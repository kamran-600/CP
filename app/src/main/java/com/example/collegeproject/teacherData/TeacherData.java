package com.example.collegeproject.teacherData;

public class TeacherData {
    String department,email,full_name,gender,password,phone_no,roll;

    public TeacherData() {
    }

    public TeacherData(String department, String email, String full_name, String gender, String password, String phone_no, String roll) {
        this.department = department;
        this.email = email;
        this.full_name = full_name;
        this.gender = gender;
        this.password = password;
        this.phone_no = phone_no;
        this.roll = roll;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }
}
