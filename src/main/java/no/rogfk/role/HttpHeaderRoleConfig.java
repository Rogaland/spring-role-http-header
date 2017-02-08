package no.rogfk.role;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpHeaderRoleConfig {
    @Bean
    public HttpHeaderRoleAspect getHttpHeaderRoleAspect() {
        return new HttpHeaderRoleAspect();
    }
}
