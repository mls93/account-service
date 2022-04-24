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

import static com.google.inject.matcher.Matchers.any;
import static java.util.stream.Collectors.toSet;

public class AccountModule extends AbstractModule {
    private final NavigableSet<Long> keysSet = new ConcurrentSkipListSet<>();

    @Override
    protected void configure() {
        install(new AccountDaoModule());
        bind(AccountService.class).to(AccountServiceImpl.class).asEagerSingleton();
        install(new AccountServletModule());
        bindListener(any(), new AccountServiceWrapperTypeListener());
    }

    @Named("threadSafeAccountServiceWrapper")
    @Singleton
    @Provides
    public AccountService threadSafeAccountServiceWrapper(AccountService accountService) {
        return new ThreadSafeAccountServiceWrapper(accountService, keysSet);
    }

    class AccountServiceWrapperTypeListener implements TypeListener {
        @Override
        public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
            encounter.register((InjectionListener<I>) injectee -> {
                System.out.println(injectee.getClass());
                if (injectee instanceof AccountServiceImpl) {
                    setupKeys(((AccountServiceImpl) injectee).getAllAccounts());
                }
            });
        }

        private void setupKeys(List<AccountDto> accounts){
            keysSet.addAll(accounts.stream().map(AccountDto::getId).collect(toSet()));
        }
    }
}