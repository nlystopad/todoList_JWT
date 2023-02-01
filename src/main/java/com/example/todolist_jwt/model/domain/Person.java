package com.example.todolist_jwt.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Person implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "email", nullable = false)
    @Email
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToMany(cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_statuses",
            joinColumns = {@JoinColumn(name = "status_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @JsonIgnore
    private Set<Status> statuses;
    @OneToMany(mappedBy = "person",
            fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Task> tasks;
    private Boolean enabled = true;

    public Person(String email, String password) {
        this.email = email;
        this.password = password;
        this.enabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
