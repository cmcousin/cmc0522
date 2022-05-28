package com.demo.cfdemo;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class RentalControllerTest {

    Gson gson = new Gson();

    @Test
    public void inventory_whenCalled_shouldReturn200() {
        String jsonMimeType = ContentType.JSON.toString();
        Response response = given()
                .get("/inventory");

        String mimeType = response.getContentType();
        assertEquals(jsonMimeType, mimeType);
    }

    @Test
    public void inventory_whenCalled_shouldReturnInventoryAsList() {
        String jsonMimeType = ContentType.JSON.toString();
        Response response = given()
                .get("/inventory");

        Tool[] toolList = response.getBody().as((Type) Tool[].class);
        assertEquals(toolList.length, 4);
    }

    @Test
    public void rental_whenCalled_shouldReturnJSONObject() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",5,101, "9/3/15");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            String mimeType = response.getContentType();
            assertEquals(jsonMimeType, mimeType);
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void rental_whenCalled_shouldReturn201() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",5,20, "9/3/15");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            assertEquals(201, response.getStatusCode());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void rental_whenRentalDaysOutOfRange_shouldReturn400() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",0,20, "9/3/15");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            assertEquals(400, response.getStatusCode());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void rental_whenDiscountPercentOutOfRange_shouldReturn400() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",3,120, "9/3/15");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            assertEquals(400, response.getStatusCode());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void rental_testCase1_expectError() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",5,101, "9/3/15");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            assertEquals(400, response.getStatusCode());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void rental_testCase2_expectSuccess() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("LADW",3,10, "7/2/20");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            assertEquals(201, response.getStatusCode());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void rental_testCase3_expectSuccess() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("CHNS",5,25, "7/2/15");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            assertEquals(201, response.getStatusCode());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void rental_testCase4_expectSuccess() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKD",6,0, "9/3/15");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            assertEquals(201, response.getStatusCode());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void rental_testCase5_expectSuccess() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",9,0, "7/2/15");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            assertEquals(201, response.getStatusCode());
        } catch (ParseException e) {
            fail();
        }
    }

    @Test
    public void rental_testCase6_expectSuccess() {
        String jsonMimeType = ContentType.JSON.toString();
        try {
            RentalAgreementRequestDTO request = new RentalAgreementRequestDTO("JAKR",4,50, "7/2/20");
            Response response = given()
                    .contentType(jsonMimeType).body(request).when()
                    .post("/rental");

            assertEquals(201, response.getStatusCode());
        } catch (ParseException e) {
            fail();
        }
    }


}
