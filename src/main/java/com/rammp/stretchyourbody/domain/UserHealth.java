package com.rammp.stretchyourbody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserHealth.
 */
@Entity
@Table(name = "user_health")
public class UserHealth implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "work_hours", nullable = false)
    private Integer workHours;

    @Column(name = "does_work_out")
    private Boolean doesWorkOut;

    @Column(name = "is_smoker")
    private Boolean isSmoker;

    @Column(name = "is_health_food")
    private Boolean isHealthFood;

    @ManyToMany
    @JoinTable(name = "user_health_body_part",
               joinColumns = @JoinColumn(name="user_healths_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="body_parts_id", referencedColumnName="id"))
    private Set<BodyPart> bodyParts = new HashSet<>();

    @OneToOne(mappedBy = "userHealth")
    @JsonIgnore
    private UserApp userApp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWorkHours() {
        return workHours;
    }

    public UserHealth workHours(Integer workHours) {
        this.workHours = workHours;
        return this;
    }

    public void setWorkHours(Integer workHours) {
        this.workHours = workHours;
    }

    public Boolean isDoesWorkOut() {
        return doesWorkOut;
    }

    public UserHealth doesWorkOut(Boolean doesWorkOut) {
        this.doesWorkOut = doesWorkOut;
        return this;
    }

    public void setDoesWorkOut(Boolean doesWorkOut) {
        this.doesWorkOut = doesWorkOut;
    }

    public Boolean isIsSmoker() {
        return isSmoker;
    }

    public UserHealth isSmoker(Boolean isSmoker) {
        this.isSmoker = isSmoker;
        return this;
    }

    public void setIsSmoker(Boolean isSmoker) {
        this.isSmoker = isSmoker;
    }

    public Boolean isIsHealthFood() {
        return isHealthFood;
    }

    public UserHealth isHealthFood(Boolean isHealthFood) {
        this.isHealthFood = isHealthFood;
        return this;
    }

    public void setIsHealthFood(Boolean isHealthFood) {
        this.isHealthFood = isHealthFood;
    }

    public Set<BodyPart> getBodyParts() {
        return bodyParts;
    }

    public UserHealth bodyParts(Set<BodyPart> bodyParts) {
        this.bodyParts = bodyParts;
        return this;
    }

    public UserHealth addBodyPart(BodyPart bodyPart) {
        this.bodyParts.add(bodyPart);
        bodyPart.getUserHealths().add(this);
        return this;
    }

    public UserHealth removeBodyPart(BodyPart bodyPart) {
        this.bodyParts.remove(bodyPart);
        bodyPart.getUserHealths().remove(this);
        return this;
    }

    public void setBodyParts(Set<BodyPart> bodyParts) {
        this.bodyParts = bodyParts;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public UserHealth userApp(UserApp userApp) {
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
        UserHealth userHealth = (UserHealth) o;
        if (userHealth.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userHealth.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserHealth{" +
            "id=" + id +
            ", workHours='" + workHours + "'" +
            ", doesWorkOut='" + doesWorkOut + "'" +
            ", isSmoker='" + isSmoker + "'" +
            ", isHealthFood='" + isHealthFood + "'" +
            '}';
    }
}
