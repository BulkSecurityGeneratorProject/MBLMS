package com.mblearning.mblms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Profiles.
 */
@Entity
@Table(name = "profiles")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Profiles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "profiles_classes",
               joinColumns = @JoinColumn(name="profiles_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="classes_id", referencedColumnName="ID"))
    private Set<Classes> classes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Profiles name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public Profiles user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Classes> getClasses() {
        return classes;
    }

    public Profiles classes(Set<Classes> classes) {
        this.classes = classes;
        return this;
    }

//    public Profiles addClasses(Classes classes) {
//        classes.add(classes);
//        classes.getProfiles().add(this);
//        return this;
//    }
//
//    public Profiles removeClasses(Classes classes) {
//        classes.remove(classes);
//        classes.getProfiles().remove(this);
//        return this;
//    }

    public void setClasses(Set<Classes> classes) {
        this.classes = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Profiles profiles = (Profiles) o;
        if(profiles.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, profiles.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Profiles{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
