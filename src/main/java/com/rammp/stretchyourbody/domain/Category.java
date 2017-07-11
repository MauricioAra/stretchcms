package com.rammp.stretchyourbody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Category.
 */
@Entity
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "status")
    private Boolean status;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<SubCategory> subCategories = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Category name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public Category image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean isStatus() {
        return status;
    }

    public Category status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<SubCategory> getSubCategories() {
        return subCategories;
    }

    public Category subCategories(Set<SubCategory> subCategories) {
        this.subCategories = subCategories;
        return this;
    }

    public Category addSubCategory(SubCategory subCategory) {
        this.subCategories.add(subCategory);
        subCategory.setCategory(this);
        return this;
    }

    public Category removeSubCategory(SubCategory subCategory) {
        this.subCategories.remove(subCategory);
        subCategory.setCategory(null);
        return this;
    }

    public void setSubCategories(Set<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Category category = (Category) o;
        if (category.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Category{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", image='" + image + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
