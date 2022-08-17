package com.cydeo.tests.officehours.day3;

import com.cydeo.utils.ZipCodeTestBase;
import org.junit.jupiter.api.Test;

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
    }
}
