package ru.mls.account.dao;

import com.google.inject.AbstractModule;
import ru.mls.account.AccountDao;

public class AccountDaoModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(AccountDao.class).to(AccountDaoImpl.class).asEagerSingleton();
    }
}