package com.example.accessingdatamysql.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="article")
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private DoctorInfoEntity doctor;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @NotNull
    @Size(min = 1, max = 150)
    private String title;

    @NotNull
    @Size(min = 1, max = 2500)
    private String text;

    @NotNull
    @Pattern(regexp = "^\\d{2}.\\d{2}.\\d{4}$", message = "Datum mora biti u formatu 'DD.MM.YYYY'.")
    private String date;


    public ArticleEntity() {}

    public ArticleEntity(DoctorInfoEntity doctor, CategoryEntity categoryEntity, String text, String date, String title) {
        this.doctor = doctor;
        this.category = categoryEntity;
        this.text = text;
        this.date = date;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DoctorInfoEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorInfoEntity doctor) {
        this.doctor = doctor;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity categoryEntity) {
        this.category = categoryEntity;
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

    @Override
    public String toString() {
        return String.format(
                "ArticleEntity[id=%d, doctor='%s', categoryEntity='%s', text='%s', date='%s']",
                id, doctor.toString(), category.toString(), text, date.toString());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
