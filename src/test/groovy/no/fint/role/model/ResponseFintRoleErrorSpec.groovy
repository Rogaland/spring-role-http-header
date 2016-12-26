package no.fint.role.model

import spock.lang.Specification


class ResponseFintRoleErrorSpec extends Specification {

    def "Create new ResponseFintRoleError"() {
        when:
        ResponseFintRoleError responseFintRoleError = new ResponseFintRoleError("Error message")

        then:
        responseFintRoleError != null
        responseFintRoleError.getMessage() == "Error message"

    }
}
