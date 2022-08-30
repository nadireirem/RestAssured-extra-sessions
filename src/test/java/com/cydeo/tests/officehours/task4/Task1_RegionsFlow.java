package com.cydeo.tests.officehours.task4;

import com.cydeo.tests.officehours.pojo.POSTRegion;
import com.cydeo.utils.HrApiTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Task1_RegionsFlow extends HrApiTestBase {


   static int regionID;
    @Order(1)
    @Test
   public  void POSTRequest() {

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("region_id",7777);
        requestBody.put("region_name","Test Region Officehours");

        //POST REGION
         regionID = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody).log().body()
                .when().post("/regions/").prettyPeek()
                .then().statusCode(201)
                .contentType(ContentType.JSON)
                .body("region_id", is(7777))
                .body("region_name", is("Test Region Officehours"))
                .extract().response().jsonPath().getInt("region_id");

    }

    // GET Method to see it is generated

    @Order(2)
    @Test
    public void PUTRequest() {

        POSTRegion region = new POSTRegion(regionID, "Intellij Region");

        //PUT Region
        regionID = given().accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .pathParam("region_id", regionID)
                .body(region)
                .when().put("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("region_id", is(regionID))
                .body("region_name", is("Intellij Region"))
                .extract().jsonPath().getInt("region_id");

        System.out.println("REGION ID FROM PUT ");
        System.out.println(regionID);

    }



    @Order(3)
    @Test
    public  void GETRequest() {

        //GET REGION
        given().accept(ContentType.JSON)
                .pathParam("region_id",regionID)
                .when().get("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .body("region_id", is(regionID))
                .body("region_name", is("Intellij Region"));



    }
    @Order(4)
    @Test
    public  void DELETERequest() {

        given().accept(ContentType.JSON)
                .pathParam("region_id",regionID)
                .when().delete("/regions/{region_id}").prettyPeek()
                .then().statusCode(200)
                .body("rowsDeleted",is(1));


    }


    // GET Method to see it is deleted
    @Order(5)
    @Test
    public  void GETRequestAfterDELETE() {

        //GET REGION
        given().accept(ContentType.JSON)
                .pathParam("region_id",regionID)
                .when().get("/regions/{region_id}").prettyPeek()
                .then().statusCode(404)
                ;



    }


}
