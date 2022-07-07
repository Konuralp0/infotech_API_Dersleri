package api_test;

import io.cucumber.java.ja.但し;
import io.cucumber.java.zh_cn.但是;
import io.cucumber.java.zh_cn.当;
import io.cucumber.java.zh_tw.當;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

public class Day2  {

    @BeforeClass
    public void initializeURL(){
        //RestAssured.baseURI = "http://numbersapi.com/random/math";
        //RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1/employees/7";  Param_way 1
        RestAssured.baseURI = "https://dummy.restapiexample.com"; //Paramway_2

    }

    @Test
    public void testRandomNumber1(){

      Response response = RestAssured.get();



      Assert.assertEquals(response.getStatusCode(),200);

      Assert.assertEquals(response.getContentType(),"text/plain; charset=utf-8");

    }


    @Test
    public void testRandomNumber2(){
        Response response = RestAssured.get();



        Assert.assertEquals(response.getStatusCode(),200);

        Assert.assertEquals(response.getContentType(),"text/plain; charset=utf-8");



        String contentType = response.getHeader("Content-Type");
        String pragma = response.getHeader("Pragma");

        Assert.assertEquals(contentType, "text/plain; charset=utf-8");
        Assert.assertEquals(pragma,"no-cache");

        boolean dateExist = response.getHeaders().hasHeaderWithName("Date");

        Assert.assertTrue(dateExist);



    }

    @Test
    public void testRandomNumber3(){
        Response response = RestAssured.get();



        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(response.getHeader("Content-Type"), "text/plain; charset=utf-8");

        Assert.assertTrue(response.getHeaders().hasHeaderWithName("X-Powered-By"));



    }

    @Test
    public void testEmployeesWithParamWay1(){

        Response response = RestAssured.get();

        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getHeader("Content-Type"),"application/json");

        String jsonResponse = response.body().print();

        Assert.assertTrue(jsonResponse.contains("Herrod Chandler"));


    }

    @Test
    public void testEmployeesWithParamWay2(){

        String employeeParam = "7";
        Response response = RestAssured.get("/api/v1/employee/".concat(employeeParam));

        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println(response.asPrettyString());

        Assert.assertEquals(response.getHeader("Content-Type"),"application/json");

        String jsonResponse = response.body().asString();

        Assert.assertTrue(jsonResponse.contains(employeeParam));

    }

    @Test
    public void testEmployeesWithParamWay3(){
//        RestAssured.given().accept("application/json")
//                .when().get("/api/v1/employee/9")
//                .then().contentType("application/jpeg");
        //Assert.assertEquals(response.getStatusCode(),200);

        RestAssured.given().accept("application/json").param("/api/v1/employee/5")
                .when().get()
                .then().statusCode(200).and().contentType("text/html; charset=utf-8");



    }


    @Test
    public void negativeTestHttpsWithParams(){
        RestAssured.given().pathParam("httpCode",1001).get("https://http.cat/{httpCode}").then().statusCode(404).and().contentType("image/jpeg");


    }


    @Test
    public void testUniverstitiesWithQueryParam(){
        Response response = RestAssured.given().queryParam("country","Turkey").get("http://universities.hipolabs.com/search");

       Assert.assertTrue(response.body().asString().contains("boun.edu.tr"));


    }


    @Test
    public void testUniverstitiesWithQueryParamWithMap(){
        String country = "Turkey";
        Map<String,String> queryParamMap = new HashMap<>();
        queryParamMap.put("country",country);

        Response response = RestAssured.given().queryParams(queryParamMap).get("http://universities.hipolabs.com/search");
    }

    @Test
    public void assertEmployeesResponseWithPathMethod(){

        Response response = RestAssured.given().pathParam("employeeId","7").get("https://dummy.restapiexample.com/api/v1/employee/{employeeId}");

        response.body().asPrettyString();
        System.out.println("data" + response.path("data.employee_salary"));

    }
}
