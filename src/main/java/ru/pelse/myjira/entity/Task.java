package ru.pelse.myjira.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Task {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd' в 'HH:mm")
    private LocalDateTime dateCreate;

    @DateTimeFormat(pattern = "yyyy-MM-dd' в 'HH:mm")
    private LocalDateTime dateClose;

    public Task(String title, String description, LocalDateTime dateCreate, Project project) {
        this.title = title;
        this.description = description;
        this.dateCreate = dateCreate;
        this.project = project;
    }

    public Task() {
    }

    //в одном проекте несколько задач
    @ManyToOne
    @JoinColumn
    private Project project;

    @OneToMany(mappedBy = "task")
    private List<Activity> activities = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDateTime getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDateTime dateClose) {
        this.dateClose = dateClose;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
