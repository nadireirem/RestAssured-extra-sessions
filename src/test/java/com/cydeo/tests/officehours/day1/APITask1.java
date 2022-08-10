package com.cydeo.tests.officehours.day1;

import com.cydeo.utils.TypiCodeTestBase;
import io.restassured.http.ContentType;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
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
        assertTrue(response.getHeader("NEL").contains("success_fraction"));

    }

    @Test
    public void task3() {

        //- Given accept type is Json
        //- Path param "id" value is 12345
        //- When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}
        //- Then status code is 404
        //
        //- And json body contains "{}"

        Response response = given().accept(ContentType.JSON)
                .pathParam("id", 12345).
                when().get("/posts/{id}");


        assertEquals(404,response.getStatusCode());

        assertEquals("{}",response.asString());


    }


    @Test
    public void task4() {

        //- Given accept type is Json
        //- Path param "id" value is 2
        //- When user sends request to  https://jsonplaceholder.typicode.com/posts/{id}/comments
        //- Then status code is 200
        //
        //- And header Content - Type is Json
        //- And json body contains "Presley.Mueller@myrl.com",  "Dallas@ole.me" , "Mallory_Kunze@marie.org"

        Response response =
                given().accept(ContentType.JSON)
                    .pathParam("id", 2).
                when().get("/posts/{id}/comments");

        assertEquals(200,response.getStatusCode());

        // Contains
        assertTrue(response.asString().contains("Presley.Mueller@myrl.com"));
        assertTrue(response.asString().contains("Dallas@ole.me"));
        assertTrue(response.asString().contains("Mallory_Kunze@marie.org"));

        // Response.path
        // Presley
        String email = response.path("[0].email");
        System.out.println(email);

        //Dallas
        email = response.path("[1].email");
        System.out.println(email);

        //Mallory
        email = response.path("[2].email");
        System.out.println(email);


        //JsonPath
        JsonPath jsonPath = response.jsonPath();
        System.out.println(jsonPath.getString("[0].email"));
        System.out.println(jsonPath.getString("email[0]"));

        System.out.println(jsonPath.getString("email[1]"));

        System.out.println(jsonPath.getString("email[2]"));



    }
}
