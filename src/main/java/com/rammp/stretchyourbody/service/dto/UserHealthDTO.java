package com.rammp.stretchyourbody.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserHealth entity.
 */
public class UserHealthDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer workHours;

    private Boolean doesWorkOut;

    private Boolean isSmoker;

    private Boolean isHealthFood;

    private Set<BodyPartDTO> bodyParts = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Integer workHours) {
        this.workHours = workHours;
    }
    public Boolean getDoesWorkOut() {
        return doesWorkOut;
    }

    public void setDoesWorkOut(Boolean doesWorkOut) {
        this.doesWorkOut = doesWorkOut;
    }
    public Boolean getIsSmoker() {
        return isSmoker;
    }

    public void setIsSmoker(Boolean isSmoker) {
        this.isSmoker = isSmoker;
    }
    public Boolean getIsHealthFood() {
        return isHealthFood;
    }

    public void setIsHealthFood(Boolean isHealthFood) {
        this.isHealthFood = isHealthFood;
    }

    public Set<BodyPartDTO> getBodyParts() {
        return bodyParts;
    }

    public void setBodyParts(Set<BodyPartDTO> bodyParts) {
        this.bodyParts = bodyParts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserHealthDTO userHealthDTO = (UserHealthDTO) o;

        if ( ! Objects.equals(id, userHealthDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserHealthDTO{" +
            "id=" + id +
            ", workHours='" + workHours + "'" +
            ", doesWorkOut='" + doesWorkOut + "'" +
            ", isSmoker='" + isSmoker + "'" +
            ", isHealthFood='" + isHealthFood + "'" +
            '}';
    }
}
