package nl.spacedebris.monolith.web;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.DisabledOnNativeImage;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import nl.spacedebris.monolith.model.enums.CartStatus;
import nl.spacedebris.monolith.utils.TestContainerResource;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static javax.ws.rs.core.Response.Status.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;


@Slf4j
@DisabledOnNativeImage
@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
//@QuarkusTestResource(KeycloakRealmResource.class)
class CartResourceTest {

    private static final String INSERT_WRONG_CART_IN_DB =
            "insert into carts values (9999, current_timestamp, current_timestamp, 'NEW', 3)";

    private static final String DELETE_WRONG_CART_IN_DB =
            "delete from carts where id = 9999";

//    static String ADMIN_BEARER_TOKEN;
//    static String USER_BEARER_TOKEN;

    @Inject
    DataSource dataSource;

//    @BeforeAll
//    static void init() {
//        ADMIN_BEARER_TOKEN = System.getProperty("quarkus-admin-access-token");
//        USER_BEARER_TOKEN = System.getProperty("quarkus-test-access-token");
//    }
//
//    @Test
//    void testFindAllWithAdminRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                .get("/carts")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body("size()", greaterThan(0));
//    }
//
//    @Test
//    void testFindAllActiveCartsWithAdminRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                .get("/carts/active")
//                .then()
//                .statusCode(OK.getStatusCode());
//    }
//
//    @Test
//    void testGetActiveCartForCustomerWithAdminRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                .get("/carts/customer/1")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body(containsString("Jason"));
//    }
//
//    @Test
//    void testGetActiveCartForCustomerWhenThereAreTwoCartsInDBWithAdminRole() {
//        executeSql(INSERT_WRONG_CART_IN_DB);
//
//        try {
//            given().when()
//                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                    .get("/carts/customer/3")
//                    .then()
//                    .statusCode(INTERNAL_SERVER_ERROR.getStatusCode())
//                    .body(containsString(INTERNAL_SERVER_ERROR.getReasonPhrase()))
//                    .body(containsString("Many active carts detected !!!"));
//        } catch (AssertionError e) {
//
//        } finally {
//            executeSql(DELETE_WRONG_CART_IN_DB);
//        }
//    }
//
//    @Test
//    void testFindByIdWithAdminRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                .get("/carts/2")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body(containsString("status"))
//                .body(containsString("NEW"));
//
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                .get("/carts/100")
//                .then()
//                .statusCode(NO_CONTENT.getStatusCode())
//                .body(emptyOrNullString());
//    }
//
//    @Test
//    void testCreateCartWithAdminRole() {
//        var requestParams = new HashMap<>();
//        requestParams.put("firstName", "Saul");
//        requestParams.put("lastName", "Berenson");
//        requestParams.put("email", "call.saul@mail.com");
//
//        var newCustomerId = given()
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                .body(requestParams)
//                .post("/customers")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .extract()
//                .jsonPath()
//                .getInt("id");
//
//        var response = given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                .post("/carts/customer/" + newCustomerId)
//                .then()
//                .statusCode(OK.getStatusCode())
//                .extract()
//                .jsonPath()
//                .getMap("$");
//
//        assertThat(response.get("id")).isNotNull();
//        assertThat(response).containsEntry("status", CartStatus.NEW.name());
//
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                .delete("/carts/" + response.get("id"))
//                .then()
//                .statusCode(NO_CONTENT.getStatusCode());
//
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + ADMIN_BEARER_TOKEN)
//                .delete("/customers/" + newCustomerId)
//                .then()
//                .statusCode(NO_CONTENT.getStatusCode());
//    }
//
//    @Test
//    void testFindAllWithUserRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/carts")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body("size()", greaterThan(0));
//    }
//
//    @Test
//    void testFindAllActiveCartsWithUserRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/carts/active")
//                .then()
//                .statusCode(OK.getStatusCode());
//    }
//
//    @Test
//    void testGetActiveCartForCustomerWithUserRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/carts/customer/3")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body(containsString("Peter"));
//    }
//

//
//    @Test
//    void testFindByIdWithUserRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/carts/2")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body(containsString("status"))
//                .body(containsString("NEW"));
//
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/carts/100")
//                .then()
//                .statusCode(NO_CONTENT.getStatusCode())
//                .body(emptyOrNullString());
//    }
//

