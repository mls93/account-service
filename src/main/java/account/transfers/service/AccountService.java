package account.transfers.service;

import lombok.NonNull;

import java.math.BigDecimal;

public interface AccountService {
    void transfer (@NonNull Long src, @NonNull Long dest, BigDecimal value);

    BigDecimal getBalance(long accountId);

    void createAccount(Long id, BigDecimal balance);
}
