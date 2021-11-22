package com.project.paymybuddy.model.Transactions;


import com.project.paymybuddy.model.User.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name= "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private double commission;
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDate creationDate = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "beneficiary_id", nullable = false)
    private UserEntity beneficiary;

    @ManyToOne
    @JoinColumn(name= "payer_id", nullable = false)
    private UserEntity payer;


}
