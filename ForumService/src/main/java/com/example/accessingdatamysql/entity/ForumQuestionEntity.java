package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Entity
@Table(name="forum_question")
public class ForumQuestionEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Korisnik ne smije biti prazan.")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @NotNull(message = "Kategorija ne smije biti prazna.")
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;



    @Size(min = 5, max = 100, message = "Naslov mora imati između 5 i 100 znakova.")
    private String title;

    @Size(min = 10, max = 400, message = "Tekst mora imati između 10 i 400 znakova.")
    private String text;

    @Pattern(regexp = "^\\d{2}.\\d{2}.\\d{4}$", message = "Datum mora biti u formatu 'DD.MM.YYYY'.")
    private String date;

    @NotNull(message = "Moraš odbrati da li pitanje anonimno ili ne")
    private boolean anonymity;

    public ForumQuestionEntity() {}

    public ForumQuestionEntity(UserEntity user, CategoryEntity categoryEntity, String title, String text, String date, boolean anonymity) {
        this.user = user;
        this.category = categoryEntity;
        this.title = title;
        this.text = text;
        this.date = date;
        this.anonymity = anonymity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity categoryEntity) {
        this.category = categoryEntity;
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

    @Override
    public String toString() {
        return String.format(
                "ForumQuestionEntity[id=%d, user='%s', categoryEntity='%s', title='%s', text='%s', date='%s', anonymity='%b']",
                id, user.toString(), category.toString(), title, text, date.toString(), anonymity);
    }
}
