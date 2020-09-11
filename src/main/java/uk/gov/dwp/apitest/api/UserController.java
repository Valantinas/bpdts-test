package uk.gov.dwp.apitest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dwp.apitest.client.RestClient;
import uk.gov.dwp.apitest.model.User;
import uk.gov.dwp.apitest.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/city/London/users")
public class UserController {

    private final RestClient restClient;
    private final UserService userService;

    public UserController(RestClient restClient, UserService spatialService) {
        this.restClient = restClient;
        this.userService = spatialService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getLondoners() {

        List<User> londoners = restClient.getLondonUsers();

        List<User> allUsers = restClient.getAllUsers();
        List<User> londonVisitors = userService.getUsersVisitingLondon(allUsers);

        List<User> londonersOrVisitors = userService.mergeListsWithDistinctUsers(londoners, londonVisitors);

        return ResponseEntity.ok(londonersOrVisitors);
    }
}
