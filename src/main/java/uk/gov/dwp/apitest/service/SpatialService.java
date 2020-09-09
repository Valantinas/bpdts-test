package uk.gov.dwp.apitest.service;

import org.springframework.stereotype.Service;
import uk.gov.dwp.apitest.model.User;

import static org.apache.lucene.util.SloppyMath.haversinMeters;

@Service
public class SpatialService {

    private static final double METERS_IN_ONE_MILE = 1609.34;
    private static final double MAX_DISTANCE_IN_MILES = 60;

    private static final double LONDON_CENTRE_LATITUDE = 51.5074;
    private static final double LONDON_CENTRE_LONGITUDE = 0.1277;

    private static final double LONDON_OVER_60_MILES_NORTH_LATITUDE = 52.5074;
    private static final double LONDON_OVER_60_MILES_SOUTH_LATITUDE = 50.5074;
    private static final double LONDON_OVER_60_MILES_EAST_LATITUDE = 1.6278;
    private static final double LONDON_OVER_60_MILES_WEST_LATITUDE = -1.4278;

    public boolean distanceWithin60milesFromLondon(User user) {

        if (roughlyOutsideLondon(user.getLatitude(), user.getLongitude())) {
            return false;
        }

        double distanceInMeters = haversinMeters(user.getLatitude(), user.getLongitude(), LONDON_CENTRE_LATITUDE, LONDON_CENTRE_LONGITUDE);
        double distanceInMiles = distanceInMeters / METERS_IN_ONE_MILE;

        return distanceInMiles < MAX_DISTANCE_IN_MILES;
    }

    private boolean roughlyOutsideLondon(double latitude, double longitude) {
        return latitude > LONDON_OVER_60_MILES_NORTH_LATITUDE
                || latitude < LONDON_OVER_60_MILES_SOUTH_LATITUDE
                || longitude > LONDON_OVER_60_MILES_EAST_LATITUDE
                || longitude < LONDON_OVER_60_MILES_WEST_LATITUDE;


    }
}
