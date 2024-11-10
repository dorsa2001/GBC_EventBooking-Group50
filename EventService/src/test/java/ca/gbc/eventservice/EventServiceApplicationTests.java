package ca.gbc.eventservice;



import io.restassured.RestAssured;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.containers.MongoDBContainer;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class EventServiceApplicationTests {



	@ServiceConnection

	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");



	@LocalServerPort

	private Integer port;



	@BeforeEach

	void setup() {

		RestAssured.baseURI = "http://localhost";

		RestAssured.port = port;

	}



	static {

		mongoDBContainer.start();

	}



	@Test

	void createEventTest() {

		String requestBody = """

            {

                "eventName": "Tech Conference 2024",

                "organizerId": "12345",

                "eventType": "Conference",

                "expectedAttendees": 200

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/events")

				.then()

				.log().all()

				.statusCode(201)

				.body("id", Matchers.notNullValue())

				.body("eventName", Matchers.equalTo("Tech Conference 2024"))

				.body("expectedAttendees", Matchers.equalTo(200));

	}



	@Test

	void getAllEventsTest() {



		String requestBody = """

            {

                "eventName": "Tech Conference 2024",

                "organizerId": "12345",

                "eventType": "Conference",

                "expectedAttendees": 200

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/events")

				.then()

				.statusCode(201);





		RestAssured.given()

				.contentType("application/json")

				.when()

				.get("/api/events")

				.then()

				.log().all()

				.statusCode(200)

				.body("size()", Matchers.greaterThan(0))

				.body("[0].eventName", Matchers.equalTo("Tech Conference 2024"));

	}

}