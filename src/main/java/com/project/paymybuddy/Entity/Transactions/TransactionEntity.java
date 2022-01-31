package com.project.paymybuddy.Entity.Transactions;


import com.project.paymybuddy.Entity.User.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @ManyToOne
    @JoinColumn(name = "beneficiary_id", nullable = false)
    private UserEntity beneficiary;

    @ManyToOne
    @JoinColumn(name= "payer_id", nullable = false)
    private UserEntity payer;


}
