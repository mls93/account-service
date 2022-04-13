package ru.mls.account.servlets;

import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;
import ru.mls.AccountService;

import javax.inject.Named;

public class AccountServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        serve("/transferMoney").with(TransferServlet.class);
        serve("/getBalance").with(AccountGetServlet.class);
        serve("/create").with(AccountCreateServlet.class);
    }

    @Provides
    public TransferServlet transferServlet(@Named("threadSafeAccountServiceWrapper")
                                                   AccountService accountService) {
        return new TransferServlet(accountService);
    }

    @Provides
    public AccountGetServlet accountGetServlet(@Named("threadSafeAccountServiceWrapper")
                                                       AccountService accountService) {
        return new AccountGetServlet(accountService);
    }

    @Provides
    public AccountCreateServlet accountCreateServlet(@Named("threadSafeAccountServiceWrapper")
                                                             AccountService accountService) {
        return new AccountCreateServlet(accountService);
    }
}
