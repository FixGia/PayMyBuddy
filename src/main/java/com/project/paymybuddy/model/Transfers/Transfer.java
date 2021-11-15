package com.project.paymybuddy.model.Transfers;


import com.project.paymybuddy.model.BankAccounts.BankAccountEntity;
import com.project.paymybuddy.model.User.Users;
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
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private double amount;
    private double credit;
    private double debit;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne
    @JoinColumn( name= "bank_account_id")
    private BankAccountEntity bankAccount;
}
