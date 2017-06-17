package com.rammp.stretchyourbody.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserVitality.
 */
@Entity
@Table(name = "user_vitality")
public class UserVitality implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_comment")
    private String comment;

    @Column(name = "jhi_range")
    private Integer range;

    @ManyToOne
    private UserApp userApp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public UserVitality comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRange() {
        return range;
    }

    public UserVitality range(Integer range) {
        this.range = range;
        return this;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public UserVitality userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserVitality userVitality = (UserVitality) o;
        if (userVitality.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userVitality.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserVitality{" +
            "id=" + id +
            ", comment='" + comment + "'" +
            ", range='" + range + "'" +
            '}';
    }
}
