package uk.gov.dwp.apitest.rest;

import groovy.json.JsonOutput;
import io.restassured.response.Response;
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
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
public class UserApplicationTest extends IntegrationSpecification {

    @Test
    void shouldReturnAnEmptyBodyIfThereAreNoUsersFromLondonOrWhoseCoordinatesWithin60Miles() {

        givenThat(get(urlPathEqualTo("/users"))
                .willReturn(okJson(JsonOutput.toJson(
                        Collections.emptyList()))));

        givenThat(get(urlPathEqualTo("/city/London/users"))
                .willReturn(okJson(JsonOutput.toJson(
                        Collections.emptyList()))));

        Response response = given()
                .get("/city/London/users");

        response.then()
                .statusCode(200)
                .assertThat()
                .body("", hasSize(0));
    }

    @Test
    void shouldReturnUsersFromLondonOrWhoseCoordinatesWithin60Miles() {

        stubFor(get(urlEqualTo("/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("all-users.json")));

        stubFor(get(urlEqualTo("/city/London/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("london-users.json")));


        Response response = given()
                .get("/city/London/users");

        response.then()
                .statusCode(200)
                .assertThat()
                .body("size()", is(9))
                .body("id", hasItems(688, 322, 658, 135, 520, 554, 266, 794, 396));
    }

}
