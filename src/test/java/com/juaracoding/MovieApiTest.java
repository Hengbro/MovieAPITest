package com.juaracoding;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;

public class MovieApiTest {

    private String apiToken = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmZTUxMDdmNDU3NTUyNWNiMjk4MzM5Nzc2ODJhMmM5YSIsInN1YiI6IjY1MmZjZDQwYTgwMjM2MDBhYjQxMWM2MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.DaeHRdfrNdvmAOvRlBMjE2y2W2GyX-JW6oB895ct4fM";
    private String apiKey ="fe5107f4575525cb29833977682a2c9a";
    @Before
    public void setup() {
        RestAssured.baseURI = "https://api.themoviedb.org/3";
    }

    @Test
    public void testGetNowPlayingMovies() {
        Response response = RestAssured
                .given()
                .queryParam("api_key", apiKey)
                .when()
                .get("/movie/now_playing");

        try {
            response.then()
                    .statusCode(200)
                    .body("results", is(notNullValue()));
        } catch (AssertionError e) {
            System.out.println("Pengujian gagal: " + e.getMessage());
            System.out.println("Pesan kesalahan dari server: " + response.getBody().asString());
            throw e;
        }
    }

    @Test
    public void testGetPopularMovies() {
        Response response = RestAssured
                .given()
                .queryParam("api_key", apiKey)
                .when()
                .get("/movie/popular");
        try {
            response.then()
                    .statusCode(200)
                    .body("results", is(notNullValue()));
        } catch (AssertionError e) {
            System.out.println("Pengujian gagal: " + e.getMessage());
            System.out.println("Pesan kesalahan dari server: " + response.getBody().asString());
            throw e;
        }
    }

    @Test
    public void testPostMovieRating() {
        String movieId = "968051";
        double rating = 6.50;

        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + apiToken)
                .formParam("value", rating)
                .when()
                .post("/movie/" + movieId + "/rating");

        try {
            response.then()
                    .statusCode(201)
                    .body("status_message", equalTo("The item/record was updated successfully."));
        } catch (AssertionError e) {
            System.out.println("Pengujian gagal: " + e.getMessage());
            System.out.println("Pesan kesalahan dari server: " + response.getBody().asString());
            throw e;
        }
    }
}
