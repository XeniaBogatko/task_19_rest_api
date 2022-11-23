import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class RestTests {
    @Test
    void getSingleUserTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200) // OK
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }
    @Test
    void notFoundUserTest() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users/278127")
                .then()
                .log().status()
                .log().body()
                .statusCode(404); // Not Found
    }
    @Test
    void createUserTest() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        given()
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201) // Created
                .body("name", is("morpheus"))
                .body("job", is("leader"));
    }
    @Test
    void createUserNegativeTest() {
        given()
                .log().uri()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(415); // Unsupported Media Type
    }
    @Test
    void deleteUserTest() {
        given()
                .log().uri()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204); // No Content
    }
}
