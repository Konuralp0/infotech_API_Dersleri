package api_test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.util.List;

public class Day1 {


    @Test
    public void test1(){
        
        String url = "https://dummy.restapiexample.com/api/v1/employees";
        Response response = RestAssured.get(url);
        System.out.println(response.asPrettyString());
        int code = 200;
       // Assert.assertEquals(response.getStatusCode(),code,"Status sollte 200 sein");

        Assert.assertEquals(response.getContentType(),"application/json","Content Type sollte application/json");


        String date = response.getHeader("Date");
        System.out.println("Date: " + date);

        List<Header> headersList = response.getHeaders().asList();

        headersList.get(0).getValue();




    }
}
