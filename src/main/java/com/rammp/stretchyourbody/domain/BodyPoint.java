package com.rammp.stretchyourbody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BodyPoint.
 */
@Entity
@Table(name = "body_point")
public class BodyPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bodypart")
    private String bodypart;

    @Column(name = "idbodypart")
    private Long idbodypart;

    @ManyToOne(optional = false)
    @NotNull
    private UserApp userApp;

    @OneToMany(mappedBy = "bodyPoint")
    @JsonIgnore
    private Set<BodyPointRegistry> bodyPointRegistries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBodypart() {
        return bodypart;
    }

    public BodyPoint bodypart(String bodypart) {
        this.bodypart = bodypart;
        return this;
    }

    public void setBodypart(String bodypart) {
        this.bodypart = bodypart;
    }

    public Long getIdbodypart() {
        return idbodypart;
    }

    public BodyPoint idbodypart(Long idbodypart) {
        this.idbodypart = idbodypart;
        return this;
    }

    public void setIdbodypart(Long idbodypart) {
        this.idbodypart = idbodypart;
    }

    public UserApp getUserApp() {
        return userApp;
    }

    public BodyPoint userApp(UserApp userApp) {
        this.userApp = userApp;
        return this;
    }

    public void setUserApp(UserApp userApp) {
        this.userApp = userApp;
    }

    public Set<BodyPointRegistry> getBodyPointRegistries() {
        return bodyPointRegistries;
    }

    public BodyPoint bodyPointRegistries(Set<BodyPointRegistry> bodyPointRegistries) {
        this.bodyPointRegistries = bodyPointRegistries;
        return this;
    }

    public BodyPoint addBodyPointRegistry(BodyPointRegistry bodyPointRegistry) {
        this.bodyPointRegistries.add(bodyPointRegistry);
        bodyPointRegistry.setBodyPoint(this);
        return this;
    }

    public BodyPoint removeBodyPointRegistry(BodyPointRegistry bodyPointRegistry) {
        this.bodyPointRegistries.remove(bodyPointRegistry);
        bodyPointRegistry.setBodyPoint(null);
        return this;
    }

    public void setBodyPointRegistries(Set<BodyPointRegistry> bodyPointRegistries) {
        this.bodyPointRegistries = bodyPointRegistries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BodyPoint bodyPoint = (BodyPoint) o;
        if (bodyPoint.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bodyPoint.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BodyPoint{" +
            "id=" + id +
            ", bodypart='" + bodypart + "'" +
            ", idbodypart='" + idbodypart + "'" +
            '}';
    }
}
