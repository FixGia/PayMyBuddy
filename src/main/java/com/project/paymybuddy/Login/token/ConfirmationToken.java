package com.project.paymybuddy.Login.token;

import com.project.paymybuddy.model.User.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ConfirmationToken {

    @SequenceGenerator(name = "confirmation_Token_Sequence",
    sequenceName = "confirmation_Token_Sequence",
    allocationSize = 1 )
    @Id
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "confirmation_Token_Sequence")
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn
    private Users users;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiredAt,
                              Users users) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
        this.users = users;
    }
}
