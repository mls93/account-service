package ru.mls.account;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ru.mls.AccountService;
import ru.mls.account.dao.AccountDaoModule;
import ru.mls.account.servlets.AccountServletModule;

import javax.inject.Named;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class AccountModule extends AbstractModule {
    private final NavigableSet<Long> keysSet = new ConcurrentSkipListSet<>();

    @Override
    protected void configure() {
        install(new AccountDaoModule());
        bind(AccountService.class).to(AccountServiceImpl.class).asEagerSingleton();
        install(new AccountServletModule());
    }

    @Named("threadSafeAccountServiceWrapper")
    @Provides
    public AccountService threadSafeAccountServiceWrapper(AccountService accountService) {
        return new ThreadSafeAccountServiceWrapper(accountService, keysSet);
    }

}