package userManagement;


import static org.testng.AssertJUnit.assertEquals;

import java.io.IOException;
import java.util.*;

import org.testng.annotations.Test;
import utils.SoftAssertionUtil;
import core.BaseTest;
import core.StatusCodes;

import static org.hamcrest.MatcherAssert.assertThat;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import pojo.CityRequest;
import pojo.PostRequestBody;
import utils.ExtentReport;
import utils.JsonReader;
import utils.PropertyReader;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.json.simple.parser.ParseException;

public class getUsers extends BaseTest
{
	
	
	
	@Test
	public void getUserData() {
		
		given()
		.header("x-api-key","reqres-free-v1")
		.when().get("https://reqres.in/api/users?page=2").
		then()
		.assertThat()
		.statusCode(200);
		
	}
	 

    @Test
    public void validateGetResponseBody() {
        // Set base URI for the API
        RestAssured.baseURI = "https://reqres.in";

        // Send a GET request and validate the response body using 'then'
        given()
        		.header("x-api-key","reqres-free-v1")
                .when()
                .get("/api/users/7")
                .then()
                .assertThat()
                .statusCode(200)
                .body(not(emptyString()))
                .body("data.id", equalTo(7))
                .body("data.email", equalTo("michael.lawson@reqres.in"))
                .body("support.text", equalTo("Tired of writing endless social media content? Let Content Caddy generate it for you."));
    }

    @Test
    public void validateResponseHasItems() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/posts")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body contains specific items
        assertThat(response.jsonPath().getList("title"), hasItems("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "qui est esse"));
    }

    @Test
    public void validateResponseHasSize() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/comments")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body has a specific size
        assertThat(response.jsonPath().getList(""), hasSize(500));
    }
    
