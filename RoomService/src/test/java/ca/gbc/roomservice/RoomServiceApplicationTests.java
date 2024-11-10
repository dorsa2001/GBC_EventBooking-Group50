package ca.gbc.roomservice;



import io.restassured.RestAssured;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.containers.PostgreSQLContainer;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class RoomServiceApplicationTests {



	@ServiceConnection

	static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");



	@LocalServerPort

	private Integer port;



	@BeforeEach

	void setup() {

		RestAssured.baseURI = "http://localhost";

		RestAssured.port = port;

	}



	static {

		postgresContainer.start();

	}



	@Test

	void createRoomTest() {

		String requestBody = """

            {

                "name": "Conference Room A",

                "capacity": 10,

                "features": "Projector, Whiteboard",

                "available": true

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/rooms")

				.then()

				.log().all()

				.statusCode(201)

				.body("id", Matchers.notNullValue())

				.body("name", Matchers.equalTo("Conference Room A"))

				.body("capacity", Matchers.equalTo(10))

				.body("features", Matchers.equalTo("Projector, Whiteboard"))

				.body("available", Matchers.equalTo(true));

	}



	@Test

	void getAllRoomsTest() {

		// First, create a room to ensure data exists

		String requestBody = """

            {

                "name": "Conference Room A",

                "capacity": 10,

                "features": "Projector, Whiteboard",

                "available": true

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/rooms")

				.then()

				.statusCode(201);





		RestAssured.given()

				.contentType("application/json")

				.when()

				.get("/api/rooms")

				.then()

				.log().all()

				.statusCode(200)

				.body("size()", Matchers.greaterThan(0))

				.body("[0].name", Matchers.equalTo("Conference Room A"));

	}

}