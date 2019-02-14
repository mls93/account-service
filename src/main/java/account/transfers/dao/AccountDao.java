package account.transfers.dao;

import account.transfers.model.Account;

import java.math.BigDecimal;

public interface AccountDao {
    void addMoney(long accountId, BigDecimal value);

    Account getAccount(long accountId);

    void createAccount(Long id, BigDecimal balance);
}
