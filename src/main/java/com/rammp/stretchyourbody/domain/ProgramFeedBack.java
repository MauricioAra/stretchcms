package com.rammp.stretchyourbody.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProgramFeedBack.
 */
@Entity
@Table(name = "program_feed_back")
public class ProgramFeedBack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_useful")
    private Boolean isUseful;

    @Column(name = "is_help_pain")
    private Boolean isHelpPain;

    @ManyToOne
    private Program program;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isIsUseful() {
        return isUseful;
    }

    public ProgramFeedBack isUseful(Boolean isUseful) {
        this.isUseful = isUseful;
        return this;
    }

    public void setIsUseful(Boolean isUseful) {
        this.isUseful = isUseful;
    }

    public Boolean isIsHelpPain() {
        return isHelpPain;
    }

    public ProgramFeedBack isHelpPain(Boolean isHelpPain) {
        this.isHelpPain = isHelpPain;
        return this;
    }

    public void setIsHelpPain(Boolean isHelpPain) {
        this.isHelpPain = isHelpPain;
    }

    public Program getProgram() {
        return program;
    }

    public ProgramFeedBack program(Program program) {
        this.program = program;
        return this;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProgramFeedBack programFeedBack = (ProgramFeedBack) o;
        if (programFeedBack.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, programFeedBack.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProgramFeedBack{" +
            "id=" + id +
            ", isUseful='" + isUseful + "'" +
            ", isHelpPain='" + isHelpPain + "'" +
            '}';
    }
}
