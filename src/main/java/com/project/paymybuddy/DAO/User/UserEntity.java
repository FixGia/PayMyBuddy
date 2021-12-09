package com.project.paymybuddy.DAO.User;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name= "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String civility;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private double wallet;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
    private Boolean locked = false;
    private Boolean enabled = false;

    /**
     * Contact List
     * Create a relation between user and user
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserEntity> contactList;

    public UserEntity(String firstname,
                      String lastname,
                      String email,
                      String civility,
                      String password
    ) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.civility = civility;
        this.email = email;
        this.password = password;

    }

}
