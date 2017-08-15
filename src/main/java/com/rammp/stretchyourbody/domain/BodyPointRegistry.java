package com.rammp.stretchyourbody.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A BodyPointRegistry.
 */
@Entity
@Table(name = "body_point_registry")
public class BodyPointRegistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "jhi_comment")
    private String comment;

    @ManyToOne(optional = false)
    @NotNull
    private BodyPoint bodyPoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public BodyPointRegistry type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public BodyPointRegistry comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BodyPoint getBodyPoint() {
        return bodyPoint;
    }

    public BodyPointRegistry bodyPoint(BodyPoint bodyPoint) {
        this.bodyPoint = bodyPoint;
        return this;
    }

    public void setBodyPoint(BodyPoint bodyPoint) {
        this.bodyPoint = bodyPoint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BodyPointRegistry bodyPointRegistry = (BodyPointRegistry) o;
        if (bodyPointRegistry.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, bodyPointRegistry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BodyPointRegistry{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", comment='" + comment + "'" +
            '}';
    }
}
