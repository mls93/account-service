package ru.mls.account;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import ru.mls.AccountService;
import ru.mls.account.dao.AccountDaoModule;
import ru.mls.account.servlets.AccountServletModule;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

import static com.google.inject.matcher.Matchers.any;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

public class AccountModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new AccountDaoModule());
        bind(AccountService.class).to(AccountServiceImpl.class).asEagerSingleton();
        install(new AccountServletModule());
    }

    @Named("threadSafeAccountServiceWrapper")
    @Singleton
    @Provides
    public AccountService threadSafeAccountServiceWrapper(AccountService accountService) {
        List<AccountDto> allAccounts = accountService.getAllAccounts();
        NavigableSet<Long> keysSet = allAccounts.stream().map(AccountDto::getId).collect(Collectors.toCollection(ConcurrentSkipListSet::new));
        return new ThreadSafeAccountServiceWrapper(accountService, keysSet);
    }
}