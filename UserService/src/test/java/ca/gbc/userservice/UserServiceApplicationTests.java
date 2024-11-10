package ca.gbc.userservice;



import io.restassured.RestAssured;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.containers.PostgreSQLContainer;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class UserServiceApplicationTests {



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

	void createUserTest() {

		String requestBody = """

            {

                "name": "John Doe",

                "email": john.doe@example.com,

                "role": "STUDENT",

                "userType": "Undergraduate"

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/users")

				.then()

				.log().all()

				.statusCode(201)

				.body("id", Matchers.notNullValue())

				.body("name", Matchers.equalTo("John Doe"))

				.body("email", Matchers.equalTo("john.doe@example.com"))

                .body("role", Matchers.equalTo("STUDENT"))

				.body("userType", Matchers.equalTo("Undergraduate"));

	}



	@Test

	void getAllUsersTest() {

		// First, create a user to ensure data exists

		String requestBody = """

            {

                "name": "John Doe",

                "email": john.doe@example.com,

                "role": "STUDENT",

                "userType": "Undergraduate"

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/users")

				.then()

				.statusCode(201);



		// Fetch all users and verify

		RestAssured.given()

				.contentType("application/json")

				.when()

				.get("/api/users")

				.then()

				.log().all()

				.statusCode(200)

				.body("size()", Matchers.greaterThan(0))

				.body("[0].name", Matchers.equalTo("John Doe"));

	}

}