package no.rogfk.role;

import no.rogfk.role.annotations.HttpHeaderRole;
import no.rogfk.role.exceptions.MissingHeaderException;
import no.rogfk.role.exceptions.RoleForbiddenException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Optional;

@Aspect
@Component
public class HttpHeaderRoleAspect {

    @Around("@annotation(no.rogfk.role.annotations.HttpHeaderRole)")
    public Object executeEndpoint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method requestMethod = signature.getMethod();

        HttpHeaderRole httpHeaderRole = requestMethod.getAnnotation(HttpHeaderRole.class);
        String accessRole = httpHeaderRole.role();
        String roleHeaderName = httpHeaderRole.roleHeaderName();

        Optional<String> roleInHeader = getRoleFromHeader(roleHeaderName);
        if (roleInHeader.isPresent()) {
            if (roleInHeader.get().equals(accessRole)) {
                return proceedingJoinPoint.proceed();
            } else {
                throw new RoleForbiddenException(String.format("The role '%s' has no access to this service.", roleInHeader.get()));
            }
        } else {
            throw new MissingHeaderException(String.format("Missing '%s' header.", roleHeaderName));
        }
    }

    private Optional<String> getRoleFromHeader(String roleHeaderName) {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return Optional.ofNullable(((ServletRequestAttributes) attributes).getRequest().getHeader(roleHeaderName));
    }
}
