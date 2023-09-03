package nl.spacedebris.monolith.web;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import nl.spacedebris.monolith.utils.TestContainerResource;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@QuarkusTestResource(TestContainerResource.class)
//@QuarkusTestResource(KeycloakRealmResource.class)
class ProductResourceTest {
//
//    static String ADMIN_BEARER_TOKEN;
//    static String USER_BEARER_TOKEN;
//
//    @BeforeAll
//    static void init() {
//        ADMIN_BEARER_TOKEN = System.getProperty("quarkus-admin-access-token");
//        USER_BEARER_TOKEN = System.getProperty("quarkus-test-access-token");
//    }
//
//    @Test
//    void testFindAll() {
//        given().when()
//                .get("/products")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body("size()", greaterThan(0))
//                .body(containsString("name"))
//                .body(containsString("description"))
//                .body(containsString("price"))
//                .body(containsString("categoryId"));
//    }
//
//    @Test
//    void testFindById() {
//        given().when()
//                .get("/products/3")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body(containsString("MacBook Pro 13"))
//                .body(containsString("1999.00"))
//                .body(containsString("categoryId"))
//                .body(containsString("AVAILABLE"));
//    }
//
//    @Test
//    void testCreate() {
//        var count = given().when()
//                .get("/products/count")
//                .then()
//                .extract()
//                .body()
//                .as(Long.class);
//
//        var requestParams = new HashMap<>();
//        requestParams.put("name", "Dell G5");
//        requestParams.put("description", "Best gaming laptop from Dell");
//        requestParams.put("price", 1490);
//        requestParams.put("status", "AVAILABLE");
//        requestParams.put("salesCounter", 0);
//        requestParams.put("categoryId", 2);
//
//        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//                .body(requestParams)
//                .post("/products")
//                .then()
//                .statusCode(UNAUTHORIZED.getStatusCode());
//    }
//
//    @Test
//    void testDelete() {
//        var requestParams = new HashMap<>();
//        requestParams.put("name", "Dell G5");
//        requestParams.put("description", "Best gaming laptop from Dell");
//        requestParams.put("price", 1490);
//        requestParams.put("status", "AVAILABLE");
//        requestParams.put("salesCounter", 0);
//        requestParams.put("categoryId", 2);
//
//        given()
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//                .body(requestParams)
//                .post("/products")
//                .then()
//                .statusCode(UNAUTHORIZED.getStatusCode());
//    }
//
//    @Test
//    void testFindByCategoryId() {
//        var ids = given().when()
//                .get("/products/category/1")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .extract()
//                .jsonPath()
//                .getList("id", Long.class);
//
//        assertThat(ids.size()).isEqualTo(3);
//    }
//
//    @Test
//    void testCountByCategoryId() {
//        var count = given().when()
//                .get("/products/count/category/1")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .extract()
//                .as(Integer.class);
//
//        assertThat(count).isGreaterThanOrEqualTo(2);
//    }
//
    @Test
    void testFindAll() {
        get("/products")
                .then()
                .statusCode(OK.getStatusCode())
                .body("size()", greaterThan(0))
                .body(containsString("name"))
                .body(containsString("description"))
                .body(containsString("price"))
                .body(containsString("categoryId"));
    }

    @Test
    void testFindById() {
        get("/products/3")
                .then()
                .statusCode(OK.getStatusCode())
                .body(containsString("MacBook Pro 13"))
                .body(containsString("1999.00"))
                .body(containsString("categoryId"))
                .body(containsString("AVAILABLE"));
    }

    @Test
    void testCreate() {
        var count = get("/products/count")
                .then()
                .extract()
                .body()
                .as(Long.class);

        var requestParams = new HashMap<>();
        requestParams.put("name", "Dell G5");
        requestParams.put("description", "Best gaming laptop from Dell");
        requestParams.put("price", 1490);
        requestParams.put("status", "AVAILABLE");
        requestParams.put("salesCounter", 0);
        requestParams.put("categoryId", 2);

        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(requestParams)
                .post("/products")
                .then()
                .statusCode(OK.getStatusCode())
                .body(containsString("id"))
                .body(containsString("Dell G5"));

        get("/products/count")
                .then()
                .body(containsString(String.valueOf(count + 1)));
    }

    @Test
    void testDelete() {
        var requestParams = new HashMap<>();
        requestParams.put("name", "Dell G5");
        requestParams.put("description", "Best gaming laptop from Dell");
        requestParams.put("price", 1490);
        requestParams.put("status", "AVAILABLE");
        requestParams.put("salesCounter", 0);
        requestParams.put("categoryId", 2);

        var response = given()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .body(requestParams)
                .post("/products")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath()
                .getMap("$");

        assertNotNull(response.get("id"));

        var newProductID = (Integer) response.get("id");

        delete("/products/" + newProductID)
                .then()
                .statusCode(NO_CONTENT.getStatusCode());

        get("/products/" + newProductID)
                .then()
                .statusCode(NO_CONTENT.getStatusCode())
                .body(is(emptyString()));
    }

    @Test
    void testFindByCategoryId() {
        var ids = get("/products/category/1")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .jsonPath()
                .getList("id", Long.class);

        assertThat(ids.size()).isEqualTo(3);
    }

    @Test
    void testCountByCategoryId() {
        var count = get("/products/count/category/1")
                .then()
                .statusCode(OK.getStatusCode())
                .extract()
                .as(Integer.class);

        assertThat(count).isGreaterThanOrEqualTo(2);
    }

//    @Test
//    void testFindAllWithUserRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/products")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body("size()", greaterThan(0))
//                .body(containsString("name"))
//                .body(containsString("description"))
//                .body(containsString("price"))
//                .body(containsString("categoryId"));
//    }
//
//    @Test
//    void testFindByIdWithUserRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/products/3")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .body(containsString("MacBook Pro 13"))
//                .body(containsString("1999.00"))
//                .body(containsString("categoryId"))
//                .body(containsString("AVAILABLE"));
//    }
//
//    @Test
//    void testCreateWithUserRole() {
//        var count = given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/products/count")
//                .then()
//                .extract()
//                .body()
//                .as(Long.class);
//
//        var requestParams = new HashMap<>();
//        requestParams.put("name", "Dell G5");
//        requestParams.put("description", "Best gaming laptop from Dell");
//        requestParams.put("price", 1490);
//        requestParams.put("status", "AVAILABLE");
//        requestParams.put("salesCounter", 0);
//        requestParams.put("categoryId", 2);
//
//        given().header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .body(requestParams)
//                .post("/products")
//                .then()
//                .statusCode(FORBIDDEN.getStatusCode());
//    }
//
//    @Test
//    void testDeleteWithUserRole() {
//        given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .delete("/products/1")
//                .then()
//                .statusCode(FORBIDDEN.getStatusCode());
//    }
//
//    @Test
//    void testFindByCategoryIdWithUserRole() {
//        var ids = given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/products/category/1")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .extract()
//                .jsonPath()
//                .getList("id", Long.class);
//
//        assertThat(ids.size()).isEqualTo(3);
//    }
//
//    @Test
//    void testCountByCategoryIdWithUserRole() {
//        var count = given().when()
//                .header(HttpHeaders.AUTHORIZATION, "Bearer " + USER_BEARER_TOKEN)
//                .get("/products/count/category/1")
//                .then()
//                .statusCode(OK.getStatusCode())
//                .extract()
//                .as(Integer.class);
//
//        assertThat(count).isGreaterThanOrEqualTo(2);
//    }
}