package com.cydeo.tests.officehours.task4;

import com.cydeo.tests.officehours.pojo.POSTRegion;
import com.cydeo.utils.HrApiTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class APITasks4 extends HrApiTestBase {

    //1) POST a region then do validations. Please use Map or POJO class, or JsonPath
    ///**
    // * given accept is json
    // * and content type is json
    // * When I send post request to "/regions/"
    // * With json:
    // * {
    // *     "region_id":100,
    // *     "region_name":"Test Region"
    // * }
    // * Then status code is 201
    // * content type is json
    // * region_id is 100
    // * region_name is Test Region
    // */


    /**
     * given accept is json
     * When I send GET request to "/regions/100"
     * Then status code is 200
     * content type is json
     * region_id is 100
     * region_name is Test Region
     */

    @Test
    public void task1() {


        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("region_id",12223);
        requestBody.put("region_name","Test Region Officehours");
        //POST REGION
        int regionID = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody).log().body()
                .when().post("/regions/").prettyPeek()
                .then().statusCode(201)
                .contentType(ContentType.JSON)
                .body("region_id", is(12223))
                .body("region_name", is("Test Region Officehours"))
                .extract().response().jsonPath().getInt("region_id");

        //GET REGION
        given().accept(ContentType.JSON)
                .pathParam("region_id",regionID)
                .when().get("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("region_id", is(regionID))
                .body("region_name", is("Test Region Officehours"));

        // DELETE REGION
        given().accept(ContentType.JSON)
                .pathParam("region_id",regionID)
                .when().delete("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .body("rowsDeleted",is(1));

    }

    //2) PUT request then DELETE
    ///**
    // * Given accept type is Json
    // * And content type is json
    // * When i send PUT request to /regions/100
    // * With json body:
    // *    {
    // *      "region_id": 100,
    // *      "region_name": "Wooden Region"
    // *    }
    // * Then status code is 200
    // * And content type is json
    // * region_id is 100
    // * region_name is Wooden Region
    //*/
    //
    ///**
    // * Given accept type is Json
    // * When i send DELETE request to /regions/100
    // * Then status code is 200
    //*/
    @Test
    public void task2() {

        POSTRegion region = new POSTRegion(2198, "Intellij Region");

        //PUT Region
        int regionID = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("region_id", 2198)
                .body(region)
                .when().put("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("region_id", is(2198))
                .body("region_name", is("Intellij Region"))
                .extract().jsonPath().getInt("region_id");

        System.out.println("REGION ID FROM PUT ");
        System.out.println(regionID);

        //DELETE same REGION
        given().accept(ContentType.JSON)
                .pathParam("region_id",regionID)
                .when().delete("/regions/{region_id}")
                .then().statusCode(200)
                .body("rowsDeleted",is(1));


    }
}
