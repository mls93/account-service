package ru.mls.server;

import com.google.inject.AbstractModule;
import ru.mls.AccountServer;

public class AccountServerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountServer.class).to(JettyAccountServer.class).asEagerSingleton();
    }
}
