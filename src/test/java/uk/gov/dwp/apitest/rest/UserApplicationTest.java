package uk.gov.dwp.apitest.rest;

import groovy.json.JsonOutput;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
class UserApplicationTest extends IntegrationSpecification {

    private static final String CITY_LONDON_USERS = "/city/London/users";
    private static final String USERS = "/users";
    private static final String LONDON_USERS = "/London/users";

    @Test
    void shouldReturnAnEmptyBodyIfThereAreNoUsersFromLondonOrWhoseCoordinatesWithin60Miles() {

        givenThat(get(urlPathEqualTo(USERS))
                .willReturn(okJson(JsonOutput.toJson(
                        Collections.emptyList()))));

        givenThat(get(urlPathEqualTo(CITY_LONDON_USERS))
                .willReturn(okJson(JsonOutput.toJson(
                        Collections.emptyList()))));

        Response response = given()
                .get(LONDON_USERS);

        response.then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("", hasSize(0));
    }

    @Test
    void shouldReturnUsersFromLondonOrWhoseCoordinatesWithin60Miles() {

        stubFor(get(urlEqualTo(USERS))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("all-users.json")));

        stubFor(get(urlEqualTo(CITY_LONDON_USERS))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("london-users.json")));


        Response response = given()
                .get(LONDON_USERS);

        response.then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(9))
                .body("id", hasItems(688, 322, 658, 135, 520, 554, 266, 794, 396))

                .body("[0].id", is(688))
                .body("[0].email", is("tcolbertsonj3@vimeo.com"))
                .body("[0].latitude", equalTo(37.13f))
                .body("[0].longitude", equalTo(-84.08f))
                .body("[0].first_name", is("Tiffi"))
                .body("[0].last_name", is("Colbertson"))
                .body("[0].ip_address", is("141.49.93.0"));
    }


    @Test
    void shouldReturnA500ResponseIfCallToUsersAPIFails() {

        stubFor(get(urlEqualTo(USERS))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("error")));

        stubFor(get(urlEqualTo(CITY_LONDON_USERS))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("london-users.json")));

        Response response = given()
                .get(LONDON_USERS);

        response.then()
                .statusCode(500);
    }

    @Test
    void shouldReturnA500ResponseIfCallToCityLondonUsersAPIFails() {

        stubFor(get(urlEqualTo(USERS))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("all-users.json")));

        stubFor(get(urlEqualTo(CITY_LONDON_USERS))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withBody("error")));

        Response response = given()
                .get(LONDON_USERS);

        response.then()
                .statusCode(500);
    }

}
