package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;
import junit.framework.Assert;

public class UserTests {

	Faker faker;
	User userPayload;
	
	public Logger logger;

	@BeforeClass
	public void setup() {
		faker = new Faker();
		userPayload = new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		logger=LogManager.getLogger(this.getClass());

	}

	@Test(priority = 1)
	public void testPostUser() {
		logger.info("-----------creating user--------");
		Response response = UserEndPoints.createUser(this.userPayload);
		response.then().log().all();
		Assert.assertEquals(200,response.getStatusCode());
		logger.info("-----------user created--------");

	}
	
	@Test(priority=2)
	public void testGetUserByName()
	{
		logger.info("-----------Reading User Info--------");
		Response response=UserEndPoints.readUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(200, response.getStatusCode());
		logger.info("-----------Reading User Info displayed--------");
	}
	
	@Test(priority=3)
	public void testUpdateUserByName()
	{
		logger.info("-----------updating user--------");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().emailAddress());
		
		Response response=UserEndPoints.updateUser(userPayload,this.userPayload.getUsername());
		response.then().log().body();
		Assert.assertEquals(200,response.getStatusCode());
		
		Response responseAfterUpdate=UserEndPoints.readUser(this.userPayload.getUsername());
		Assert.assertEquals(200, responseAfterUpdate.getStatusCode());
		logger.info("-----------user updated--------");
		
	}
	
	@Test(priority=4)
	public void testDeleteUserByName()
	{
	
		logger.info("-----------Deleting user--------");
		Response response=UserEndPoints.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(200,response.getStatusCode());
		logger.info("-----------User deleted successfully--------");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
