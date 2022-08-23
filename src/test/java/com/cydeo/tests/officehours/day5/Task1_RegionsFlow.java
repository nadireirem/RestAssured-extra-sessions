package com.cydeo.tests.officehours.day5;

import com.cydeo.utils.HrApiTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Task1_RegionsFlow extends HrApiTestBase {


   static int regionID;

    @Test
   public  void POSTRequest() {


        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("region_id",1222345);
        requestBody.put("region_name","Test Region Officehours");
        //POST REGION
         regionID = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody).log().body()
                .when().post("/regions/").prettyPeek()
                .then().statusCode(201)
                .contentType(ContentType.JSON)
                .body("region_id", is(1222345))
                .body("region_name", is("Test Region Officehours"))
                .extract().response().jsonPath().getInt("region_id");


    }

    @Test
    public  void GETRequest() {

        //GET REGION
        given().accept(ContentType.JSON)
                .pathParam("region_id",regionID)
                .when().get("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("region_id", is(regionID))
                .body("region_name", is("Test Region Officehours"));



    }

    @Test
    public  void DELETERequest() {

        given().accept(ContentType.JSON)
                .pathParam("region_id",regionID)
                .when().delete("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .body("rowsDeleted",is(1));


    }
}
