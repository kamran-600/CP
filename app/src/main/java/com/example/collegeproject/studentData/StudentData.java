package com.example.collegeproject.studentData;

import com.google.firebase.firestore.Blob;

public class StudentData {

    String full_name, father_name, personal_phone, gender, dob, father_phone, email,role;

    Blob profileImageBlob;
    String roll_number, academic_fee, department, batch, hostel_fee, academic_year, password;


    public StudentData() {
    }

    public StudentData(String full_name, String father_name, String personal_phone, String gender, String dob, String father_phone, String email, String role, String roll_number, String academic_fee, String department, String batch, String hostel_fee, String academic_year, String password) {
        this.full_name = full_name;
        this.father_name = father_name;
        this.personal_phone = personal_phone;
        this.gender = gender;
        this.dob = dob;
        this.father_phone = father_phone;
        this.email = email;
        this.role = role;
        this.roll_number = roll_number;
        this.academic_fee = academic_fee;
        this.department = department;
        this.batch = batch;
        this.hostel_fee = hostel_fee;
        this.academic_year = academic_year;
        this.password = password;
    }

    public StudentData(String full_name, String father_name, String personal_phone, String gender, String dob, String father_phone, String email, String role, Blob profileImageBlob, String roll_number, String academic_fee, String department, String batch, String hostel_fee, String academic_year, String password) {
        this.full_name = full_name;
        this.father_name = father_name;
        this.personal_phone = personal_phone;
        this.gender = gender;
        this.dob = dob;
        this.father_phone = father_phone;
        this.email = email;
        this.role = role;
        this.profileImageBlob = profileImageBlob;
        this.roll_number = roll_number;
        this.academic_fee = academic_fee;
        this.department = department;
        this.batch = batch;
        this.hostel_fee = hostel_fee;
        this.academic_year = academic_year;
        this.password = password;
    }

    public StudentData(Blob profileImageBlob) {
        this.profileImageBlob = profileImageBlob;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getPersonal_phone() {
        return personal_phone;
    }

    public void setPersonal_phone(String personal_phone) {
        this.personal_phone = personal_phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFather_phone() {
        return father_phone;
    }

    public void setFather_phone(String father_phone) {
        this.father_phone = father_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Blob getProfileImageBlob() {
        return profileImageBlob;
    }

    public void setProfileImageBlob(Blob profileImageBlob) {
        this.profileImageBlob = profileImageBlob;
    }

    public String getRoll_number() {
        return roll_number;
    }

    public void setRoll_number(String roll_number) {
        this.roll_number = roll_number;
    }

    public String getAcademic_fee() {
        return academic_fee;
    }

    public void setAcademic_fee(String academic_fee) {
        this.academic_fee = academic_fee;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getHostel_fee() {
        return hostel_fee;
    }

    public void setHostel_fee(String hostel_fee) {
        this.hostel_fee = hostel_fee;
    }

    public String getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(String academic_year) {
        this.academic_year = academic_year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}