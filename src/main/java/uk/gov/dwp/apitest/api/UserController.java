package uk.gov.dwp.apitest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dwp.apitest.client.RestClient;
import uk.gov.dwp.apitest.model.User;
import uk.gov.dwp.apitest.service.SpatialService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping(value = "/city/London/users")
public class UserController {

    private final RestClient restClient;
    private final SpatialService spatialService;

    public UserController(RestClient restClient, SpatialService spatialService) {
        this.restClient = restClient;
        this.spatialService = spatialService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getLondoners() {

        List<User> users = restClient.get();

        List<User> londoners = users.stream()
                .filter(spatialService::distanceWithin60milesFromLondon)
                .collect(toList());

        return ResponseEntity.ok(londoners);
    }
}
