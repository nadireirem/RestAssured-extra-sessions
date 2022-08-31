package com.cydeo.tests.officehours.newsAPI;

import com.cydeo.utils.NewsAPITestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;
public class NewsAPI extends NewsAPITestBase {

    @Test
    public void queryParams() {

        given().accept(ContentType.JSON)
                .queryParam("q","bitcoin")
                .queryParam("apiKey","d5725fa0694b4a6c840333fd8cb1515b")
                .get("/everything").prettyPeek()
                .then().statusCode(200);

    }

    @Test
    public void headerAuth() {

        given().accept(ContentType.JSON)
                .queryParam("q","bitcoin")
                .header("x-api-key","d5725fa0694b4a6c840333fd8cb1515b")
                .get("/everything").prettyPeek()
                .then().statusCode(200);

    }


}
