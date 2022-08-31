package com.cydeo.tests.officehours.newsAPI;

import com.cydeo.tests.officehours.pojo.Article;
import com.cydeo.utils.NewsAPITestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    public void headerXApiKey() {

        given().accept(ContentType.JSON)
                .queryParam("q","bitcoin")
                .header("x-api-key","d5725fa0694b4a6c840333fd8cb1515b")
                .get("/everything").prettyPeek()
                .then().statusCode(200);

    }

    @Test
    public void headerAuthorization() {

        given().accept(ContentType.JSON)
                .queryParam("q","bitcoin")
                .header("Authorization","d5725fa0694b4a6c840333fd8cb1515b")
                .get("/everything").prettyPeek()
                .then().statusCode(200);

    }


    @Test
    public void GETEveryting() {

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .queryParam("q", "bitcoin")
                .header("Authorization", "d5725fa0694b4a6c840333fd8cb1515b")
                .get("/everything").prettyPeek()
                .then().statusCode(200)
                .extract().jsonPath();
        Article article = jsonPath.getObject("article[0]", Article.class);

        System.out.println(article);

    }





}
