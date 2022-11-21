package apitests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import  org.json.simple.*;

import java.util.HashMap;
import java.util.Map;


public class TypicodeTest {
    private RequestSpecification requestSpec;
    private Response response;

    @BeforeEach
    public void createRequestSpecification(){
       requestSpec = new RequestSpecBuilder().
               setBaseUri("https://jsonplaceholder.typicode.com/posts").
               build();
    }

    @Test
    public void getRequest_To_List_All_Post_Resources(){
        var response = given().
                spec(requestSpec).
                log().all().
                when().
                get();

        response.then().log().all();
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void getRequest_To_Return_A_Single_Post_Resource(){
        String endPoint = "?id=11";
        response = given().
                spec(requestSpec).
                when().
                get(endPoint);

        response.print();
        response.then().assertThat().statusCode(200);
    }

    @Test
    public void postRequest_To_Create_A_New_Post_Resource(){
        Map<String, Object> map = new HashMap<>();
        map.put("title","foo la");
        map.put("body","bare");
        map.put("userId",1);

        JSONObject jsonObject = new JSONObject(map);

        response = given().
                spec(requestSpec).
                body(jsonObject.toJSONString()).
                contentType(ContentType.JSON.toString()).
                when().
                post();

        response.print();
        response.then().assertThat().statusCode(201);
    }

    @Test
    public void deleteRequest_To_Remove_A_Post_Resource(){
        String endPoint = "/1";
        response = given().
                spec(requestSpec).
                when().
                delete(endPoint);

        response.then().log().all();
        response.then().assertThat().statusCode(200);

    }
}