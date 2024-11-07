package br.com.pettz.integrationstestes.configs;

import java.util.Map;
import java.util.stream.Stream;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

@ContextConfiguration(initializers = AbstractIntegrationTest.initializer.class)
public class AbstractIntegrationTest {

    static class initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        static PostgreSQLContainer<?> postgresql = new PostgreSQLContainer<>("postgres:16.4");

        private static void startContainers() {
            Startables.deepStart(Stream.of(postgresql)).join();
        }

        private static Map<String, String> createConnectionConfigurantion() {
            return Map.of(
                "spring.datasource.url", postgresql.getJdbcUrl(),
                "spring.datasource.username", postgresql.getUsername(),
                "spring.datasource.password", postgresql.getPassword());
        }

        @Override
        @SuppressWarnings({ "rawtypes", "unchecked" })
        public void initialize(ConfigurableApplicationContext applicationContext) {
            startContainers();
            ConfigurableEnvironment environment = applicationContext.getEnvironment();
            MapPropertySource testContainers = new MapPropertySource("testContainers", (Map) createConnectionConfigurantion());
            environment.getPropertySources().addFirst(testContainers);
        }
    }    
}