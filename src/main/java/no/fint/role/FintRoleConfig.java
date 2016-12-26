package no.fint.role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FintRoleConfig {
    @Bean
    public FintRoleAspect getFintRoleAspect() {
        return new FintRoleAspect();
    }
}