//

//
//    @Test
//    void testDeleteWithUserRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/carts/active")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body(containsString("Peter"))
//                .body(containsString("NEW"));
//
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .delete("/carts/3")
//                .then()
//                .statusCode(NO_CONTENT.getStatusCode());
//
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/carts/3")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body(containsString("Peter"))
//                .body(containsString("CANCELED"));
//    }

    @Test
    void testFindAll() {
        get("/carts").then()
                .statusCode(OK.getStatusCode())
                .body("size()", greaterThan(0));
    }

    @Test
    void testFindAllActiveCarts() {
        get("/carts/active").then()
                .statusCode(OK.getStatusCode());
    }

    @Test
    void testGetActiveCartForCustomer() {
        get("/carts/customer/3").then()
                .contentType(ContentType.JSON)
                .statusCode(OK.getStatusCode())
                .body(containsString("Peter"));
    }

    @Test
    void testFindById() {
        get("/carts/3").then()
                .statusCode(OK.getStatusCode())
                .body(containsString("status"))
                .body(containsString("NEW"));

        get("/carts/100").then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    void testDelete() {
        get("/carts/active").then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Jason"))
                .body(containsString("NEW"));

        delete("/carts/1").then()
                .statusCode(NO_CONTENT.getStatusCode());

        get("/carts/1").then()
                .statusCode(OK.getStatusCode())
                .body(containsString("Jason"))
                .body(containsString("CANCELED"));
    }
    @Test
    void testGetActiveCartForCustomerWhenThereAreTwoCartsInDB() {
        executeSql(INSERT_WRONG_CART_IN_DB);

        try {
            get("/carts/customer/3")
                    .then()
                    .statusCode(INTERNAL_SERVER_ERROR.getStatusCode())
//                    .body(containsString(INTERNAL_SERVER_ERROR.getReasonPhrase()))
                    .body(containsString("Many active carts detected !!!"));
//                    .body(containsString("this is not there, we fail???"));
        } catch (IllegalStateException e) {
            log.info("Request to get all Orders", e);
        } finally {
            executeSql(DELETE_WRONG_CART_IN_DB);
        }
    }

    @Test
    void testCreateCart() {
        var requestParams = Map.of(
                "firstName", "Saul",
                "lastName", "Berenson",
                "email", "call.saul@mail.com");

        var newCustomerId = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(requestParams).post("/customers").then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath()
                .getInt("id");

        var response = post("/carts/customer/" + newCustomerId)
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath()
                .getMap("$");

        assertThat(response.get("id")).isNotNull();
        assertThat(response).containsEntry("status", CartStatus.NEW.name());

        delete("/carts/" + response.get("id"))
                .then()
                .statusCode(NO_CONTENT.getStatusCode());

        delete("/customers/" + newCustomerId)
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

    @Test
    void testFailCreateCartWhileHavingAlreadyActiveCart() {
        var requestParams = Map.of(
                "firstName", "Saul",
                "lastName", "Berenson",
                "email", "call.saul@mail.com");

        var newCustomerId = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(requestParams)
                .post("/customers")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath()
                .getLong("id");

        var newCartId = post("/carts/customer/" + newCustomerId)
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath()
                .getLong("id");

        post("/carts/customer/" + newCustomerId)
                .then()
                .statusCode(INTERNAL_SERVER_ERROR.getStatusCode())
                .body(containsString("There is already an active cart"));

        assertThat(newCartId).isNotZero();

        delete("/carts/" + newCartId)
                .then()
                .statusCode(NO_CONTENT.getStatusCode());

        delete("/customers/" + newCustomerId)
                .then()
                .statusCode(NO_CONTENT.getStatusCode());
    }

//    @Test
//    void testCreateCart() {
//        post("/carts/customer/" + 555555).then()
//                .statusCode(UNAUTHORIZED.getStatusCode());
//    }
//


    private void executeSql(String insertWrongCartInDb) {
        try (var connection = dataSource.getConnection()) {
            var statement = connection.createStatement();
            statement.executeUpdate(insertWrongCartInDb);
        } catch (SQLException e) {
             System.out.println(e.getMessage());
        }
    }
}