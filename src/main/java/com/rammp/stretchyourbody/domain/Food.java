package com.rammp.stretchyourbody.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Food.
 */
@Entity
@Table(name = "food")
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "is_recommended")
    private Boolean isRecommended;

    @ManyToMany
    @JoinTable(name = "food_food_tag",
               joinColumns = @JoinColumn(name="foods_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="food_tags_id", referencedColumnName="id"))
    private Set<FoodTag> foodTags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Food name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public Food image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public Food description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isStatus() {
        return status;
    }

    public Food status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean isIsRecommended() {
        return isRecommended;
    }

    public Food isRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
        return this;
    }

    public void setIsRecommended(Boolean isRecommended) {
        this.isRecommended = isRecommended;
    }

    public Set<FoodTag> getFoodTags() {
        return foodTags;
    }

    public Food foodTags(Set<FoodTag> foodTags) {
        this.foodTags = foodTags;
        return this;
    }

    public Food addFoodTag(FoodTag foodTag) {
        this.foodTags.add(foodTag);
        foodTag.getFoods().add(this);
        return this;
    }

    public Food removeFoodTag(FoodTag foodTag) {
        this.foodTags.remove(foodTag);
        foodTag.getFoods().remove(this);
        return this;
    }

    public void setFoodTags(Set<FoodTag> foodTags) {
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
        Food food = (Food) o;
        if (food.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, food.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Food{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", image='" + image + "'" +
            ", description='" + description + "'" +
            ", status='" + status + "'" +
            ", isRecommended='" + isRecommended + "'" +
            '}';
    }
}
