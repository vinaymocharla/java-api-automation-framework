package userManagement;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.response.Response;

public class regresTests {
	
	
    @Test(description = "Validate the status code for GET users endpoint")
    public void validateStatusCodeGetUser() {
       Response resp = given()
    		   
    		   .queryParam("page", 2)
    		   .header("x-api-key","reqres-free-v1")
    		   
               .when()
               .get("https://reqres.in/api/users"); //RestAssured


       int actualStatusCode = resp.statusCode();  //RestAssured
       assertThat(actualStatusCode, equalTo(200)); //Testng
      
    }
    
    @Test
    public void testTwo() throws InterruptedException {
        System.out.println("TestClassTwo running in thread: " + Thread.currentThread());
        Thread.sleep(2000);
    }
    

}
