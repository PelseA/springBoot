package ru.pelse.myjira.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Activity {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd' в 'HH:mm")
    private LocalDateTime date;

    @Column(nullable = false, length = 4000)
    private String action;

    @Column(nullable = false, length = 1000)
    private String currentResult;

    @ManyToOne
    @JoinColumn
    private State state;

    @OneToMany(mappedBy = "activity") // поле в классе Image
    private List<Image> images = new ArrayList<>();

    //в одной задаче несколько действий(activity)
    @ManyToOne
    @JoinColumn
    private Task task;

    public Activity() {
    }

    public Activity(String action, String currentResult, State state, Task task) {
        this.date = LocalDateTime.now();
        this.action = action;
        this.currentResult = currentResult;
        this.state = state;
        this.task = task;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getCurrentResult() {
        return currentResult;
    }

    public void setCurrentResult(String currentResult) {
        this.currentResult = currentResult;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
