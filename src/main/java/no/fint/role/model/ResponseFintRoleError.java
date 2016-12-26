package no.fint.role.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseFintRoleError {
    private String message;
}