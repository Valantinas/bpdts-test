package uk.gov.dwp.apitest.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import uk.gov.dwp.apitest.model.User;

import java.util.List;

@FeignClient(name = "users", url = "${userApi.host}")
public interface RestClient {

        @GetMapping(value = "/users", consumes = "application/json")
        List<User> getAllUsers();

        @GetMapping(value = "/city/London/users", consumes = "application/json")
        List<User> getLondonUsers();
}