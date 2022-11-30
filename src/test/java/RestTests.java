import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.UserCreationModel;
import models.lombok.UserCretionResponseModel;
import models.pojo.CreateUserModel;
import models.pojo.CreateUserResponseModel;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static specs.CreateUserNegativeSpecs.createUserNegativeRequestSpec;
import static specs.CreateUserNegativeSpecs.createUserNegativeResponseSpec;
import static specs.CreateUserSpecs.createUserRequestSpec;
import static specs.CreateUserSpecs.createUserResponseSpec;
import static specs.DeleteUserSpecs.deleteUserRequestSpec;
import static specs.DeleteUserSpecs.deleteUserResponseSpec;
import static specs.GetUserSpecs.getUserRequestSpec;
import static specs.GetUserSpecs.getUserResponseSpec;
import static specs.NotFoundUserSpecs.notFoundUserRequestSpec;
import static specs.NotFoundUserSpecs.notFoundUserResponseSpec;

public class RestTests {
    @Test
    void getSingleUserTest() {
        given()
                .spec(getUserRequestSpec)
                .when()
                .get()
                .then()
                .spec(getUserResponseSpec)
                .body("data.first_name", is("Janet"))
                .body("data.last_name", is("Weaver"));
    }

    @Test
    void notFoundUserTest() {
        given()
                .spec(notFoundUserRequestSpec)
                .when()
                .get()
                .then()
                .spec(notFoundUserResponseSpec);
    }

    @Test
    void createUserPojoTest() {
        // String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";
        CreateUserModel userModel = new CreateUserModel();
        userModel.setJob("leader");
        userModel.setName("morpheus");

        CreateUserResponseModel responseModel = given()
                .log().all()
                .contentType(JSON)
                .body(userModel)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201) // Created
                .extract().as(CreateUserResponseModel.class);

        assertThat(responseModel.getJob()).isEqualTo("leader");
        assertThat(responseModel.getName()).isEqualTo("morpheus");
    }

    @Test
    void createUserLombokTest() {
        UserCreationModel userModel = new UserCreationModel();
        userModel.setJob("leader");
        userModel.setName("morpheus");

        UserCretionResponseModel responseModel = given()
                .log().all()
                .contentType(JSON)
                .body(userModel)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201) // Created
                .extract().as(UserCretionResponseModel.class);

        assertThat(responseModel.getJob()).isEqualTo("leader");
        assertThat(responseModel.getName()).isEqualTo("morpheus");
    }

    @Test
    void createUserWithAllureListenerTest() {
        UserCreationModel userModel = new UserCreationModel();
        userModel.setJob("leader");
        userModel.setName("morpheus");

        UserCretionResponseModel responseModel = given()
                .filter(new AllureRestAssured())
                .log().all()
                .contentType(JSON)
                .body(userModel)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201) // Created
                .extract().as(UserCretionResponseModel.class);

        assertThat(responseModel.getJob()).isEqualTo("leader");
        assertThat(responseModel.getName()).isEqualTo("morpheus");
    }

    @Test
    void createUserWithCustomAllureListenerTest() {
        UserCreationModel userModel = new UserCreationModel();
        userModel.setJob("leader");
        userModel.setName("morpheus");

        UserCretionResponseModel responseModel = given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(JSON)
                .body(userModel)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201) // Created
                .extract().as(UserCretionResponseModel.class);

        assertThat(responseModel.getJob()).isEqualTo("leader");
        assertThat(responseModel.getName()).isEqualTo("morpheus");
    }

    @Test
    void createUserWithSpecsTest() {
        UserCreationModel userModel = new UserCreationModel();
        userModel.setJob("leader");
        userModel.setName("morpheus");

        UserCretionResponseModel responseModel = given()
                //given(createUserRequestSpec)
                .spec(createUserRequestSpec)
                .body(userModel)
                .when()
                .post()
                .then()
                .spec(createUserResponseSpec)
                .extract().as(UserCretionResponseModel.class);

        assertThat(responseModel.getJob()).isEqualTo("leader");
        assertThat(responseModel.getName()).isEqualTo("morpheus");
    }

    @Test
    void createUserNegativeTest() {
        given()
                .spec(createUserNegativeRequestSpec)
                .when()
                .post()
                .then()
                .spec(createUserNegativeResponseSpec);
    }

    @Test
    void deleteUserTest() {
        given()
                .spec(deleteUserRequestSpec)
                .when()
                .delete()
                .then()
                .spec(deleteUserResponseSpec);
    }
}
