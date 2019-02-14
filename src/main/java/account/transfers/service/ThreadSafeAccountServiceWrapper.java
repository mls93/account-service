package account.transfers.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.NavigableSet;
import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class ThreadSafeAccountServiceWrapper implements AccountService {
    private final AccountService basicAccountService;
    private final NavigableSet<Long> keys;

    @Override
    public void transfer(@NonNull Long src, @NonNull Long dest, BigDecimal value) {
        Long min = src < dest ? src : dest;
        Long max = src > dest ? src : dest;
        synchronized (searchInKeys(min)) {
            synchronized (searchInKeys(max)) {
                basicAccountService.transfer(src, dest, value);
            }
        }
    }

    private Long searchInKeys(Long key) {
        Long ceil = keys.ceiling(key);
        Long floor = keys.floor(key);
        if (!Objects.equals(ceil, floor)) throw new RuntimeException("Account with id " + key + " is not exist");
        return ceil;
    }

    @Override
    public BigDecimal getBalance(long accountId) {
        return basicAccountService.getBalance(accountId);
    }

    @Override
    public void createAccount(Long id, BigDecimal balance) {
        basicAccountService.createAccount(id, balance);
        keys.add(id);
    }
}
