package com.rammp.stretchyourbody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A FoodTag.
 */
@Entity
@Table(name = "food_tag")
public class FoodTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "foodTags")
    @JsonIgnore
    private Set<Food> foods = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FoodTag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Food> getFoods() {
        return foods;
    }

    public FoodTag foods(Set<Food> foods) {
        this.foods = foods;
        return this;
    }

    public FoodTag addFood(Food food) {
        this.foods.add(food);
        food.getFoodTags().add(this);
        return this;
    }

    public FoodTag removeFood(Food food) {
        this.foods.remove(food);
        food.getFoodTags().remove(this);
        return this;
    }

    public void setFoods(Set<Food> foods) {
        this.foods = foods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FoodTag foodTag = (FoodTag) o;
        if (foodTag.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, foodTag.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FoodTag{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
