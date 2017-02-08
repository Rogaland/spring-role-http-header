package no.rogfk.role.integration.testutils;

import no.rogfk.role.annotations.HttpHeaderRole;
import no.rogfk.role.exceptions.MissingHeaderException;
import no.rogfk.role.exceptions.RoleForbiddenException;
import no.rogfk.role.model.ResponseHttpHeaderRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", method = RequestMethod.GET)
public class RoleTestController {
    @HttpHeaderRole(role = "FINT_ADMIN_PORTAL_USER")
    @RequestMapping(value = "/role1")
    public ResponseEntity test1() {
        return ResponseEntity.ok().build();
    }

    @HttpHeaderRole(role = "FINT_ADMIN_PORTAL_USER", roleHeaderName = "x-test-role")
    @RequestMapping(value = "/role2")
    public ResponseEntity test2() {
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(RoleForbiddenException.class)
    public ResponseEntity handleForbiddenAccess(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseHttpHeaderRole(e.getMessage()));
    }

    @ExceptionHandler(MissingHeaderException.class)
    public ResponseEntity handleMissingHeader(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseHttpHeaderRole(e.getMessage()));
    }

}
