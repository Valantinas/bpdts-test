package uk.gov.dwp.apitest.rest;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UserApplication.class)
class IntegrationSpecification {

    @LocalServerPort
    private int port;

    private WireMockServer wireMockServer;

    @BeforeEach
    void setup() {

        int userApiPort = 8081;
        wireMockServer = new WireMockServer(userApiPort);
        wireMockServer.start();
        configureFor("localhost", userApiPort);

        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @AfterEach
    void cleanup() {
        wireMockServer.stop();
    }

}
