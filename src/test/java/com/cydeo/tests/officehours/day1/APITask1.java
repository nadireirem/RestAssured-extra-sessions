package com.cydeo.tests.officehours.day1;

import com.cydeo.utils.TypiCodeTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class APITask1 extends TypiCodeTestBase {


    @Test
    public void task1() {

        //- Given accept type is Json
        //- When user sends request to https://jsonplaceholder.typicode.com/posts
        Response response = given().log().uri().accept(ContentType.JSON)
                .when().get("/posts");

        //-Then print response body
        // it prints response body
        response.prettyPrint();

        //- And status code is 200
        assertEquals(200,response.statusCode());
        assertEquals(200,response.getStatusCode());
        assertEquals(HttpStatus.SC_OK,response.statusCode());

        //- And Content - Type is Json
        assertEquals("application/json; charset=utf-8",response.getContentType());
        assertEquals("application/json; charset=utf-8",response.contentType());
        assertEquals("application/json; charset=utf-8",response.getHeader("Content-Type"));

    }
}
