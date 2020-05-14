package Automation.Fisdom;

import java.io.IOException;
import java.util.Iterator;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONObject;
import org.testng.annotations.Test;
import junit.framework.Assert;
import pojos.CreateBookingResponse;
import pojos.UpdateBookingResponse;

public class FisdomTest extends Operations {
	Operations ops = new Operations();
	CreateBookingResponse bookingDetails;
	UpdateBookingResponse updatedBookingDetails ;
	
	
	public FisdomTest() {
		super();
	}

	// auth token is generated in the Super class, asserting that it is not null
	@Test (priority=1)
	void checkGeneratedAuthToken() throws ClientProtocolException, IOException {
		Assert.assertNotNull(token);
	}

	// create a new booking
	@Test (priority=2)
	 void createFlightBooking() {
		String body = setTestData("NewBooking");
		bookingDetails = ops.createBooking(body);
		Assert.assertNotNull(bookingDetails.getBookingid());
	}
	
	// get the booking details
	@Test (priority=3)
	void getBookingDetails() {
		updatedBookingDetails = ops.getBookingDetails(Integer.parseInt(bookingDetails.getBookingid()));
		Assert.assertEquals(testData.get("firstname"), updatedBookingDetails.getFirstname());
	}
	
	// update the above created booking
	@Test (priority=4)
	void updateBooking() {
		String body = setTestData("UpdateFullBooking");
		updatedBookingDetails = ops.updateBooking(Integer.parseInt(bookingDetails.getBookingid()), body);
		Assert.assertNotSame(testData.get("firstname"), updatedBookingDetails.getFirstname());
	}
	
	// partially update the above created booking
	@Test (priority=5)
	void partialUpdateBooking() throws JsonParseException, JsonMappingException, IOException {
		String body = setTestData("PartialUpdate");
		JSONObject obj = new JSONObject(body);
		JSONObject temoObj = new JSONObject(body);
		Iterator<String> keys = obj.keys();
		while(keys.hasNext()) { String key = keys.next();
		if (!(key.equals("firstname") | key.equals("lastname"))) { temoObj.remove(key);}
		}
		body = temoObj.toString();
		updatedBookingDetails = ops.updatePartialBooking(Integer.parseInt(bookingDetails.getBookingid()), body);
		Assert.assertNotSame(testData.get("firstname"),updatedBookingDetails.getFirstname());
		Assert.assertEquals(testData.get("pfirstname"), updatedBookingDetails.getFirstname());
	}
}
