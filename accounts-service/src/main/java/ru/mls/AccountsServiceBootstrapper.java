package ru.mls;


import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.mls.account.AccountModule;
import ru.mls.properties.PropertiesModule;
import ru.mls.server.AccountServerModule;

public class AccountsServiceBootstrapper {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(
                new PropertiesModule(),
                new AccountServerModule(),
                new AccountModule()
                );
        injector.getInstance(AccountServer.class).start();
    }
}
