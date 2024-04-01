package com.example.accessingdatamysql.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.*;

import java.util.Date;

@Entity
public class ForumQuestion {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Korisnik ne smije biti prazan.")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotNull(message = "Kategorija ne smije biti prazna.")
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @NotNull
    @Size(min = 1, max = 100, message = "Naslov mora imati između 1 i 100 znakova.")
    private String title;
    @NotNull
    @Size(min = 1, max = 1000, message = "Tekst mora imati između 1 i 1000 znakova.")
    private String text;
    @NotNull
    @Pattern(regexp = "^\\d{2}.\\d{2}.\\d{4}$", message = "Datum mora biti u formatu 'DD.MM.YYYY'.")
    private String date;

    @AssertTrue(message = "Anonimnost mora biti postavljena na true ili false.")
    private boolean anonymity;

    public ForumQuestion() {}

    public ForumQuestion(User user, Category category, String title, String text, String date, boolean anonymity) {
        this.user = user;
        this.category = category;
        this.title = title;
        this.text = text;
        this.date = date;
        this.anonymity = anonymity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
                "ForumQuestion[id=%d, user='%s', category='%s', title='%s', text='%s', date='%s', anonymity='%b']",
                id, user.toString(), category.toString(), title, text, date.toString(), anonymity);
    }
}
