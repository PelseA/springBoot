package ru.pelse.myjira.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Image {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    private String path;

    @ManyToOne
    @JoinColumn
    private AffairCourse affairCourse;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public AffairCourse getAffairCourse() {
        return affairCourse;
    }

    public void setAffairCourse(AffairCourse affairCourse) {
        this.affairCourse = affairCourse;
    }
}
