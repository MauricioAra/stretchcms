package com.rammp.stretchyourbody.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Food entity.
 */
public class FoodDTO implements Serializable {

    private Long id;

    private String name;

    private String image;

    private String description;

    private Boolean status;

    private Boolean isRecommended;

    private Set<FoodTagDTO> foodTags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
    public Boolean getIsRecommended() {
        return isRecommended;
    }

    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    public Set<FoodTagDTO> getFoodTags() {
        return foodTags;
    }

    public void setFoodTags(Set<FoodTagDTO> foodTags) {
        this.foodTags = foodTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FoodDTO foodDTO = (FoodDTO) o;

        if ( ! Objects.equals(id, foodDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FoodDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", image='" + image + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            ", isRecommended='" + isRecommended + "'" +
            '}';
    }
}
