package ru.mls.account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {
    void addMoney(long accountId, BigDecimal value);

    AccountDto getAccount(long accountId);

    void createAccount(Long id, BigDecimal balance);

    List<AccountDto> getAllAccounts();
}
