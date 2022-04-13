package ru.mls;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import ru.mls.account.AccountModule;
import ru.mls.server.AccountServerModule;

public class AccountsServiceBootstrapper {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new AccountServerModule(),
                new AccountModule(),
                new JpaPersistModule("item-manager")
        );
        injector.getInstance(PersistService.class).start();
        injector.getInstance(AccountServer.class).start();
    }
}
