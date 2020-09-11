package uk.gov.dwp.apitest.service;

import org.junit.jupiter.api.Test;
import uk.gov.dwp.apitest.model.User;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceTest {

    private UserService underTest = new UserService();

    @Test
    void shouldIdentifyUsersVisitingLondon() {
        // GIVEN
        User userInLondon = User.builder().latitude(51.451841).longitude(-0.054862).build();
        User userAwayFromLondon = User.builder().latitude(23.945891).longitude(104.412274).build();
        List<User> userList = asList(userInLondon, userAwayFromLondon);

        // WHEN
        List<User> londonVisitors = underTest.getUsersVisitingLondon(userList);

        // THEN
        assertEquals(1, londonVisitors.size());
        assertEquals(userInLondon, londonVisitors.get(0));
    }

    @Test
    void shouldMergeTwoUserListsCorrectly() {
        // GIVEN
        User user1 = User.builder().id(1).latitude(51.451841).longitude(-0.054862).build();
        User user2 = User.builder().id(2).latitude(23.945891).longitude(104.412274).build();
        User user3 = User.builder().id(3).latitude(32.6797904).longitude(-5.5781378).build();

        // WHEN
        List<User> mergedList = underTest.mergeListsWithDistinctUsers(asList(user1, user2), singletonList(user3));

        // THEN
        assertEquals(3, mergedList.size());
        assertEquals(user1, mergedList.get(0));
        assertEquals(user2, mergedList.get(1));
        assertEquals(user3, mergedList.get(2));
    }


    @Test
    void shouldMergeTwoListsCorrectlyOmittingDuplicateUsers() {
        // GIVEN
        User user1 = User.builder().id(1).latitude(51.451841).longitude(-0.054862).build();
        User user2 = User.builder().id(2).latitude(23.945891).longitude(104.412274).build();
        User user3 = User.builder().id(3).latitude(32.6797904).longitude(-5.5781378).build();

        // WHEN
        List<User> mergedList = underTest.mergeListsWithDistinctUsers(asList(user1, user2), asList(user2, user3));

        // THEN
        assertEquals(3, mergedList.size());
        assertEquals(user1, mergedList.get(0));
        assertEquals(user2, mergedList.get(1));
        assertEquals(user3, mergedList.get(2));
    }


}