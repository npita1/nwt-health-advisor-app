package com.example.accessingdatamysql.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="article")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    //@JsonIgnore
    // dodaj da je json ignore probaj
    private DoctorInfoEntity doctor;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @NotNull
    @Size(min = 1, max = 150)
    private String title;

    @NotNull
    @Size(min = 1, max = 10000)
    private String text;

    @NotNull
    @Pattern(regexp = "^\\d{2}.\\d{2}.\\d{4}$", message = "Datum mora biti u formatu 'DD.MM.YYYY'.")
    private String date;

    private String imagePath;


    public ArticleEntity() {}

    public ArticleEntity(DoctorInfoEntity doctor, CategoryEntity categoryEntity, String text, String date, String title, String imagePath) {
        this.doctor = doctor;
        this.category = categoryEntity;
        this.text = text;
        this.date = date;
        this.title = title;
        this.imagePath = imagePath;
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
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return String.format(
                "ArticleEntity[id=%d, doctor='%s', category='%s', text='%s', date='%s', imagePath='%s']",
                id, doctor.toString(), category.toString(), text, date, imagePath);
    }




    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
