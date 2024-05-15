package com.example.accessingdatamysql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Naziv kategorije ne smije biti prazan.")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "Opis ne smije biti prazan.")
    @Size(min=0, max = 4500, message = "Text must be at most 5000 characters")
    private String description;

    public CategoryEntity() {}

    public CategoryEntity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
                "CategoryEntity[id=%d, name='%s', description='%s']",
                id, name, description);
    }
}
