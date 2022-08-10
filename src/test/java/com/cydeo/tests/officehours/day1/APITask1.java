package com.cydeo.tests.officehours.day1;

import com.cydeo.utils.TypiCodeTestBase;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.Assertion;
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


    @Test
    public void task2() {
        //- Given accept type is Json
        //- Path param "id" value is 1
        //- When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}

        Response response = given().accept(ContentType.JSON).pathParam("id", 1)
                .when().get("posts/{id}");

        //- Then status code is 200
         assertEquals(200,response.getStatusCode());

        //-And json body contains "repellat provident"
        assertTrue(response.asString().contains("repellat provident"));
        //- And header Content - Type is Json
        assertEquals("application/json; charset=utf-8",response.getContentType());


        // How can we verify Date ?
        System.out.println(response.getHeader("Date"));

        assertTrue(response.getHeaders().hasHeaderWithName("Date"));

        //- And header "X-Powered-By" value is "Express"
        assertEquals("Express",response.getHeader("X-Powered-By"));

        //- And header "X-Ratelimit-Limit" value is 1000
        assertEquals("1000",response.getHeader("X-Ratelimit-Limit"));

        //- And header "Age" value is more than 100
        String age = response.getHeader("Age");
        assertTrue(Integer.parseInt(age)>=100);

        Integer ageWithValueOf = Integer.valueOf(response.getHeader("Age"));


        //- And header "NEL" value contains "success_fraction"
        assertEquals("success_fraction",response.getHeader("NEL"));




    }
}
