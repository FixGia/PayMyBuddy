package com.project.paymybuddy.Entity.BankAccounts;

import com.project.paymybuddy.Entity.User.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@Table(name = "bank_account")
public class BankAccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_bank_account", nullable = false)
    private Integer idBankAccount;

    private double Amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Column(name = "iban" )
    private String iban;


}
