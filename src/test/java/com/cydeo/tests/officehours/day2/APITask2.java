package com.cydeo.tests.officehours.day2;

import com.cydeo.utils.HrApiTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class APITask2 extends HrApiTestBase {

    @Test
    public void task1() {
        //Q1:
        //- Given accept type is Json
        //- Path param value- US
        //- When users sends request to /countries
        //- Then status code is 200
        //- And Content - Type is Json
        //- And country_id is US
        //- And Country_name is United States of America
        //- And Region_id is 2


        Response response = given().accept(ContentType.JSON)
                .pathParam("country_id", "US")
                .when().get("/countries/{country_id}");


        assertEquals(200,response.getStatusCode());
        response.prettyPrint();

       assertEquals(ContentType.JSON.toString(),response.getContentType());


       //convert response to jsonpath
        JsonPath jsonPath = response.jsonPath();

        assertEquals("US",jsonPath.getString("country_id"));
        assertEquals("United States of America",jsonPath.getString("country_name"));
        assertEquals(2,jsonPath.getInt("region_id"));


    }

    @Test
    public void task2() {

        //- Given accept type is Json
        //- Query param value - q={"department_id":80}
        //- When users sends request to /employees
        //- Then status code is 200
        //- And Content - Type is Json
        //- And all job_ids start with 'SA'
        //- And all department_ids are 80
        //- Count is 25


        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"department_id\":80}").
                when().get("/employees");

        response.prettyPrint();

        assertEquals(200,response.getStatusCode());
        assertEquals(ContentType.JSON.toString(),response.getContentType());


        JsonPath jsonPath = response.jsonPath();



        //- And all job_ids start with 'SA'

        //OPTION 1
        List<String> list = jsonPath.getList("items.job_id");
        System.out.println(list);
        List<String> afterFilter = list.stream().filter(each -> each.startsWith("SA")).collect(Collectors.toList());
        assertEquals(afterFilter.size(),list.size());

        //OPTION 2
        assertTrue(list.stream().allMatch(each->each.startsWith("SA")));

        //- And all department_ids are 80
        System.out.println("=== DEPARTMENT IDS ===");
        List<Integer> departmentIDs = jsonPath.getList("items.department_id");
        System.out.println(departmentIDs);
        assertTrue(departmentIDs.stream().allMatch(each->each==80));

        //- Count is 25
        int count = jsonPath.getInt("count");
        System.out.println(count);
        assertEquals(25,count);

    }
}
