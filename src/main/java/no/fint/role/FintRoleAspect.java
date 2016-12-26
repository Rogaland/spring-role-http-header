package no.fint.role;

import no.fint.role.annotations.FintRole;
import no.fint.role.exceptions.ForbiddenException;
import no.fint.role.exceptions.MissingHeaderException;
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
public class FintRoleAspect {

    @Around("@annotation(no.fint.role.annotations.FintRole)")
    public Object executeEndpoint(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method requestMethod = signature.getMethod();

        FintRole fintRole = requestMethod.getAnnotation(FintRole.class);
        String accessRole = fintRole.role();
        String roleHeaderName = fintRole.roleHeaderName();

        Optional<String> roleInHeader = getRoleFromHeader(roleHeaderName);
        if (roleInHeader.isPresent()) {
            if (roleInHeader.get().equals(accessRole)) {
                return proceedingJoinPoint.proceed();
            } else {
                throw new ForbiddenException(String.format("The role '%s' has no access to this service.", roleInHeader.get()));
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
