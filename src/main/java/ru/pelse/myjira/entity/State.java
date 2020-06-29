package ru.pelse.myjira.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class State {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @Column(nullable = false, length = 100)
    private String value;

    @OneToMany(mappedBy = "state")
    private List<AffairCourse> affairCourses = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<AffairCourse> getAffairCourses() {
        return affairCourses;
    }

    public void setAffairCourses(List<AffairCourse> affairCourses) {
        this.affairCourses = affairCourses;
    }
}
