package com.cydeo.tests.officehours.spartanAuth;

import com.cydeo.utils.SpartanSecureTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class SpartanAuthTest  extends SpartanSecureTestBase {

    @Test
    public void testPublicUser() {
        given().accept(ContentType.JSON)
                .get("/spartans").prettyPeek().
                then().log().ifValidationFails()
                .statusCode(401);
    }

    @Test
    public void testAuthenticatedUser() {
        given().accept(ContentType.JSON)
                .auth().basic("user","user")
                .get("/spartans").prettyPeek()
                .then().log().ifValidationFails()
                .statusCode(200);
    }
}
