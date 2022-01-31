package com.project.paymybuddy.Entity.User;

import com.project.paymybuddy.Util.GeneralConstraints;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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

    @Length(max= GeneralConstraints.VARIABLE_LENGTH_30, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHABET_CHARACTERS,
            message = "must be alphanumeric, minimum 2 characters")
    @NotBlank(message = " Civility is mandatory")
    private String civility;

    @Length(max= GeneralConstraints.VARIABLE_LENGTH_125, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHABET_CHARACTERS,
            message = "must be alphanumeric, minimum 2 characters")
    @NotBlank(message = " firstname is mandatory")
    private String firstname;

    @Length(max=GeneralConstraints.VARIABLE_LENGTH_125, message = "Max Length must be 30 characters")
    @Pattern(regexp = GeneralConstraints.PATTERN_ALPHABET_CHARACTERS,
            message = "must be alphanumeric, minimum 2 characters")
    @NotBlank(message = " lastname is mandatory")
    private String lastname;

    @Length(max=GeneralConstraints.VARIABLE_LENGTH_125, message = "Max Length must be 30 characters")
    @NotBlank(message = " email is mandatory")
    @Pattern(regexp = GeneralConstraints.PATTERN_USERNAME)
    private String email;

    @Length(max=GeneralConstraints.VARIABLE_LENGTH_125, message = "Max Length must be 30 characters")
    @NotBlank(message = " password is mandatory")
    @Pattern(regexp = GeneralConstraints.PATTERN_PASSWORD,
            message = "The password must contain at least"
                    + " 8 characters that includes"
                    + " any one uppercase letter,"
                    + " any one number and"
                    + " any one symbol ( & ~ # @ = * - + € ^ $ £ µ % )")
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
