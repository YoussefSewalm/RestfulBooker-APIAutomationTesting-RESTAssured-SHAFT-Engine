package using_SHAFT_Engine;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class CreatingThenDeleteBooking extends GeneratingToken {

	
	@SuppressWarnings("unchecked")
	@Test
	public void CreatingThenDeletingBooking() 
	{
		JSONObject CreatingBookingBodyReq=new JSONObject();
				
		CreatingBookingBodyReq.put("firstname", "youssef170");
		CreatingBookingBodyReq.put("lastname", "alaa");
		CreatingBookingBodyReq.put("totalprice", 1200);
		CreatingBookingBodyReq.put("depositpaid", true);
		JSONObject bookingdates=new JSONObject();
		bookingdates.put("checkin", "2018-01-01");
		bookingdates.put("checkout", "2019-01-01");
		CreatingBookingBodyReq.put("bookingdates", bookingdates);
		CreatingBookingBodyReq.put("additionalneeds", "IceCream");
	
		Response CreatingBooking=APIObject.buildNewRequest("/booking", RequestType.POST)
				.setContentType(ContentType.JSON)
				.setTargetStatusCode(200)
				.setRequestBody(CreatingBookingBodyReq)
				.perform();
		//GET EXPECTED VALUES AFTER POST(3shan lma a3ml get b3d l post)
		String EXPECTED_USER_ID=RestActions.getResponseJSONValue(CreatingBooking, "bookingid");
		String EXPECTED_USER_FIRSTNAME=RestActions.getResponseJSONValue(CreatingBooking, "booking.firstname");
		String EXPECTED_USER_LASTNAME=RestActions.getResponseJSONValue(CreatingBooking, "booking.lastname");
		String EXPECTED_USER_TOTALPRICE=RestActions.getResponseJSONValue(CreatingBooking, "booking.totalprice");
		String EXPECTED_USER_DEPOSITPAID=RestActions.getResponseJSONValue(CreatingBooking, "booking.depositpaid");
		String EXPECTED_USER_CHECKIN=RestActions.getResponseJSONValue(CreatingBooking, "booking.bookingdates.checkin");
		String EXPECTED_USER_CHECKOUT=RestActions.getResponseJSONValue(CreatingBooking, "booking.bookingdates.checkout");
		String EXPECTED_USER_ADDITIONALNEEDS=RestActions.getResponseJSONValue(CreatingBooking, "booking.additionalneeds");
		
		//VERIFY(AFTER GET) 
		Assertions.assertEquals("youssef170", EXPECTED_USER_FIRSTNAME);
		Assertions.assertEquals("alaa", EXPECTED_USER_LASTNAME);
		Assertions.assertEquals("1200", EXPECTED_USER_TOTALPRICE);
		Assertions.assertEquals("true", EXPECTED_USER_DEPOSITPAID);
		Assertions.assertEquals("2018-01-01", EXPECTED_USER_CHECKIN);
		Assertions.assertEquals("2019-01-01", EXPECTED_USER_CHECKOUT);
		Assertions.assertEquals("IceCream", EXPECTED_USER_ADDITIONALNEEDS);	
		
		Response VerifyBookingIDexists=APIObject.buildNewRequest("/booking", RequestType.GET)
				.setContentType(ContentType.JSON)
				.setTargetStatusCode(200)
				.setUrlArguments("firstname=youssef170&lastname=alaa")
				.perform();
		//get ACTUAL ID from GetBookingIDs API to verify ID Exists
		String ACTUAL_USER_ID=RestActions.getResponseJSONValue(VerifyBookingIDexists, "[0].bookingid");
		//Verify ID Created in POST Method with ID exists in GET Method
		Assertions.assertEquals(EXPECTED_USER_ID, ACTUAL_USER_ID);
		
		
		Response GetBooking=APIObject.buildNewRequest("/booking/"+EXPECTED_USER_ID, RequestType.GET)
				.setContentType(ContentType.JSON)
				.perform();
		//get ACTUAL VALUES AFTER GET (To Verify Booking is Setted correctly in the Database)
		String ACTUAL_USER_FIRSTNAME=RestActions.getResponseJSONValue(GetBooking, "firstname");
		String ACTUAL_USER_LASTNAME=RestActions.getResponseJSONValue(GetBooking, "lastname");
		String ACTUAL_USER_TOTALPRICE=RestActions.getResponseJSONValue(GetBooking, "totalprice");
		String ACTUAL_USER_DEPOSITPAID=RestActions.getResponseJSONValue(GetBooking, "depositpaid");
		String ACTUAL_USER_CHECKIN=RestActions.getResponseJSONValue(GetBooking, "bookingdates.checkin");
		String ACTUAL_USER_CHECKOUT=RestActions.getResponseJSONValue(GetBooking, "bookingdates.checkout");
		String ACTUAL_USER_ADDITIONALNEEDS=RestActions.getResponseJSONValue(GetBooking, "additionalneeds");
		
		//VERIFY(WITH POST RESPONSE) To Check Booking is created correctly in database
		Assertions.assertEquals(EXPECTED_USER_FIRSTNAME, ACTUAL_USER_FIRSTNAME);
		Assertions.assertEquals(EXPECTED_USER_LASTNAME, ACTUAL_USER_LASTNAME);
		Assertions.assertEquals(EXPECTED_USER_TOTALPRICE, ACTUAL_USER_TOTALPRICE);
		Assertions.assertEquals(EXPECTED_USER_DEPOSITPAID, ACTUAL_USER_DEPOSITPAID);
		Assertions.assertEquals(EXPECTED_USER_CHECKIN, ACTUAL_USER_CHECKIN);
		Assertions.assertEquals(EXPECTED_USER_CHECKOUT, ACTUAL_USER_CHECKOUT);
		Assertions.assertEquals(EXPECTED_USER_ADDITIONALNEEDS, ACTUAL_USER_ADDITIONALNEEDS);	
		
		Response DeleteBooking=APIObject.buildNewRequest("/booking/"+EXPECTED_USER_ID, RequestType.DELETE)
				.setContentType(ContentType.JSON)
				//Cookie Header Will be added automatic to all requests From GeneratingToken.java
				.setTargetStatusCode(201)
				.performRequest();
		//VERIFY DELETION SUCCESSFUL
		String ACTUAL_DELETE_RESPONSE=RestActions.getResponseBody(DeleteBooking);
		Assertions.assertEquals("Created", ACTUAL_DELETE_RESPONSE );		
		
 	}
}
