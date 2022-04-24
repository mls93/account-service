package ru.mls.account.dao;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import org.h2.jdbcx.JdbcConnectionPool;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.Transaction;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import ru.mls.account.AccountDao;
import ru.mls.connection.h2.ConnectionProviderModule;

import javax.sql.DataSource;

public class AccountDaoModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ConnectionProviderModule());
        bind(AccountDao.class).to(JooqAccountDao.class).asEagerSingleton();
    }

    public DataSourceConnectionProvider connectionProvider(
            DataSource dataSource
    ) {
        return new DataSourceConnectionProvider(dataSource);
    }

    @Provides
    public DSLContext dslContext(DataSource dataSource) {
        return new DefaultDSLContext(configuration(dataSource));
    }

    public DefaultConfiguration configuration(DataSource dataSource) {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(connectionProvider(dataSource));
        return jooqConfiguration;
    }

}