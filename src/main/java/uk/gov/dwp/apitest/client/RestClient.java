package uk.gov.dwp.apitest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import uk.gov.dwp.apitest.model.User;

import java.util.List;

@FeignClient(name = "users", url = "https://bpdts-test-app-v3.herokuapp.com")
public interface RestClient {

        @GetMapping(value = "/users", consumes = "application/json")
        List<User> get();
}
