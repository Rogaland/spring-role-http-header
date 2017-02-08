package no.rogfk.role.annotations;

import no.rogfk.role.HttpHeaderRoleConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(HttpHeaderRoleConfig.class)
public @interface EnableHttpHeaderRole {
}
