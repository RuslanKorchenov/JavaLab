package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String username;
    String email;
    String password;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    State state;
    @Column(name = "confirm_code")
    String confirmCode;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    List<FileInfo> files = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
    List<Order> orders = new ArrayList<>();
}
