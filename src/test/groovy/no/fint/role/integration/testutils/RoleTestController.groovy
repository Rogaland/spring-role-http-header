package no.fint.role.integration.testutils

import no.fint.role.annotations.FintRole
import no.fint.role.exceptions.ForbiddenException
import no.fint.role.exceptions.MissingHeaderException
import no.fint.role.model.ResponseFintRoleError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = "/role", method = RequestMethod.GET)
class RoleTestController {

    @FintRole(role = "FINT_ADMIN_PORTAL_USER")
    @RequestMapping
    ResponseEntity test() {
        return ResponseEntity.ok().build()
    }

    @ExceptionHandler(ForbiddenException.class)
    ResponseEntity handleForbiddenAccess(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseFintRoleError(e.getMessage()))
    }

    @ExceptionHandler(MissingHeaderException.class)
    ResponseEntity handleMissingHeader(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseFintRoleError(e.getMessage()))
    }
}
