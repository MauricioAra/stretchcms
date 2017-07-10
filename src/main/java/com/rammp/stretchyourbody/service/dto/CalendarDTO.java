package com.rammp.stretchyourbody.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Calendar entity.
 */
public class CalendarDTO implements Serializable {

    private Long id;

    @NotNull
    private String date;

    @NotNull
    private String hour;

    private Boolean status;

    private Set<ProgramDTO> programs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<ProgramDTO> getPrograms() {
        return programs;
    }

    public void setPrograms(Set<ProgramDTO> programs) {
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

        CalendarDTO calendarDTO = (CalendarDTO) o;

        if ( ! Objects.equals(id, calendarDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CalendarDTO{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", hour='" + hour + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
