package com.rammp.stretchyourbody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Exercise.
 */
@Entity
@Table(name = "exercise")
public class Exercise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "jhi_time")
    private String time;

    @Column(name = "repetition")
    private Integer repetition;

    @Column(name = "difficulty")
    private String difficulty;

    @Column(name = "calification")
    private Integer calification;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "is_recommended")
    private Boolean isRecommended;

    @ManyToOne
    private BodyPart bodyPart;

    @ManyToMany(mappedBy = "exercises")
    @JsonIgnore
    private Set<UserApp> userApps = new HashSet<>();

    @ManyToMany(mappedBy = "exercises")
    @JsonIgnore
    private Set<Program> programs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Exercise name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public Exercise image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTime() {
        return time;
    }

    public Exercise time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRepetition() {
        return repetition;
    }

    public Exercise repetition(Integer repetition) {
        this.repetition = repetition;
        return this;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public Exercise difficulty(String difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getCalification() {
        return calification;
    }

    public Exercise calification(Integer calification) {
        this.calification = calification;
        return this;
    }

    public void setCalification(Integer calification) {
        this.calification = calification;
    }

    public Boolean isStatus() {
        return status;
    }

    public Exercise status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isIsRecommended() {
        return isRecommended;
    }

    public Exercise isRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
        return this;
    }

    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    public BodyPart getBodyPart() {
        return bodyPart;
    }

    public Exercise bodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
        return this;
    }

    public void setBodyPart(BodyPart bodyPart) {
        this.bodyPart = bodyPart;
    }

    public Set<UserApp> getUserApps() {
        return userApps;
    }

    public Exercise userApps(Set<UserApp> userApps) {
        this.userApps = userApps;
        return this;
    }

    public Exercise addUserApp(UserApp userApp) {
        this.userApps.add(userApp);
        userApp.getExercises().add(this);
        return this;
    }

    public Exercise removeUserApp(UserApp userApp) {
        this.userApps.remove(userApp);
        userApp.getExercises().remove(this);
        return this;
    }

    public void setUserApps(Set<UserApp> userApps) {
        this.userApps = userApps;
    }

    public Set<Program> getPrograms() {
        return programs;
    }

    public Exercise programs(Set<Program> programs) {
        this.programs = programs;
        return this;
    }

    public Exercise addProgram(Program program) {
        this.programs.add(program);
        program.getExercises().add(this);
        return this;
    }

    public Exercise removeProgram(Program program) {
        this.programs.remove(program);
        program.getExercises().remove(this);
        return this;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        if (exercise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exercise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", image='" + image + "'" +
            ", time='" + time + "'" +
            ", repetition='" + repetition + "'" +
            ", difficulty='" + difficulty + "'" +
            ", calification='" + calification + "'" +
            ", status='" + status + "'" +
            ", isRecommended='" + isRecommended + "'" +
            '}';
    }
}
