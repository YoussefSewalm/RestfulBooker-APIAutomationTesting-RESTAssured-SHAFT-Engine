package using_SHAFT_Engine;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class UpdatingBooking extends GeneratingToken {
	
	
	@SuppressWarnings("unchecked")
	@Test
	
	public void UpdateBooking()
	{
		
		Response GetAllAvailableID=APIObject.buildNewRequest("/booking", RequestType.GET)
				.performRequest();
		
		String Available_ID=RestActions.getResponseJSONValue(GetAllAvailableID, "[0].bookingid");
		
		
		JSONObject updatebookingbody=new JSONObject();
		updatebookingbody.put("firstname", "Fares");
		updatebookingbody.put("lastname", "Mohamed");
		updatebookingbody.put("totalprice", 1200);
		updatebookingbody.put("depositpaid", true);
		JSONObject bookingdates=new JSONObject();
		bookingdates.put("checkin","2019-01-01");
		bookingdates.put("checkout", "2020-01-01");
		updatebookingbody.put("bookingdates", bookingdates);
		updatebookingbody.put("additionalneeds", "Juice");

		
		Response Updatebooking=APIObject.buildNewRequest("/booking/"+Available_ID, RequestType.PUT)
				.setContentType(ContentType.JSON)
				.setTargetStatusCode(200)
				//Cookie Header Will be added automatic to all requests From GeneratingToken.java
				.setRequestBody(updatebookingbody)
				.performRequest();
		
		//get EXPECTED VALUES AFTER PUT(3shan lma a3ml get b3d l put)
		String EXPECTED_USER_FIRSTNAME=RestActions.getResponseJSONValue(Updatebooking, "firstname");
		String EXPECTED_USER_LASTNAME=RestActions.getResponseJSONValue(Updatebooking, "lastname");
		String EXPECTED_USER_TOTALPRICE=RestActions.getResponseJSONValue(Updatebooking, "totalprice");
		String EXPECTED_USER_DEPOSITPAID=RestActions.getResponseJSONValue(Updatebooking, "depositpaid");
		String EXPECTED_USER_CHECKIN=RestActions.getResponseJSONValue(Updatebooking, "bookingdates.checkin");
		String EXPECTED_USER_CHECKOUT=RestActions.getResponseJSONValue(Updatebooking, "bookingdates.checkout");
		String EXPECTED_USER_ADDITIONALNEEDS=RestActions.getResponseJSONValue(Updatebooking, "additionalneeds");
		
		//VERIFY(WITH PUT RESPONSE) 
		Assertions.assertEquals("Fares", EXPECTED_USER_FIRSTNAME);
		Assertions.assertEquals("Mohamed", EXPECTED_USER_LASTNAME);
		Assertions.assertEquals("1200", EXPECTED_USER_TOTALPRICE);
		Assertions.assertEquals("true", EXPECTED_USER_DEPOSITPAID);
		Assertions.assertEquals("2019-01-01", EXPECTED_USER_CHECKIN);
		Assertions.assertEquals("2020-01-01", EXPECTED_USER_CHECKOUT);
		Assertions.assertEquals("Juice", EXPECTED_USER_ADDITIONALNEEDS);	
		
		Response GetBookingAfterUpdate=APIObject.buildNewRequest("/booking/"+Available_ID, RequestType.GET)
				.setContentType(ContentType.JSON)
				.setTargetStatusCode(200)
				.perform();
		//GET ACTUAL VALUES AFTER PUT (To Verify Booking is Updated correctly in the Database)
		String ACTUAL_USER_FIRSTNAME=RestActions.getResponseJSONValue(GetBookingAfterUpdate, "firstname");
		String ACTUAL_USER_LASTNAME=RestActions.getResponseJSONValue(GetBookingAfterUpdate, "lastname");
		String ACTUAL_USER_TOTALPRICE=RestActions.getResponseJSONValue(GetBookingAfterUpdate, "totalprice");
		String ACTUAL_USER_DEPOSITPAID=RestActions.getResponseJSONValue(GetBookingAfterUpdate, "depositpaid");
		String ACTUAL_USER_CHECKIN=RestActions.getResponseJSONValue(GetBookingAfterUpdate, "bookingdates.checkin");
		String ACTUAL_USER_CHECKOUT=RestActions.getResponseJSONValue(GetBookingAfterUpdate, "bookingdates.checkout");
		String ACTUAL_USER_ADDITIONALNEEDS=RestActions.getResponseJSONValue(GetBookingAfterUpdate, "additionalneeds");

		//VERIFY(AFTER GET) BOOKING CREATED DATA WITH BOOKING DATA EXISTS IN DATABASE
		Assertions.assertEquals(EXPECTED_USER_FIRSTNAME, ACTUAL_USER_FIRSTNAME);
		Assertions.assertEquals(EXPECTED_USER_LASTNAME, ACTUAL_USER_LASTNAME);
		Assertions.assertEquals(EXPECTED_USER_TOTALPRICE, ACTUAL_USER_TOTALPRICE);
		Assertions.assertEquals(EXPECTED_USER_DEPOSITPAID, ACTUAL_USER_DEPOSITPAID);
		Assertions.assertEquals(EXPECTED_USER_CHECKIN, ACTUAL_USER_CHECKIN);
		Assertions.assertEquals(EXPECTED_USER_CHECKOUT, ACTUAL_USER_CHECKOUT);
		Assertions.assertEquals(EXPECTED_USER_ADDITIONALNEEDS, ACTUAL_USER_ADDITIONALNEEDS);
	}

}
