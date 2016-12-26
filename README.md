# FINT Role HTTP Header

[![Build Status](https://jenkins.rogfk.no/buildStatus/icon?job=FINTprosjektet/fint-role-http-header/master)](https://jenkins.rogfk.no/job/FINTprosjektet/job/fint-role-http-header/job/master/)



## Installation

build.gradle

```
repositories {
    maven {
        url  "http://dl.bintray.com/fint/maven"
    }
}

compile('no.fint:fint-role-http-header:0.0.1')
```
## Documentation
If the role is present nothing will happen. If the header is missing a `MissingHeaderException` will be thrown.
If the value in the header is not equal to the role in `@FintRole` a `ForbiddenException` is thrown.

### Annotations
| Annotation        | Description           |
|-------------------|-----------------------|
| @EnableFintRole   | Enables FINT roles    |
| @FintRole         | Parameters:
* Name of the role
* Name of the header (default: 'x-role') |


### Exceptions
| Exceptions        | Description           |
|-------------------|-----------------------|
| MissingHeaderException | Is thrown if the header is missing. |
| ForbiddenException | Is thrown if the role in the header is not equal to the role in the @FintRole annotation. |


## Usage

- Set `@EnableFintRole` on your application class.
- Set `@FintRole("HEADER_VALUE", "HEADER_NAME")` for each endpoint methods in your controller.
- Implement exception handles for `ForbiddenException` and `MissingHeaderException`.

## Example

````java
@EnableFintRole
@SpringBootApplication
public class Application {
...
````

````java
@RestController
@RequestMapping(value = "/test")
public class Controller {

    @FintRole(role = "FINT_ADMIN_PORTAL_USER", "x-fint-role")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity test() {
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity handleForbiddenAccess(Exception e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseFintRoleError(e.getMessage()));
    }

    @ExceptionHandler(MissingHeaderException.class)
    public ResponseEntity handleMissingHeader(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseFintRoleError(e.getMessage()));
    }
}
```