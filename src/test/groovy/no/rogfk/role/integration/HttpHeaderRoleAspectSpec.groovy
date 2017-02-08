package no.rogfk.role.integration

import no.rogfk.role.integration.testutils.TestApplication
import no.rogfk.role.model.ResponseHttpHeaderRole
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest(classes = TestApplication, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HttpHeaderRoleAspectSpec extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    private HttpHeaders headers

    void setup() {
        headers = new HttpHeaders()
    }

    def "Sending correct role header and role"() {
        given:
        headers.add('x-role', 'FINT_ADMIN_PORTAL_USER')

        when:
        def response = restTemplate.exchange('/role1', HttpMethod.GET, new HttpEntity<>(headers), String)

        then:
        response.statusCode == HttpStatus.OK
    }

    def "Sending custom role header and role"() {
        given:
        headers.add('x-test-role', 'FINT_ADMIN_PORTAL_USER')

        when:
        def response = restTemplate.exchange('/role2', HttpMethod.GET, new HttpEntity<>(headers), String)

        then:
        response.statusCode == HttpStatus.OK
    }

    def "Missing role header"() {
        when:
        def response = restTemplate.exchange('/role1', HttpMethod.GET, new HttpEntity<>(headers), ResponseHttpHeaderRole)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body.message != null
    }

    def "Sending correct role header and wrong role"() {
        given:
        def headers = new HttpHeaders()
        headers.add('x-role', 'FINT_ADMIN_PORTAL')

        when:
        def response = restTemplate.exchange('/role1', HttpMethod.GET, new HttpEntity<>(headers), ResponseHttpHeaderRole)

        then:
        response.statusCode == HttpStatus.FORBIDDEN
        response.body.message != null
    }
}
