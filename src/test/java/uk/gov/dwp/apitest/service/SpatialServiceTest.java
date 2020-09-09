package uk.gov.dwp.apitest.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.dwp.apitest.model.User;

import static org.junit.jupiter.api.Assertions.*;

class SpatialServiceTest {

    private SpatialService spatialService = new SpatialService();

    @ParameterizedTest
    @CsvSource({
            "32.6797904, -5.5781378, false",
            "23.945891, 104.412274, false",
            "51.451841, -0.054862, true",
            "51.545122, -0.055078, true"
    })
    void shouldIdentifyIfUserWithin60MilesFromLondon(double latitude, double longitude, boolean expected) {
        // GIVEN
        User user = new User();
        user.setLatitude(latitude);
        user.setLongitude(longitude);

        // WHEN
        boolean distanceIsWithin60milesFromLondon = spatialService.distanceWithin60milesFromLondon(user);

        // THEN
        assertEquals(expected, distanceIsWithin60milesFromLondon);
    }

}