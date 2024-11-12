package using_SHAFT_Engine;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.shaft.api.RestActions;
import com.shaft.api.RestActions.RequestType;
import com.shaft.driver.DriverFactory;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

public class GeneratingToken {
	
	
	public RestActions APIObject; 
	
	
	@SuppressWarnings("unchecked")
	@BeforeClass
	public void SetupAndGettingToken() throws JSONException
	{
		JSONObject gettoken=new JSONObject();
		gettoken.put("username", "admin");
		gettoken.put("password", "password123");
		
		APIObject=DriverFactory.getAPIDriver("https://restful-booker.herokuapp.com");
		Response GenerateToken=APIObject.buildNewRequest("/auth", RequestType.POST)
			     .setContentType(ContentType.JSON)
			     .setRequestBody(gettoken)
			     .performRequest();
		//Get Token From Response 
		String token=RestActions.getResponseJSONValue(GenerateToken, "token");
		
		APIObject.addHeaderVariable("Cookie", "token="+token);
		/* SETTING(APEND) TOKEN INITIALLY TO ALL REQUESTS NEEDING TOKENS */
		//APIObject.addHeaderVariable("Cookie", token);
		
    }

}
