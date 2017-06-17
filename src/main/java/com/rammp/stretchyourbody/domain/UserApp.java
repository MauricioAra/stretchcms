package com.rammp.stretchyourbody.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserApp.
 */
@Entity
@Table(name = "user_app")
public class UserApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private String age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "height")
    private Double height;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToOne
    @JoinColumn(unique = true)
    private UserHealth userHealth;

    @OneToMany(mappedBy = "userApp")
    @JsonIgnore
    private Set<Program> programs = new HashSet<>();

    @OneToMany(mappedBy = "userApp")
    @JsonIgnore
    private Set<UserVitality> userVitalities = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "user_app_exercise",
               joinColumns = @JoinColumn(name="user_apps_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="exercises_id", referencedColumnName="id"))
    private Set<Exercise> exercises = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserApp name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public UserApp lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAge() {
        return age;
    }

    public UserApp age(String age) {
        this.age = age;
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public UserApp gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Double getWeight() {
        return weight;
    }

    public UserApp weight(Double weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getHeight() {
        return height;
    }

    public UserApp height(Double height) {
        this.height = height;
        return this;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public User getUser() {
        return user;
    }

    public UserApp user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserHealth getUserHealth() {
        return userHealth;
    }

    public UserApp userHealth(UserHealth userHealth) {
        this.userHealth = userHealth;
        return this;
    }

    public void setUserHealth(UserHealth userHealth) {
        this.userHealth = userHealth;
    }

    public Set<Program> getPrograms() {
        return programs;
    }

    public UserApp programs(Set<Program> programs) {
        this.programs = programs;
        return this;
    }

    public UserApp addProgram(Program program) {
        this.programs.add(program);
        program.setUserApp(this);
        return this;
    }

    public UserApp removeProgram(Program program) {
        this.programs.remove(program);
        program.setUserApp(null);
        return this;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }

    public Set<UserVitality> getUserVitalities() {
        return userVitalities;
    }

    public UserApp userVitalities(Set<UserVitality> userVitalities) {
        this.userVitalities = userVitalities;
        return this;
    }

    public UserApp addUserVitality(UserVitality userVitality) {
        this.userVitalities.add(userVitality);
        userVitality.setUserApp(this);
        return this;
    }

    public UserApp removeUserVitality(UserVitality userVitality) {
        this.userVitalities.remove(userVitality);
        userVitality.setUserApp(null);
        return this;
    }

    public void setUserVitalities(Set<UserVitality> userVitalities) {
        this.userVitalities = userVitalities;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public UserApp exercises(Set<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    public UserApp addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        exercise.getUserApps().add(this);
        return this;
    }

    public UserApp removeExercise(Exercise exercise) {
        this.exercises.remove(exercise);
        exercise.getUserApps().remove(this);
        return this;
    }

    public void setExercises(Set<Exercise> exercises) {
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
        UserApp userApp = (UserApp) o;
        if (userApp.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userApp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserApp{" +
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
