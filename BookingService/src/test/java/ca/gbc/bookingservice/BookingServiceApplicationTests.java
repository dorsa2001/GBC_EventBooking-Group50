package ca.gbc.bookingservice;



import io.restassured.RestAssured;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.containers.MongoDBContainer;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class BookingServiceApplicationTests {



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

	void createBookingTest() {

		String requestBody = """

            {

                "userId": "12345",

                "roomId": "67890",

                "startTime": "2024-12-01T09:00:00",

                "endTime": "2024-12-01T11:00:00",

                "purpose": "Team Meeting"

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/bookings")

				.then()

				.log().all()

				.statusCode(201)

				.body("id", Matchers.notNullValue())

				.body("purpose", Matchers.equalTo("Team Meeting"));

	}



	@Test

	void getAllBookingsTest() {


		String requestBody = """

            {

                "userId": "12345",

                "roomId": "67890",

                "startTime": "2024-12-01T09:00:00",

                "endTime": "2024-12-01T11:00:00",

                "purpose": "Team Meeting"

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/bookings")

				.then()

				.statusCode(201);




		RestAssured.given()

				.contentType("application/json")

				.when()

				.get("/api/bookings")

				.then()

				.log().all()

				.statusCode(200)

				.body("size()", Matchers.greaterThan(0))

				.body("[0].purpose", Matchers.equalTo("Team Meeting"));

	}

}