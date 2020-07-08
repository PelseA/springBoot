package ru.pelse.myjira.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id;

    @Column(nullable = false, unique = true)
    @Email(message = "Проверьте правильность e-mail")
    @NotEmpty(message = "Обязательная информация: укажите e-mail")
    private String username;  //уникальным значением будет email

    @Column(nullable = false)
    @NotBlank(message = "Придумайте пароль")
    @Size(min = 5, message = "Минимум 5 символов")
    private String password;

    @Transient //не добавлять поле в БД
    @NotBlank(message = "Повторите пароль")
    private String password2;

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false)
    @NotEmpty(message = "Обязательная информация: Ваше имя")
    private String firstname;

    private String lastname;

    // дата начала события будет приходить строкой
    // @DateTimeFormat - в каком виде будет дата
    @DateTimeFormat(pattern = "MM/dd/yyyy") //yyyy-MM-dd
    private LocalDate birth;

    private String phone;

    private boolean active;
    private String activationCode;

    //у одного пользователя несколько проектов
    @OneToMany(mappedBy = "user", //имя поля в классе Project
                cascade = CascadeType.ALL, //при удалении пользователя, удалятся его проекты
                orphanRemoval = true, //при удалении проекта из коллекции -> удаление его из БД
                fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(mappedBy = "user", //имя поля в классе Subscriber
            cascade = CascadeType.ALL,
            orphanRemoval = true, //при удалении подписчика из коллекции -> удаление его из БД
            fetch = FetchType.LAZY)
    private List<Subscriber> subscribers = new ArrayList<>();

    @Column(nullable = false)
    @ManyToMany(cascade = CascadeType.REFRESH,
                fetch = FetchType.EAGER)
    @JoinTable // промежуточная таблица
    private Set<Role> roles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles(); // Role must implement GrantedAuthority
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
