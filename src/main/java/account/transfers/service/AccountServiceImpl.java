package account.transfers.service;

import account.transfers.dao.AccountDao;
import com.google.inject.persist.Transactional;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.math.BigDecimal;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class AccountServiceImpl implements AccountService {
    private final AccountDao accountDao;

    @Override
    @Transactional
    public void transfer(Long src, Long dest, BigDecimal value) {
        accountDao.addMoney(src, value.multiply(BigDecimal.valueOf(-1)));
        accountDao.addMoney(dest, value);
    }

    @Override
    public BigDecimal getBalance(long accountId) {
        return accountDao.getAccount(accountId).getBalance();
    }

    @Override
    @Transactional
    public void createAccount(Long id, BigDecimal balance) {
        accountDao.createAccount(id, balance);
    }
}
