package api_test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day3_JsonPath {
    @BeforeClass
    public void before() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }



    @Test
    public void assertActivityResponseWithPathMethod() {
        // request activity api with activity number (query param)
        Response response = RestAssured.get("http://www.boredapi.com/api/activity?key=9008639");
        // verify status code
        Assert.assertEquals(response.getStatusCode(), 200);
        // verify content type
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8");

        // verify each field
        int participants = response.path("participants");
        float access = response.path("accessibility");
        Assert.assertEquals(response.path("activity"), "Memorize a favorite quote or poem");
        Assert.assertEquals(response.path("type"), "education");
        Assert.assertEquals(participants, 1);
        Assert.assertEquals(response.path("key"), "9008639");
        Assert.assertEquals(access, 0.8f);

    }

    @Test
    public void assertRandomUserWithPathMethod() {
        // request random user endpoint
        Response response = RestAssured.get("https://randomuser.me/api");
        // verify status code
        // verify content type
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8");

        // verify each field
        System.out.println((String)response.path("info.seed"));
        System.out.println((int)response.path("info.results"));
        System.out.println((int)response.path("info.page"));
        System.out.println((String)response.path("info.version"));


//        System.out.println((String)response.path("results.email[0]"));
        List<String> emailList = response.path("results.email");
        System.out.println("emailList.size() = " + emailList.size());
        System.out.println("emailList.get(0) = " + emailList.get(0));
    }

    @Test
    public void assertEmployeesWithPathMethod() {
        Response response = RestAssured.get("https://dummy.restapiexample.com/api/v1/employees");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getContentType(), "application/json");

        System.out.println((String) response.path("data.employee_name[1]"));
        // api calismadigi icin yapilamadi
    }

    @Test
    public void printDifferentDataFromEmployeesWithPathMethod() {
        // request /employees endpoint (or use universities api)
        // verify status code
        // verify content type
        Response response = RestAssured.get("http://universities.hipolabs.com/search?country=Turkey");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getContentType(), "application/json");


        // locate and print several ids and names (using gpath syntax e.g. .[0], .[-1] )
        List<String> listOfUniversities = response.path("name");
        System.out.println("listOfUniversities.size() = " + listOfUniversities.size());

        for (String uni: listOfUniversities) {
            //System.out.println(uni);
        }

        System.out.println((String) response.path("name[0]"));
        System.out.println((String) response.path("name[-1]"));
    }

    @Test
    public void getListsFromEmployeesWithPathMethod() {

        //cancelled
    }

    @Test
    public void locateNestedObjectsInJsonResponse() {
        // request https://randomuser.me/api
        // verify status code and content type
        Response response = RestAssured.get("https://randomuser.me/api");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getContentType(), "application/json; charset=utf-8");

        // locate and print nested data inside json response [e.g. items.nestedobject[2].name[1]]

        System.out.println((int)response.path("results.dob[0].age"));
        System.out.println((int)response.path("results.registered[0].age"));
    }


    /////// Save response to JsonPath. Use JsonPath to handle response body data ////////


    @Test
    public void saveResponseToJsonPath() {
        // request /uni endpoint
        // verify status code and content type
        Response response = RestAssured.get("http://universities.hipolabs.com/search?country=Turkey");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getContentType(), "application/json");
        // locate and create sub lists from response
        JsonPath jPath = response.jsonPath();
        String uni = jPath.getString("name[3]");
        System.out.println("uni = " + uni);

        // print list using for loop

        List<Object> web_pages = jPath.getList("web_pages");
        for (Object pages : web_pages) {
            System.out.println("pages = " + pages);
        }
        // verify status code and content type
        // save response to JsonPath
        // assert data using JsonPath
    }

    @Test
    public void createListUsingJsonPath() {
        // request employees
        // verify status code and content type
        // save response to JsonPath
        // create and print list using JsonPath

        //cancelled
    }

    @Test
    public void useHamcrestForAssertion() {
        /**
         * Given content type json
         * When uni api call is made
         * then status code should be 200
         * Content type should be "application/json"
         * Header "Connection" should equal to keep-alive
         * Header "Server" should equal to "nginx/1.14.0"
         * Header "Date" should be notNullValue
         * Body path(list) hasItems("john", "doe") -
         */

//        Response response = RestAssured.get("http://universities.hipolabs.com/search?country=Turkey");
//        Assert.assertEquals(response.getStatusCode(), 200);
//        Assert.assertEquals(response.getContentType(), "application/json");
//        Assert.assertEquals(response.getHeader("Connection"), "keep-alive");

        RestAssured.given().accept(ContentType.JSON)
                .when().get("http://universities.hipolabs.com/search?country=Turkey")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().assertThat().header("Connection", "keep-alive")
                .and().assertThat().header("Server", Matchers.equalTo("nginx/1.14.0"))
                .and().assertThat().header("Date", Matchers.notNullValue())
                .and().assertThat().header("Content-Length", Matchers.notNullValue());

    }

    @Test
    public void useHamcrestForListAssertion() {
        /**
         * Given content type json
         * When http://universities.hipolabs.com/search?country=Turkey
         * then status code should be 200
         * Content type should be "..."
         * Body("name" hasItems("bogazici", "odtu"))
         */

        RestAssured.given().accept(ContentType.JSON)
                .when().get("http://universities.hipolabs.com/search?country=Turkey")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json")
                .and().assertThat().body("name", Matchers.hasItems("Nuh Naci Yazgan University", "Izmir Economy University","Hasan Kalyoncu University"));
    }

    @Test public void useHamcrestForAssertion2() {
        /**
         * Given content type json
         * When "http://www.boredapi.com/api/activity?key=9008639" call is made
         * then status code should be 200
         * Content type should be "application/json; charset=utf-8"
         * response should be:
         {
         "activity": "Memorize a favorite quote or poem",
         "type": "education",
         "participants": 1,
         "price": 0,
         "link": "",
         "key": "9008639",
         "accessibility": 0.8
         }
         */

        RestAssured.given().accept(ContentType.JSON)
                .when().get("http://www.boredapi.com/api/activity?key=9008639")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json; charset=utf-8")
                .and().assertThat().body("accessibility", Matchers.equalTo(0.8f));

    }

    @Test public void useRestAssuredLogging() {
        // request "https://randomuser.me/api"
        // log relevant data

//        RestAssured.given().accept(ContentType.JSON).log().uri().log().method()
//                .when().get("https://randomuser.me/apis")
//                .then().assertThat().statusCode(Matchers.notNullValue())
//                .and().assertThat().contentType(Matchers.notNullValue())
//                .log().ifStatusCodeIsEqualTo(404);

        RestAssured.given().accept(ContentType.JSON)
                .when().get("https://randomuser.me/apis")
                .then().assertThat().statusCode(Matchers.notNullValue())
                .and().assertThat().contentType(Matchers.notNullValue())
                .log().ifError();
    }

    @Test public void useRestAssuredLogging2() {
        // request "https://randomuser.me/api"
        // use logging at @beforeclass

        RestAssured.given().accept(ContentType.JSON)
                .when().get("https://randomuser.me/api")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType("application/json; charset=utf-8");
    }

    @Test
    public void deserializeToMap() {
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get("https://randomuser.me/api");

        Map<String, Object> deserializationMap = response.body().as(Map.class);

        System.out.println("deserializationMap.toString() = " + deserializationMap.toString());
    }

}
