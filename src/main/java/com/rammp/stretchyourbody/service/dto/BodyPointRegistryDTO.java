package com.rammp.stretchyourbody.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BodyPointRegistry entity.
 */
public class BodyPointRegistryDTO implements Serializable {

    private Long id;

    private String type;

    private String comment;

    private Long bodyPointId;

    private String bodyPointBodypart;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getBodyPointId() {
        return bodyPointId;
    }

    public void setBodyPointId(Long bodyPointId) {
        this.bodyPointId = bodyPointId;
    }

    public String getBodyPointBodypart() {
        return bodyPointBodypart;
    }

    public void setBodyPointBodypart(String bodyPointBodypart) {
        this.bodyPointBodypart = bodyPointBodypart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BodyPointRegistryDTO bodyPointRegistryDTO = (BodyPointRegistryDTO) o;

        if ( ! Objects.equals(id, bodyPointRegistryDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BodyPointRegistryDTO{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
