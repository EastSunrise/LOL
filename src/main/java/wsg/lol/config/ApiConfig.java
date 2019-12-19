package wsg.lol.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import wsg.lol.common.enums.system.RegionEnum;

import java.util.List;

/**
 * Configuration for api.
 *
 * @author Kingen
 */
@Getter
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfig {

    @Setter
    private RegionEnum region;

    @Setter
    private List<Token> tokens;

    private double rate = 5D / 6D;

    @Setter
    private Token single;

    @Setter
    @Getter
    public static class Token {
        private String key;
        private String username;
    }
}
