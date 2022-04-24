package ru.mls.account;

import com.google.inject.persist.Transactional;
import lombok.RequiredArgsConstructor;
import ru.mls.AccountService;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    @Override
    //todo implement transaction management
    @Transactional
    public void transfer(Long src, Long dest, BigDecimal value) {
        addMoney(src, value.multiply(BigDecimal.valueOf(-1)));
        addMoney(dest, value);
    }

    private void addMoney(long accountId, BigDecimal value) {
        AccountDto account = accountDao.getAccount(accountId);
        BigDecimal newBalance = account.getBalance().add(value);
        if (newBalance.compareTo(value) < 0) throw new RuntimeException("Not enough money");
        accountDao.addMoney(accountId, value);
    }

    @Override
    public BigDecimal getBalance(long accountId) {
        return accountDao.getAccount(accountId).getBalance();
    }

    @Override
    @Transactional
    public void createAccount(Long id, BigDecimal balance) {
        AccountDto account = accountDao.getAccount(id);
        if (account != null) throw new RuntimeException("Account with id" + id + " already exists!");
        accountDao.createAccount(id, balance);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountDao.getAllAccounts();
    }
}
