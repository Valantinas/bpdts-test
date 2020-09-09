package uk.gov.dwp.apitest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/city/London/users")
public class UserController {

    @GetMapping
    public ResponseEntity<String> get() {

        return ResponseEntity.ok("todo");
    }
}
