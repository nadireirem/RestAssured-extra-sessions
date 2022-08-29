package com.cydeo.tests.officehours.authorization;

import com.cydeo.utils.BookItAPITestBase;
import org.junit.jupiter.api.Test;
import com.cydeo.utils.ConfigurationReader;
import io.restassured.http.ContentType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import java.util.Map;
import static io.restassured.RestAssured.*;

/**
 * Given accept type is Json
 * And header Authorization is access token of teacher
 * And path param "room_name" is "duke"
 * When I send GET request to "/api/rooms/{room_name}
 * Then status code is 200
 * And content type is json
 * id = 116
 * name is "duke"
 * And room capacity is 4
 * And the room has to have TV
 * And the room does not have withWhiteBoard
 */

public class Task1 extends BookItAPITestBase {
    @Test
    public void dukeTest() {

        String accessToken =  getAccessToken(ConfigurationReader.getProperty("teacher_email"), ConfigurationReader.getProperty("teacher_password"));
        System.out.println(accessToken);

        Map <String, ?> roomInfo = given().accept(ContentType.JSON)
                .header("Authorization", accessToken)
                .when().get("/api/rooms/duke")
                .then().statusCode(200)
                .contentType(ContentType.JSON)
                .log().all().extract().as(Map.class);

        assertThat(roomInfo.get("id"), is(116));
        assertThat(roomInfo.get("name"), is("duke"));
        assertThat(roomInfo.get("capacity"), is(4));
        assertThat(roomInfo.get("withTV"), is(true));
        assertThat(roomInfo.get("withWhiteBoard"), is(false));


    }

}
