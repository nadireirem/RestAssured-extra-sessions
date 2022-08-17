package com.cydeo.tests.officehours.day3;

import com.cydeo.tests.officehours.pojo.ZipInfo;
import com.cydeo.utils.ZipCodeTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

public class APITask3 extends ZipCodeTestBase {

    @Test
    public void task1() {
        //Given Accept application/json
        //And path zipcode is 22031
        //When I send a GET request to /us endpoint
        //Then status code must be 200
        //And content type must be application/json
        //And Server header is cloudflare
        //And Report-To header exists
        //And body should contains following information
        //    post code is 22031
        //    country  is United States
        //    country abbreviation is US
        //    place name is Fairfax
        //    state is Virginia
        //    latitude is 38.8604


        Response response = given().accept(ContentType.JSON)
                .pathParam("postal-code", 22031).
                when().get("/us/{postal-code}").prettyPeek();

        //Then status code must be 200
        assertEquals(200,response.statusCode());
        //And content type must be application/json
        assertEquals(ContentType.JSON.toString(),response.contentType());
        //And Server header is cloudflare
        assertEquals("cloudflare",response.getHeader("Server"));
        //And Report-To header exists
        assertFalse((response.getHeader("Report-To").isEmpty()));
        assertTrue(response.getHeaders().hasHeaderWithName("Report-To"));

        System.out.println("==== RESPONSE AS ===");
        //OPTION 1 --> response as
        ZipInfo zipInfo = response.as(ZipInfo.class);
        System.out.println(zipInfo);


        //And body should contains following information
        //    post code is 22031
        assertEquals("22031",zipInfo.getPostCode());
        //    country  is United States
        assertEquals("United States",zipInfo.getCountry());
        //    country abbreviation is US
        assertEquals("US",zipInfo.getCountryAbbreviation());

        //    place name is Fairfax
        assertEquals("Fairfax",zipInfo.getPlaces().get(0).getPlaceName());

        //    state is Virginia
        assertEquals("Virginia",zipInfo.getPlaces().get(0).getState());

        //    latitude is 38.8604
        assertEquals("38.8604",zipInfo.getPlaces().get(0).getLatitude());


        System.out.println("==== JSONPATH GETOBJECT ===");
        //OPTION 2 --> jsonpath

        ZipInfo zipInfoJsonPath = response.jsonPath().getObject("", ZipInfo.class);
        System.out.println(zipInfoJsonPath);
        //And body should contains following information
        //    post code is 22031
        assertEquals("22031",zipInfoJsonPath.getPostCode());
        //    country  is United States
        assertEquals("United States",zipInfoJsonPath.getCountry());
        //    country abbreviation is US
        assertEquals("US",zipInfoJsonPath.getCountryAbbreviation());

        //    place name is Fairfax
        assertEquals("Fairfax",zipInfoJsonPath.getPlaces().get(0).getPlaceName());

        //    state is Virginia
        assertEquals("Virginia",zipInfoJsonPath.getPlaces().get(0).getState());

        //    latitude is 38.8604
        assertEquals("38.8604",zipInfoJsonPath.getPlaces().get(0).getLatitude());

    }
}
