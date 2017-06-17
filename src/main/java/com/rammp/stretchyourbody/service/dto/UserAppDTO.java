package com.rammp.stretchyourbody.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the UserApp entity.
 */
public class UserAppDTO implements Serializable {

    private Long id;

    private String name;

    private String lastName;

    private String age;

    private String gender;

    private Double weight;

    private Double height;

    private Long userId;

    private String userEmail;

    private Long userHealthId;

    private Set<ExerciseDTO> exercises = new HashSet<>();

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
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getUserHealthId() {
        return userHealthId;
    }

    public void setUserHealthId(Long userHealthId) {
        this.userHealthId = userHealthId;
    }

    public Set<ExerciseDTO> getExercises() {
        return exercises;
    }

    public void setExercises(Set<ExerciseDTO> exercises) {
        this.exercises = exercises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAppDTO userAppDTO = (UserAppDTO) o;

        if ( ! Objects.equals(id, userAppDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserAppDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", lastName='" + lastName + "'" +
            ", age='" + age + "'" +
            ", gender='" + gender + "'" +
            ", weight='" + weight + "'" +
            ", height='" + height + "'" +
            '}';
    }
}
