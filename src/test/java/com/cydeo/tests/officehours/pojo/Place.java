package com.cydeo.tests.officehours.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {

    @JsonProperty("place name")
    public String placeName;

    public String longitude;

    public String state;
    @JsonProperty("state abbreviation")
    public String stateAbbreviation;

    public String latitude;
}
