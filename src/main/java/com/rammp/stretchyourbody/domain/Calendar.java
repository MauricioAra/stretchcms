package com.rammp.stretchyourbody.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Calendar.
 */
@Entity
@Table(name = "calendar")
public class Calendar implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private String date;

    @Column(name = "hour")
    private String hour;

    @Column(name = "status")
    private Boolean status;

    @ManyToMany
    @JoinTable(name = "calendar_program",
               joinColumns = @JoinColumn(name="calendars_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="programs_id", referencedColumnName="id"))
    private Set<Program> programs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public Calendar date(String date) {
        this.date = date;
        return this;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public Calendar hour(String hour) {
        this.hour = hour;
        return this;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Boolean isStatus() {
        return status;
    }

    public Calendar status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Set<Program> getPrograms() {
        return programs;
    }

    public Calendar programs(Set<Program> programs) {
        this.programs = programs;
        return this;
    }

    public Calendar addProgram(Program program) {
        this.programs.add(program);
        program.getCalendars().add(this);
        return this;
    }

    public Calendar removeProgram(Program program) {
        this.programs.remove(program);
        program.getCalendars().remove(this);
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
        Calendar calendar = (Calendar) o;
        if (calendar.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, calendar.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Calendar{" +
            "id=" + id +
            ", date='" + date + "'" +
            ", hour='" + hour + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
