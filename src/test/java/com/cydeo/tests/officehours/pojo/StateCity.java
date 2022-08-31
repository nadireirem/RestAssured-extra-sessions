package com.cydeo.tests.officehours.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateCity {

        @JsonProperty("country abbreviation")
        public String countryAbbreviation;
        @JsonProperty("places")
        public List<StateCityPlaces> places = null;


        @JsonProperty("place name")
        public String placeName;

        public String state;
        @JsonProperty("state abbreviation")
        public String stateAbbreviation;

}
