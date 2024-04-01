package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Naziv kategorije ne smije biti prazan.")
    private String name;
    @Size(max = 255, message = "Opis kategorije može sadržavati najviše 255 znakova.")
    private String description;

//    @JsonIgnore
//    @OneToMany(mappedBy = "category")
//    private Article article;

    public Category() {}

    public Category(String name, String field, String description) {
        this.name = name;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
                "Category[id=%d, name='%s', description='%s']",
                id, name, description);
    }
}
