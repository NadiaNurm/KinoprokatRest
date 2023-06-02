package com.example.kinoprokatrest.models;

import com.example.kinoprokatrest.models.Film;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class User implements UserDetails {
    /*
        Модель пользователя. Пользователь имеет имя и пароль, подписку. Он так же может купить отдельные фильмы, которые хранятся в списке.
    */
    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Roles> roles;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_film",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="film_id")
    )
    private List<Film> filmList;
    private boolean enabled; //

    @JsonIgnore
    @OneToOne()
    private Subscription subscriptions;

    public User(Long id, String username, String password, List<Roles> roles, List<Film> filmList, boolean enabled, Subscription subscriptions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.filmList = filmList;
        this.enabled = enabled;
        this.subscriptions = subscriptions;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }


    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public List<Film> getFilmList() {
        return filmList;
    }

    public void setFilmList(List<Film> filmList) {
        this.filmList = filmList;
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
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Subscription getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Subscription subscriptions) {
        this.subscriptions = subscriptions;
    }
}
