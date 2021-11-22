package com.project.paymybuddy.model.Transfers;


import com.project.paymybuddy.model.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.model.User.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name= "transfer")
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private double amount;
    private String description;
    private double credit;
    private double debit;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn( name= "bank_account_id")
    private BankAccountEntity bankAccount;
}
