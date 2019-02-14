package account.transfers.config;

import account.transfers.config.database.DatabaseServer;
import account.transfers.config.database.DatabaseServerProvider;
import account.transfers.dao.AccountDao;
import account.transfers.dao.AccountDaoImpl;
import account.transfers.service.AccountService;
import account.transfers.service.AccountServiceImpl;
import account.transfers.service.ThreadSafeAccountServiceWrapper;
import account.transfers.servlets.AccountCreateServlet;
import account.transfers.servlets.AccountGetServlet;
import account.transfers.servlets.TransferServlet;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Named;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

public class AccountModule extends AbstractModule {
    private final NavigableSet<Long> keysSet = new ConcurrentSkipListSet<>();

    @Override
    protected void configure() {
        bind(DatabaseServer.class)
                .toProvider(DatabaseServerProvider.class);
        bind(AccountDao.class).to(AccountDaoImpl.class).asEagerSingleton();
        bind(AccountService.class).to(AccountServiceImpl.class).asEagerSingleton();
    }

    @Named("threadSafeAccountServiceWrapper")
    @Provides
    public AccountService threadSafeAccountServiceWrapper(AccountService accountService) {
        return new ThreadSafeAccountServiceWrapper(accountService, keysSet);
    }

    @Provides
    public TransferServlet transferServlet(@Named("threadSafeAccountServiceWrapper")
                                           AccountService accountService){
        return new TransferServlet(accountService);
    }

    @Provides
    public AccountGetServlet accountGetServlet(@Named("threadSafeAccountServiceWrapper")
                                                       AccountService accountService){
        return new AccountGetServlet(accountService);
    }

    @Provides
    public AccountCreateServlet accountCreateServlet(@Named("threadSafeAccountServiceWrapper")
                                                       AccountService accountService){
        return new AccountCreateServlet(accountService);
    }
}