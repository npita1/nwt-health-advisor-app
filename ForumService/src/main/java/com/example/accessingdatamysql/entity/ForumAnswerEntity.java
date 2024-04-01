package com.example.accessingdatamysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name="forum_answer")
public class ForumAnswerEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @JsonIgnore
    private ForumQuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorInfoEntity doctor;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @JsonIgnore
    private ForumAnswerEntity parent;

    @NotNull(message = "Tekst ne smije biti prazan.")
    private String text;

    @NotNull
    @Pattern(regexp = "^\\d{2}.\\d{2}.\\d{4}$", message = "Datum mora biti u formatu 'DD.MM.YYYY'.")
    private String date;

    public ForumAnswerEntity() {}

    public ForumAnswerEntity(ForumQuestionEntity question, DoctorInfoEntity doctor, String text, String date, ForumAnswerEntity parent) {
        this.question = question;
        this.doctor = doctor;
        this.text = text;
        this.date = date;
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ForumQuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(ForumQuestionEntity question) {
        this.question = question;
    }

    public DoctorInfoEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorInfoEntity doctor) {
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

    public ForumAnswerEntity getParent() {
        return parent;
    }

    public void setParent(ForumAnswerEntity parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return String.format(
                "ForumAnswerEntity[id=%d, question='%s', doctor='%s', text='%s', date='%s', parent='%s']",
                id, question.toString(), doctor.toString(), text, date.toString(), parent.toString());
    }
}
