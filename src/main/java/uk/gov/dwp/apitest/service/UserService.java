package uk.gov.dwp.apitest.service;

import org.springframework.stereotype.Service;
import uk.gov.dwp.apitest.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.lucene.util.SloppyMath.haversinMeters;

@Service
public class UserService {

    private static final double METERS_IN_ONE_MILE = 1609.34;
    private static final double MAX_DISTANCE_IN_MILES = 60;

    private static final double LONDON_CENTRE_LATITUDE = 51.5074;
    private static final double LONDON_CENTRE_LONGITUDE = 0.1277;


    public List<User> findUsersVisitingLondon(List<User> users) {

        return users.stream()
                .filter(this::distanceWithin60milesFromLondon)
                .collect(toList());
    }


    public List<User> mergeListsOmittingDuplicateUsers(List<User> users1, List<User> users2) {
        Collection<User> values = Stream
                .of(users1, users2)
                .flatMap(List::stream)
                .collect(toMap(User::getId, u -> u, (User user1, User user2) -> user1))
                .values();

        return new ArrayList<>(values);
    }


    private boolean distanceWithin60milesFromLondon(User user) {
        double distanceInMeters = haversinMeters(user.getLatitude(), user.getLongitude(), LONDON_CENTRE_LATITUDE, LONDON_CENTRE_LONGITUDE);
        double distanceInMiles = distanceInMeters / METERS_IN_ONE_MILE;

        return distanceInMiles < MAX_DISTANCE_IN_MILES;
    }

}
