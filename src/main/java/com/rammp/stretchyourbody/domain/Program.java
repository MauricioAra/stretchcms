package com.rammp.stretchyourbody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Program.
 */
@Entity
@Table(name = "program")
public class Program implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "int_date")
    private String intDate;

    @Column(name = "finish_date")
    private String finishDate;

    @Column(name = "jhi_interval")
    private Integer interval;

    @Column(name = "cant_repetition")
    private Integer cantRepetition;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "is_dairy")
    private Boolean isDairy;

    @OneToMany(mappedBy = "program")
    @JsonIgnore
    private Set<ProgramFeedBack> programFeedBacks = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "program_exercise",
               joinColumns = @JoinColumn(name="programs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="exercises_id", referencedColumnName="id"))
    private Set<Exercise> exercises = new HashSet<>();

    @ManyToOne
    private UserApp userApp;

    @ManyToMany(mappedBy = "programs")
    @JsonIgnore
    private Set<Calendar> calendars = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Program name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntDate() {
        return intDate;
    }

    public Program intDate(String intDate) {
        this.intDate = intDate;
        return this;
    }

    public void setIntDate(String intDate) {
        this.intDate = intDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public Program finishDate(String finishDate) {
        this.finishDate = finishDate;
        return this;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getInterval() {
        return interval;
    }

    public Program interval(Integer interval) {
        this.interval = interval;
        return this;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getCantRepetition() {
        return cantRepetition;
    }

    public Program cantRepetition(Integer cantRepetition) {
        this.cantRepetition = cantRepetition;
        return this;
    }

    public void setCantRepetition(Integer cantRepetition) {
        this.cantRepetition = cantRepetition;
    }

    public Boolean isStatus() {
        return status;
    }

    public Program status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isIsDairy() {
        return isDairy;
    }

    public Program isDairy(Boolean isDairy) {
        this.isDairy = isDairy;
        return this;
    }

    public void setIsDairy(Boolean isDairy) {
        this.isDairy = isDairy;
    }

    public Set<ProgramFeedBack> getProgramFeedBacks() {
        return programFeedBacks;
    }

    public Program programFeedBacks(Set<ProgramFeedBack> programFeedBacks) {
        this.programFeedBacks = programFeedBacks;
        return this;
    }

    public Program addProgramFeedBack(ProgramFeedBack programFeedBack) {
        this.programFeedBacks.add(programFeedBack);
        programFeedBack.setProgram(this);
        return this;
    }

    public Program removeProgramFeedBack(ProgramFeedBack programFeedBack) {
        this.programFeedBacks.remove(programFeedBack);
        programFeedBack.setProgram(null);
        return this;
    }

    public void setProgramFeedBacks(Set<ProgramFeedBack> programFeedBacks) {
        this.programFeedBacks = programFeedBacks;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public Program exercises(Set<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    public Program addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        exercise.getPrograms().add(this);
        return this;
    }

    public Program removeExercise(Exercise exercise) {
        this.exercises.remove(exercise);
        exercise.getPrograms().remove(this);
        return this;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public Program userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<Calendar> getCalendars() {
        return calendars;
    }

    public Program calendars(Set<Calendar> calendars) {
        this.calendars = calendars;
        return this;
    }

    public Program addCalendar(Calendar calendar) {
        this.calendars.add(calendar);
        calendar.getPrograms().add(this);
        return this;
    }

    public Program removeCalendar(Calendar calendar) {
        this.calendars.remove(calendar);
        calendar.getPrograms().remove(this);
        return this;
    }

    public void setCalendars(Set<Calendar> calendars) {
        this.calendars = calendars;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Program program = (Program) o;
        if (program.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, program.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Program{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", intDate='" + intDate + "'" +
            ", finishDate='" + finishDate + "'" +
            ", interval='" + interval + "'" +
            ", cantRepetition='" + cantRepetition + "'" +
            ", status='" + status + "'" +
            ", isDairy='" + isDairy + "'" +
            '}';
    }
}
