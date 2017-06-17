package com.rammp.stretchyourbody.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProgramFeedBack entity.
 */
public class ProgramFeedBackDTO implements Serializable {

    private Long id;

    private Boolean isUseful;

    private Boolean isHelpPain;

    private Long programId;

    private String programName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getIsUseful() {
        return isUseful;
    }

    public void setIsUseful(Boolean isUseful) {
        this.isUseful = isUseful;
    }
    public Boolean getIsHelpPain() {
        return isHelpPain;
    }

    public void setIsHelpPain(Boolean isHelpPain) {
        this.isHelpPain = isHelpPain;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProgramFeedBackDTO programFeedBackDTO = (ProgramFeedBackDTO) o;

        if ( ! Objects.equals(id, programFeedBackDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProgramFeedBackDTO{" +
            "id=" + id +
            ", isUseful='" + isUseful + "'" +
            ", isHelpPain='" + isHelpPain + "'" +
            '}';
    }
}
