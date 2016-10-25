package com.mblearning.mblms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Classes.
 */
@Entity
@Table(name = "classes")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Classes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "year", nullable = false)
    private Integer year;

    @NotNull
    @Column(name = "semester", nullable = false)
    private Integer semester;

    @Column(name = "summary")
    private String summary;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "classes_user",
               joinColumns = @JoinColumn(name="classes_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="ID"))
    private Set<User> users = new HashSet<>();

    @ManyToOne
    private School school;

    @ManyToMany(mappedBy = "classes")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Profiles> profiles = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Classes name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public Classes code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getYear() {
        return year;
    }

    public Classes year(Integer year) {
        this.year = year;
        return this;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public Classes semester(Integer semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getSummary() {
        return summary;
    }

    public Classes summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Classes users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Classes addUser(User user) {
        users.add(user);
        return this;
    }

    public Classes removeUser(User user) {
        users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public School getSchool() {
        return school;
    }

    public Classes school(School school) {
        this.school = school;
        return this;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<Profiles> getProfiles() {
        return profiles;
    }

    public Classes profiles(Set<Profiles> profiles) {
        this.profiles = profiles;
        return this;
    }

//    public Classes addProfiles(Profiles profiles) {
//        profiles.add(profiles);
//        profiles.getClasses().add(this);
//        return this;
//    }
//
//    public Classes removeProfiles(Profiles profiles) {
//        profiles.remove(profiles);
//        profiles.getClasses().remove(this);
//        return this;
//    }

    public void setProfiles(Set<Profiles> profiles) {
        this.profiles = profiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Classes classes = (Classes) o;
        if(classes.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, classes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Classes{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", code='" + code + "'" +
            ", year='" + year + "'" +
            ", semester='" + semester + "'" +
            ", summary='" + summary + "'" +
            '}';
    }
}
