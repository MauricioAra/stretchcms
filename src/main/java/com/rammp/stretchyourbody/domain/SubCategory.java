package com.rammp.stretchyourbody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A SubCategory.
 */
@Entity
@Table(name = "sub_category")
public class SubCategory implements Serializable {

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

    @OneToMany(mappedBy = "subCategory")
    @JsonIgnore
    private Set<BodyPart> bodyParts = new HashSet<>();

    @ManyToOne
    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public SubCategory name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public SubCategory image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean isStatus() {
        return status;
    }

    public SubCategory status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<BodyPart> getBodyParts() {
        return bodyParts;
    }

    public SubCategory bodyParts(Set<BodyPart> bodyParts) {
        this.bodyParts = bodyParts;
        return this;
    }

    public SubCategory addBodyPart(BodyPart bodyPart) {
        this.bodyParts.add(bodyPart);
        bodyPart.setSubCategory(this);
        return this;
    }

    public SubCategory removeBodyPart(BodyPart bodyPart) {
        this.bodyParts.remove(bodyPart);
        bodyPart.setSubCategory(null);
        return this;
    }

    public void setBodyParts(Set<BodyPart> bodyParts) {
        this.bodyParts = bodyParts;
    }

    public Category getCategory() {
        return category;
    }

    public SubCategory category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubCategory subCategory = (SubCategory) o;
        if (subCategory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, subCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SubCategory{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", image='" + image + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
