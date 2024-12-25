package com.example.accessingdatamysql.dto;

public class ForumQuestionDTO {

    private Long id;
    private String title;
    private String text;
    private String date;

    public ForumQuestionDTO(Long id, String title, String text, String date, boolean anonymity, Long categoryId, String userFirstName, String userLastName) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.anonymity = anonymity;
        this.categoryId = categoryId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }

    private boolean anonymity;
    private Long categoryId;
    private String userFirstName;

    private String userLastName;

    public ForumQuestionDTO() {}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isAnonymity() {
        return anonymity;
    }

    public void setAnonymity(boolean anonymity) {
        this.anonymity = anonymity;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }
}