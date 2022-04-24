package ru.mls.migration;

import com.google.inject.AbstractModule;
import ru.mls.MigrationProcessor;

public class MigrationProcessorModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MigrationProcessor.class)
                .to(LiquibaseMigrationProcessor.class).asEagerSingleton();
    }
}
