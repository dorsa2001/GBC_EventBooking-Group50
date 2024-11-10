package ca.gbc.approvalservice;



import io.restassured.RestAssured;

import org.hamcrest.Matchers;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;

import org.testcontainers.containers.PostgreSQLContainer;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class ApprovalServiceApplicationTests {



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

	void createApprovalTest() {

		String requestBody = """

            {

                "eventId": "56789",

                "approvedBy": "admin",

                "status": "APPROVED"

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/approvals")

				.then()

				.log().all()

				.statusCode(201)

				.body("id", Matchers.notNullValue())

				.body("status", Matchers.equalTo("APPROVED"));

	}



	@Test

	void getAllApprovalsTest() {



		String requestBody = """

            {

                "eventId": "56789",

                "approvedBy": "admin",

                "status": "APPROVED"

            }

        """;



		RestAssured.given()

				.contentType("application/json")

				.body(requestBody)

				.when()

				.post("/api/approvals")

				.then()

				.statusCode(201);





		RestAssured.given()

				.contentType("application/json")

				.when()

				.get("/api/approvals")

				.then()

				.log().all()

				.statusCode(200)

				.body("size()", Matchers.greaterThan(0))

				.body("[0].status", Matchers.equalTo("APPROVED"));

	}

}

