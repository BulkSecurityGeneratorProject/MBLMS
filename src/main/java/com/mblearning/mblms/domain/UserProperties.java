package com.mblearning.mblms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserProperties.
 */
@Entity
@Table(name = "user_properties")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProperties implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "educator")
    private Boolean educator;

    @Column(name = "gpa")
    private Float gpa;

    @Column(name = "focus")
    private String focus;

    @Column(name = "program")
    private String program;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne
    private School school;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public UserProperties status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean isEducator() {
        return educator;
    }

    public UserProperties educator(Boolean educator) {
        this.educator = educator;
        return this;
    }

    public void setEducator(Boolean educator) {
        this.educator = educator;
    }

    public Float getGpa() {
        return gpa;
    }

    public UserProperties gpa(Float gpa) {
        this.gpa = gpa;
        return this;
    }

    public void setGpa(Float gpa) {
        this.gpa = gpa;
    }

    public String getFocus() {
        return focus;
    }

    public UserProperties focus(String focus) {
        this.focus = focus;
        return this;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getProgram() {
        return program;
    }

    public UserProperties program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public User getUser() {
        return user;
    }

    public UserProperties user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public School getSchool() {
        return school;
    }

    public UserProperties school(School school) {
        this.school = school;
        return this;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserProperties userProperties = (UserProperties) o;
        if(userProperties.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userProperties.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserProperties{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", educator='" + educator + "'" +
            ", gpa='" + gpa + "'" +
            ", focus='" + focus + "'" +
            ", program='" + program + "'" +
            '}';
    }
}
