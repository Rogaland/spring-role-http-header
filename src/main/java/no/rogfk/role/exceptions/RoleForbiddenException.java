package no.rogfk.role.exceptions;

public class RoleForbiddenException extends RuntimeException {
    public RoleForbiddenException(String message) {
        super(message);
    }
}
