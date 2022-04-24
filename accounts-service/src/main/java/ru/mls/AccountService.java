package ru.mls;

import lombok.NonNull;
import ru.mls.account.AccountDto;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    void transfer (@NonNull Long src, @NonNull Long dest, BigDecimal value);

    BigDecimal getBalance(long accountId);

    void createAccount(Long id, BigDecimal balance);

    List<AccountDto> getAllAccounts();
}
