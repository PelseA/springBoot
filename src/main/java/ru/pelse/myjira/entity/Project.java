package ru.pelse.myjira.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @NotBlank(message = "Добавьте название проекта")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Добавьте описание проекта")
    @Length(max = 2048, message = "Описание слишком длинное, допустимо до 1000 символов")
    @Column(nullable = false, length = 2048)
    private String description;

    //несколько проектов у одного пользователя
    @ManyToOne
    @JoinColumn
    private User user;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    private List<Subscriber> subscribers = new ArrayList<>();

    public Project() {
    }

    public Project(@NotBlank(message = "Добавьте название проекта") String title,
                   @NotBlank(message = "Добавьте описание проекта") @Length(max = 2048, message = "Описание слишком длинное, допустимо до 1000 символов") String description,
                   User user) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> users) {
        this.subscribers = users;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
