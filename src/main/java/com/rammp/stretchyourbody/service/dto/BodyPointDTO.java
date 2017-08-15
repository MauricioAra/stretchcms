package com.rammp.stretchyourbody.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BodyPoint entity.
 */
public class BodyPointDTO implements Serializable {

    private Long id;

    private String bodypart;

    private Long idbodypart;

    private Long userAppId;

    private String userAppName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getBodypart() {
        return bodypart;
    }

    public void setBodypart(String bodypart) {
        this.bodypart = bodypart;
    }
    public Long getIdbodypart() {
        return idbodypart;
    }

    public void setIdbodypart(Long idbodypart) {
        this.idbodypart = idbodypart;
    }

    public Long getUserAppId() {
        return userAppId;
    }

    public void setUserAppId(Long userAppId) {
        this.userAppId = userAppId;
    }

    public String getUserAppName() {
        return userAppName;
    }

    public void setUserAppName(String userAppName) {
        this.userAppName = userAppName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BodyPointDTO bodyPointDTO = (BodyPointDTO) o;

        if ( ! Objects.equals(id, bodyPointDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BodyPointDTO{" +
            "id=" + id +
            ", bodypart='" + bodypart + "'" +
            ", idbodypart='" + idbodypart + "'" +
            '}';
    }
}
