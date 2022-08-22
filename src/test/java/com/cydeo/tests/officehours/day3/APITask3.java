package com.cydeo.tests.officehours.day3;

import com.cydeo.tests.officehours.pojo.StateCity;
import com.cydeo.tests.officehours.pojo.StateCityPlaces;
import com.cydeo.tests.officehours.pojo.ZipInfo;
import com.cydeo.utils.ZipCodeTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import lombok.Data;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /*
    Given Accept application/json
And path state is VA
And path city is fairfax
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And payload should contain following information
    country abbreviation is US
    country  United States
    place name  Fairfax
    each places must contains fairfax as a value
    each post code must start with 22
     */

    @Test
    public void task3jsonPath(){

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .and().pathParam("state", "VA")
                .and().pathParam("place name", "Fairfax")
                .when().get("/us/{state}/{place name}").prettyPeek()
                .then().statusCode(200)
                .contentType(ContentType.JSON).extract().jsonPath();
/*
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("state", "VA")
                .and().pathParam("place name", "Fairfax")
                .when().get("/us/{state}/{place name}");

        assertEquals(200,response.statusCode());
        assertEquals("application/json" ,response.contentType());
        response.prettyPrint();
*/

        assertEquals("US" , jsonPath.getString("'country abbreviation'"));
        assertEquals("United States" , jsonPath.getString("country"));
        assertEquals("Fairfax" , jsonPath.getString("'place name'"));

        List<String> palces = jsonPath.getList("places.'place name'");

        for (String each : palces) {
            assertTrue(each.contains("Fairfax"));
        }

        List<String> postCodes = jsonPath.getList("places.'post code'");
        System.out.println(postCodes);

        for (String each : postCodes) {
            assertTrue(each.startsWith("22"));
        }

    }
@Test
    public void task3Pojo(){
        /*
    Given Accept application/json
And path state is VA
And path city is fairfax
When I send a GET request to /us endpoint
Then status code must be 200
And content type must be application/json
And payload should contain following information
    country abbreviation is US
    country  United States
    place name  Fairfax
    each places must contains fairfax as a value
    each post code must start with 22
     */

        Map<String, Object> pathParams = new HashMap<>();
        pathParams.put("state","VA");
        pathParams.put("place name","Fairfax");

        Response response = given().accept(ContentType.JSON)
                .and().pathParams(pathParams)
                .and().get("/us/{state}/{place name}");
        response.prettyPrint();

        assertEquals(HttpStatus.SC_OK,response.statusCode());
        assertEquals("application/json" , response.contentType());
        //assertEquals(ContentType.JSON.toString(), response.contentType());

        //deserialize json to pojo

        StateCity stateCity = response.as(StateCity.class);

        assertEquals("US" , stateCity.getCountryAbbreviation());
        assertEquals("United States" , stateCity.getCountry());
        assertEquals("Fairfax", stateCity.getPlaceName());

    List<StateCityPlaces> places = stateCity.getPlaces();
    System.out.println(places);

    System.out.println(places.get(places.size() - 1).getPlaceName());

    for (int i = 0; i < places.size(); i++) {
         assertTrue(places.get(i).getPlaceName().contains("Fairfax"));
         assertTrue(places.get(i).getPostCode().startsWith("22"));
    }

    assertTrue(places.stream().allMatch(each -> each.getPlaceName().contains("Fairfax")));
    assertTrue(places.stream().allMatch(each -> each.getPostCode().startsWith("22")));


}
}
