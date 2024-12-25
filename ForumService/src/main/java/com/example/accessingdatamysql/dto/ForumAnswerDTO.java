package com.example.accessingdatamysql.dto;

public class ForumAnswerDTO {

    private Long id;
    private String text;
    private String date;
    private String doctorFirstName;
    private String doctorLastName;
    private String doctorImagePath;

    public ForumAnswerDTO() {}

    public ForumAnswerDTO(Long id, String text, String date, String doctorFirstName, String doctorLastName, String doctorImagePath) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.doctorFirstName = doctorFirstName;
        this.doctorLastName = doctorLastName;
        this.doctorImagePath = doctorImagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getDoctorImagePath() {
        return doctorImagePath;
    }

    public void setDoctorImagePath(String doctorImagePath) {
        this.doctorImagePath = doctorImagePath;
    }
}