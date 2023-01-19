package com.example.collegeproject.attendance;

public class AttendanceModelClass {

    String className,section;
    int classLogo;

    public AttendanceModelClass(String className, String section, int classLogo) {
        this.className = className;
        this.section = section;
        this.classLogo = classLogo;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getClassLogo() {
        return classLogo;
    }

    public void setClassLogo(int classLogo) {
        this.classLogo = classLogo;
    }
}
