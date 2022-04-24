package ru.mls.properties;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesModule extends AbstractModule {
    @Override
    protected void configure() {
        Names.bindProperties(binder(), getProperties());
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        InputStream applicationPropertiesStream = PropertiesModule.class.getClassLoader().getResourceAsStream("application.properties");
        try {
            properties.load(applicationPropertiesStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}
