package account.transfers.dao;

import account.transfers.model.Account;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

@RequiredArgsConstructor( onConstructor = @__({ @Inject}))
public class AccountDaoImpl implements AccountDao {
    private final Provider<EntityManager> em;

    @Override
    public void addMoney(long accountId, BigDecimal value) {
        Account account = this.getAccount(accountId);
        BigDecimal newBalance = account.getBalance().add(value);
        if (newBalance.compareTo(value) < 0) throw new RuntimeException("Not enough money");
        account.setBalance(account.getBalance().add(value));
    }

    @Override
    public Account getAccount(long accountId) {
        Account account = em.get().find( Account.class, accountId);
        if (account==null) throw new RuntimeException("Account with id " + accountId + " is not found");
        return account;
    }

    @Override
    public void createAccount(Long id, BigDecimal balance) {
        em.get().persist(new Account(id,balance));
    }
}
