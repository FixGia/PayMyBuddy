package com.project.paymybuddy.DAO.BankAccounts;

import com.project.paymybuddy.DAO.User.UserEntity;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bank_account", nullable = false)
    private Integer idBankAccount;

    private double Amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "users_id")
    private UserEntity user;

    @Column(name = "rib", nullable = false)
    private String rib;


}
