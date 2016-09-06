package de.stytex.foobar;

import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import de.stytex.foobar.config.constant.Constants;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.HystrixAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.retry.annotation.EnableRetry;

/**
 * @author Jianxin Zhong
 *
 * Created by on 01.09.16.
 *
 */

@SpringBootApplication(exclude = {HystrixAutoConfiguration.class})
@EnableRetry
//@ComponentScan
//@EnableAutoConfiguration(exclude = {LiquibaseAutoConfiguration.class})
@EnableMetrics
//@EnableConfigurationProperties({JHipsterProperties.class})
//@EnableConfigurationProperties({JHipsterProperties.class, LiquibaseProperties.class})
@EnableCircuitBreaker
//@EnableEurekaClient
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Inject
    private Environment env;

    /**
     * Initializes foo.
     * <p>
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     * <p>
     * You can find more information on how profiles work with JHipster on <a href="http://jhipster.github.io/profiles/">http://jhipster.github.io/profiles/</a>.
     */
    @PostConstruct
    public void initApplication() {
        if (env.getActiveProfiles().length == 0) {
            LOG.warn("No Spring profile configured, running with default configuration");
        } else {
            LOG.info("Running with Spring profile(s) : {}", Arrays.toString(env.getActiveProfiles()));
            Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
            if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT)
                    && activeProfiles.contains(Constants.SPRING_PROFILE_PRODUCTION)) {
                LOG.error("You have misconfigured your application! "
                        + "It should not run with both the 'dev' and 'prod' profiles at the same time.");
            }
            if (activeProfiles.contains(Constants.SPRING_PROFILE_DEVELOPMENT)
                    && activeProfiles.contains(Constants.SPRING_PROFILE_CLOUD)) {
                LOG.error("You have misconfigured your application! "
                        + "It should not run with both the 'dev' and 'cloud' profiles at the same time.");
            }
        }
    }

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(Application.class);
        SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
        addDefaultProfile(app, source);
        Environment env = app.run(args).getEnvironment();
        LOG.info("\n----------------------------------------------------------\n\t"
                        + "Application '{}' is running! Access URLs:\n\t"
                        + "Local: \t\thttp://127.0.0.1:{}\n\t"
                        + "External: \thttp://{}:{}"
                        + "\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"));

        String configServerStatus = env.getProperty("configserver.status");
        LOG.info("\n----------------------------------------------------------\n\t"
                        + "Config Server: \t{}"
                        + "\n----------------------------------------------------------",
                configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);
    }

    /**
     * If no profile has been configured, set by default the "dev" profile.
     */
    private static void addDefaultProfile(SpringApplication app, SimpleCommandLinePropertySource source) {
        if (!source.containsProperty("spring.profiles.active")
                && !System.getenv().containsKey("SPRING_PROFILES_ACTIVE")) {

            app.setAdditionalProfiles(Constants.SPRING_PROFILE_LOCAL);
        }
    }
}
