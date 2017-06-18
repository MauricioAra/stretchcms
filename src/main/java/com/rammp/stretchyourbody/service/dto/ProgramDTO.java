package com.rammp.stretchyourbody.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Program entity.
 */
public class ProgramDTO implements Serializable {

    private Long id;

    private String name;

    private String intDate;

    private String finishDate;

    private Integer interval;

    private Integer cantRepetition;

    private Boolean status;

    private Boolean isDairy;

    private Boolean isRecommended;

    private Set<ExerciseDTO> exercises = new HashSet<>();

    private Long userAppId;

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
    public String getIntDate() {
        return intDate;
    }

    public void setIntDate(String intDate) {
        this.intDate = intDate;
    }
    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }
    public Integer getCantRepetition() {
        return cantRepetition;
    }

    public void setCantRepetition(Integer cantRepetition) {
        this.cantRepetition = cantRepetition;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Boolean getIsDairy() {
        return isDairy;
    }

    public void setIsDairy(Boolean isDairy) {
        this.isDairy = isDairy;
    }
    public Boolean getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    public Set<ExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercises(Set<ExerciseDTO> exercises) {
        this.exercises = exercises;
    }

    public Long getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(Long userAppId) {
        this.userAppId = userAppId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgramDTO programDTO = (ProgramDTO) o;

        if ( ! Objects.equals(id, programDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProgramDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", intDate='" + intDate + "'" +
            ", finishDate='" + finishDate + "'" +
            ", interval='" + interval + "'" +
            ", cantRepetition='" + cantRepetition + "'" +
            ", status='" + status + "'" +
            ", isDairy='" + isDairy + "'" +
            ", isRecommended='" + isRecommended + "'" +
            '}';
    }
}
