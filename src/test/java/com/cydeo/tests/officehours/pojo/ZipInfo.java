package com.cydeo.tests.officehours.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Data
public class ZipInfo {

    @JsonProperty("post code")
    private String postCode;

    private String country;
    @JsonProperty("country abbreviation")
   private String countryAbbreviation;


    private List<Place> places;

}
