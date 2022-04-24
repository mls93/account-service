package ru.mls.account.dao;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import ru.mls.account.AccountDao;
import ru.mls.account.AccountDto;
import ru.mls.tables.records.AccountRecord;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static ru.mls.tables.Account.ACCOUNT;

@RequiredArgsConstructor(onConstructor = @__({@Inject}))
public class JooqAccountDao implements AccountDao {
    private final DSLContext dsl;

    @Override
    public void addMoney(long accountId, BigDecimal value) {
        dsl.update(ACCOUNT)
                .set(ACCOUNT.BALANCE, ACCOUNT.BALANCE.plus(value))
                .where(ACCOUNT.ID.eq(accountId))
                .execute();
    }

    @Override
    public AccountDto getAccount(long accountId) {
        AccountRecord accountRecord = dsl.selectFrom(ACCOUNT)
                .where(ACCOUNT.ID.eq(accountId))
                .fetchAny();
        if (accountRecord == null) return null;
        return accountRecord.into(AccountDto.class);
    }

    @Override
    public void createAccount(Long id, BigDecimal balance) {
        dsl.insertInto(ACCOUNT)
                .set(ACCOUNT.ID, id)
                .set(ACCOUNT.BALANCE, balance)
                .execute();
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return dsl.selectFrom(ACCOUNT)
                .fetch()
                .into(AccountDto.class);
    }
}