    @Test
    public void validateListContainsInOrder() {
        // Set base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Send a GET request and store the response in a variable
        Response response = given()
                .when()
                .get("/comments?postId=1")
                .then()
                .extract()
                .response();

        // Use Hamcrest to check that the response body contains specific items in a specific order
        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com", "Nikita@garfield.biz","Lew@alysha.tv","Hayden@althea.biz");
        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));
    }

    @Test
    public void testGetUsersWithMultipleQueryParams() {
    }
    
    @Test
    public void testCreateUserWithFormParam() {
    	
    	String serverAddress = PropertyReader.propertyReader("resources//config.properties","server");
    	System.out.println(serverAddress);
    	
        Response response = given()
        		.header("x-api-key","reqres-free-v1")
                .contentType(ContentType.JSON)
                .body("{\"name\":\"John Doe\", \"job\":\"Developer\"}")
                .when()
                .post(serverAddress+"users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert that the response contains the correct name and job values
        response.then().body("name", equalTo("John Doe"));
        response.then().body("job", equalTo("Developer"));
    }
    
    @Test
    public void testGetUserListWithHeader() {
       given()
               .header("Content-Type","application/json")
               .header("x-api-key","reqres-free-v1")
               .when()
               .get("https://reqres.in/api/users?page=2")
               .then()
               .statusCode(200);
       System.out.println("testGetUserListWithHeader Executed Successfully");
    }
    
    @Test
    public void testWithTwoHeaders() {
       given()
               .header("Authorization", "bearer ywtefdu13tx4fdub1t3ygdxuy3gnx1iuwdheni1u3y4gfuy1t3bx")
               .header("Content-Type", "application/json")
               .header("x-api-key","reqres-free-v1")
               
               .when()
               .get("https://reqres.in/api/users?page=2")
               .then()
               .statusCode(200);
       System.out.println("testWithTwoHeaders Executed Successfully");
    }
    
    @Test
    public void testGetUserList() {
        // Set base URI for the API
        RestAssured.baseURI = "https://reqres.in/api";

        // Create a Map to hold headers
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer <your_token_here>");
        headers.put("x-api-key","reqres-free-v1");

        // Send a GET request with headers
        given()
                .headers(headers)
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .body("total", equalTo(12))
                .body("data[0].first_name", equalTo("Michael"))
                .body("data[0].last_name", equalTo("Lawson"));
    }
    
    @Test
    public void testFetchMultipleHeaders() {
        given()
        	.header("x-api-key","reqres-free-v1")
            .when()
            .get("https://reqres.in/api/users?page=2")
            .then()
            .assertThat()
            .header("Content-Type","application/json; charset=utf-8")
            .header("Server","cloudflare")
            .statusCode(StatusCodes.SUCCESS.code);


    
    }
    
    @Test
    public void testFetchHeaders() {
       Response response = given()
    		   .header("x-api-key","reqres-free-v1")
               .when()
               .get("https://reqres.in/api/users?page=2")
               .then()
               .extract().response();

       Headers headers = response.getHeaders();
       
       for(Header h: headers) {
    	   
    	   if(h.getName().equals("Server")) {
    		   
    		   assertEquals(h.getValue(),"cloudflare");
    	   }
       }
    }
    
    
    @Test
    public void testCookies() {
        // Set up the base URL and path for the endpoint
        String baseUrl = "https://httpbin.org";
        String path = "/cookies";

        // Set up the cookies to send with the request
        Map<String, String> cookies = new HashMap<>();
        cookies.put("cookie1", "value1");
        cookies.put("cookie2", "value2");

        // Send the request with the cookies
        given()
            .cookies(cookies)
        .when()
            .get(baseUrl + path)
        .then()
            .body("cookies.cookie1", equalTo("value1"))
            .body("cookies.cookie2", equalTo("value2"));
    }
    
    @Test()
    public void validateWithTestDataFromJson() throws IOException, ParseException {
       String username = JsonReader.getTestData("username");
       String password = JsonReader.getTestData("password");
       System.out.println("username from json is: " + username + "***password from json is:" + password);
       Response resp = given()
               .auth()
               .basic(username, password)
               .when()
               .get("https://postman-echo.com/basic-auth"); //RestAssured


       int actualStatusCode = resp.statusCode();  //RestAssured
       assertEquals(actualStatusCode, 200); //Testng
       System.out.println("validateWithTestDataFromJson executed successfully");
    }
    
    
    @Test()
    public void validateWithDataFromPropertiesFile() {
       String serverAddress = PropertyReader.propertyReader("resources//config.properties","serverAddress");
       System.out.println("Server Address is : " + serverAddress);
       Response resp =
               given()
                       .queryParam("page", 2)
                       .header("x-api-key","reqres-free-v1")
                       .when()
                       .get(serverAddress);
       int actualStatusCode = resp.statusCode();  //RestAssured
       assertEquals(actualStatusCode, 200); //Testng
       System.out.println("validateWithDataFromPropertiesFile executed successfully" + serverAddress);

   


    
    
    
    
    
    
    

    }
    
    @Test(groups="Regression")
    public void validateFromProperties_TestData() throws IOException, ParseException {
       String serverAddress = PropertyReader.propertyReader("resources//config.properties","server");
       String endpoint = JsonReader.getTestData("endpoint");
       String URL = serverAddress + endpoint;
       System.out.println("URL  is : " + URL);
       Response resp =
               given()
                       .queryParam("page", 2)
                       .header("x-api-key","reqres-free-v1")
                       .when()
                       .get(URL);
       int actualStatusCode = resp.statusCode();  //RestAssured
       assertEquals(actualStatusCode, 200); //Testng
       
       
       System.out.println("validateFromProperties_TestData executed successfully" + URL);
    }
    

    
    @Test(description = "Validate the status code using SoftAssertion", groups= {"Regression","Smoke"})
    public void validateStatusCodeUsingSoftAssertion() {
    	
       Response resp = given()
    		   
    		   .queryParam("page", 2)
    		   .header("x-api-key","reqres-free-v1")
    		   
               .when()
               .get("https://reqres.in/api/users"); //RestAssured


       int actualStatusCode = resp.statusCode();  //RestAssured
       
       SoftAssertionUtil.assertEquals(actualStatusCode, StatusCodes.SUCCESS.code,"verified sucess message");
       SoftAssertionUtil.assertAll();     
       
       ExtentReport.logInfo("verfied the status code with softAssertion");
      
    }
    
    @Test
    public void validatePostRequestWithPOJO() {
    	
    	String serverAddress = PropertyReader.propertyReader("resources//config.properties","server");
    	System.out.println(serverAddress);
    	
    	PostRequestBody postRequest = new PostRequestBody();
    	
    	postRequest.setName("Vinay");
    	postRequest.setJob("QA engineer");
    	
        Response response = given()
        		.header("x-api-key","reqres-free-v1")
                .contentType(ContentType.JSON)
                .body(postRequest)
                .when()
                .post(serverAddress+"users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert that the response contains the correct name and job values
        response.then().body("name", equalTo("Vinay"));
        response.then().body("job", equalTo("QA engineer"));
    }
    
    @Test
    public void validatePostRequestWithPOJOList() {
    	
    	String serverAddress = PropertyReader.propertyReader("resources//config.properties","server");
    	System.out.println(serverAddress);
    	
    	List<String> languages = new ArrayList<>();
    	languages.add("Java");
    	languages.add("python");
    	
    	PostRequestBody postRequest = new PostRequestBody();
    	
    	postRequest.setName("Vinay");
    	postRequest.setJob("QA engineer");
    	postRequest.setLanguages(languages);
    	
    	
        Response response = given()
        		.header("x-api-key","reqres-free-v1")
                .contentType(ContentType.JSON)
                .body(postRequest)
                .when()
                .post(serverAddress+"users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert that the response contains the correct name and job values
        response.then().body("name", equalTo("Vinay"));
        response.then().body("job", equalTo("QA engineer"));
    }
    
    @Test
    public void validatePostRequestWithPOJOListObject() {
    	
    	String serverAddress = PropertyReader.propertyReader("resources//config.properties","server");
    	System.out.println(serverAddress);
    	
    	List<String> languages = new ArrayList<>();
    	languages.add("Java");
    	languages.add("python");
    	
    	CityRequest cityRequest1 = new CityRequest();
    	
    	cityRequest1.setName("Bengaluru");
    	cityRequest1.setTemperature("33");
    	
    	CityRequest cityRequest2 = new CityRequest();
    	cityRequest2.setName("Lubbock");
    	cityRequest2.setTemperature("66");
    	
    	List<CityRequest> cityRequests = new ArrayList<>();
    	
    	cityRequests.add(cityRequest1);
    	cityRequests.add(cityRequest2);
    	
    	
    	
    	
    	PostRequestBody postRequest = new PostRequestBody();
    	
    	postRequest.setName("Vinay");
    	postRequest.setJob("QA engineer");
    	postRequest.setLanguages(languages);
    	postRequest.setCityRequests(cityRequests);
    	
    	
        Response response = given()
        		.header("x-api-key","reqres-free-v1")
                .contentType(ContentType.JSON)
                .body(postRequest)
                .when()
                .post(serverAddress+"users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert that the response contains the correct name and job values
        response.then().body("name", equalTo("Vinay"));
        response.then().body("job", equalTo("QA engineer"));
        
        System.out.println(response.asString());
    }
    
    @Test
    public void validatePostRequestWithResponsePOJO() {
    	
    	String serverAddress = PropertyReader.propertyReader("resources//config.properties","server");
    	System.out.println(serverAddress);
    	
    	List<String> languages = new ArrayList<>();
    	languages.add("Java");
    	languages.add("python");
    	
    	CityRequest cityRequest1 = new CityRequest();
    	
    	cityRequest1.setName("Bengaluru");
    	cityRequest1.setTemperature("33");
    	
    	CityRequest cityRequest2 = new CityRequest();
    	cityRequest2.setName("Lubbock");
    	cityRequest2.setTemperature("66");
    	
    	List<CityRequest> cityRequests = new ArrayList<>();
    	
    	cityRequests.add(cityRequest1);
    	cityRequests.add(cityRequest2);
    	
    	
    	
    	
    	PostRequestBody postRequest = new PostRequestBody();
    	
    	postRequest.setName("Vinay");
    	postRequest.setJob("QA engineer");
    	postRequest.setLanguages(languages);
    	postRequest.setCityRequests(cityRequests);
    	
    	
        Response response = given()
        		.header("x-api-key","reqres-free-v1")
                .contentType(ContentType.JSON)
                .body(postRequest)
                .when()
                .post(serverAddress+"users")
                .then()
                .statusCode(201)
                .extract()
                .response();
        
        PostRequestBody responseBody =response.as(PostRequestBody.class);
        
        assertEquals(responseBody.getName(),"Vinay");
        
        System.out.println(responseBody.getCityRequests().get(1).getTemperature());

        // Assert that the response contains the correct name and job values
        response.then().body("name", equalTo("Vinay"));
        response.then().body("job", equalTo("QA engineer"));
        
        System.out.println(response.asString());
    }
    
    
    
  

    



}





    
    




   



	


