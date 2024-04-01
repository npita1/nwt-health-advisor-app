package com.example.accessingdatamysql.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;


@Entity
public class ForumAnswer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @JsonIgnore
    private ForumQuestion question;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorInfo doctor;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore
    private ForumAnswer parent;

    @NotNull(message = "Tekst ne smije biti prazan.")
    private String text;

    @NotNull
    @Pattern(regexp = "^\\d{2}.\\d{2}.\\d{4}$", message = "Datum mora biti u formatu 'DD.MM.YYYY'.")
    private String date;

    public ForumAnswer() {}

    public ForumAnswer(ForumQuestion question, DoctorInfo doctor, String text, String date, ForumAnswer parent) {
        this.question = question;
        this.doctor = doctor;
        this.text = text;
        this.date = date;
        this.parent = parent;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ForumQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ForumQuestion question) {
        this.question = question;
    }

    public DoctorInfo getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorInfo doctor) {
        this.doctor = doctor;
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

    public ForumAnswer getParent() {
        return parent;
    }

    public void setParent(ForumAnswer parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return String.format(
                "ForumAnswer[id=%d, question='%s', doctor='%s', text='%s', date='%s', parent='%s']",
                id, question.toString(), doctor.toString(), text, date.toString(), parent.toString());
    }
}
