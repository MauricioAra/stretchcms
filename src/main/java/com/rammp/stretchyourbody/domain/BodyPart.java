package com.rammp.stretchyourbody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BodyPart.
 */
@Entity
@Table(name = "body_part")
public class BodyPart implements Serializable {

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

    @OneToMany(mappedBy = "bodyPart")
    @JsonIgnore
    private Set<Exercise> exercises = new HashSet<>();

    @ManyToOne
    private SubCategory subCategory;

    @ManyToMany(mappedBy = "bodyParts")
    @JsonIgnore
    private Set<UserHealth> userHealths = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BodyPart name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public BodyPart image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean isStatus() {
        return status;
    }

    public BodyPart status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public BodyPart exercises(Set<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    public BodyPart addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        exercise.setBodyPart(this);
        return this;
    }

    public BodyPart removeExercise(Exercise exercise) {
        this.exercises.remove(exercise);
        exercise.setBodyPart(null);
        return this;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public BodyPart subCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
        return this;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Set<UserHealth> getUserHealths() {
        return userHealths;
    }

    public BodyPart userHealths(Set<UserHealth> userHealths) {
        this.userHealths = userHealths;
        return this;
    }

    public BodyPart addUserHealth(UserHealth userHealth) {
        this.userHealths.add(userHealth);
        userHealth.getBodyParts().add(this);
        return this;
    }

    public BodyPart removeUserHealth(UserHealth userHealth) {
        this.userHealths.remove(userHealth);
        userHealth.getBodyParts().remove(this);
        return this;
    }

    public void setUserHealths(Set<UserHealth> userHealths) {
        this.userHealths = userHealths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BodyPart bodyPart = (BodyPart) o;
        if (bodyPart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bodyPart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BodyPart{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", image='" + image + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
