package account.transfers.config.database;

import javax.inject.Provider;

public class DatabaseServerProvider implements Provider<DatabaseServer> {

    @Override
    public DatabaseServer get() {
        return new H2DatabaseServer("sa", "", "accounts");
    }
}
