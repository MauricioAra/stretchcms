package com.rammp.stretchyourbody.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Exercise entity.
 */
public class ExerciseDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String image;

    @NotNull
    private String time;

    @NotNull
    private Integer repetition;

    @NotNull
    private String difficulty;

    @NotNull
    private Integer calification;

    private Boolean status;

    private Boolean isRecommended;

    private Integer totalcalification;

    private Integer countvotes;

    private Long bodyPartId;

    private String bodyPartName;

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
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    public Integer getRepetition() {
        return repetition;
    }

    public void setRepetition(Integer repetition) {
        this.repetition = repetition;
    }
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public Integer getCalification() {
        return calification;
    }

    public void setCalification(Integer calification) {
        this.calification = calification;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Boolean getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }
    public Integer getTotalcalification() {
        return totalcalification;
    }

    public void setTotalcalification(Integer totalcalification) {
        this.totalcalification = totalcalification;
    }
    public Integer getCountvotes() {
        return countvotes;
    }

    public void setCountvotes(Integer countvotes) {
        this.countvotes = countvotes;
    }

    public Long getBodyPartId() {
        return bodyPartId;
    }

    public void setBodyPartId(Long bodyPartId) {
        this.bodyPartId = bodyPartId;
    }

    public String getBodyPartName() {
        return bodyPartName;
    }

    public void setBodyPartName(String bodyPartName) {
        this.bodyPartName = bodyPartName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ExerciseDTO exerciseDTO = (ExerciseDTO) o;

        if ( ! Objects.equals(id, exerciseDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", image='" + image + "'" +
            ", time='" + time + "'" +
            ", repetition='" + repetition + "'" +
            ", difficulty='" + difficulty + "'" +
            ", calification='" + calification + "'" +
            ", status='" + status + "'" +
            ", isRecommended='" + isRecommended + "'" +
            ", totalcalification='" + totalcalification + "'" +
            ", countvotes='" + countvotes + "'" +
            '}';
    }
}
