package Automation.Fisdom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.testng.Assert;
import pojos.AuthResponse;
import pojos.Bookingdates;
import pojos.CreateBookingRequest;
import pojos.CreateBookingResponse;
import pojos.UpdateBookingResponse;

public class Operations {
	CloseableHttpClient client = HttpClients.createDefault();
	ObjectMapper om = new ObjectMapper();
	public CreateBookingRequest cbr = new CreateBookingRequest();
	Bookingdates bd = new Bookingdates();
	static String token =null;
	boolean depositpaid = false;
	
	Properties testData = new Properties();
	
	
	public Operations() {
		token = auth();
		try {
			File file = new File(System.getProperty("user.dir")+"/src/TestData/Inputs.properties");
			InputStream is = new FileInputStream(file);
			testData.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String auth() {
		AuthResponse token = null;
		String url = "https://restful-booker.herokuapp.com/auth";
		HttpPost post = new HttpPost(url);
		String body ="{\"username\":\"admin\",\"password\":\"password123\"}";
		try {
		StringEntity entity = new StringEntity(body);
		post.setEntity(entity);
		post.addHeader("Content-Type", "application/json");
		CloseableHttpResponse response;
			response = client.execute(post);
		Assert.assertEquals(200, response.getStatusLine().getStatusCode());
		token = om.readValue(response.getEntity().getContent(), AuthResponse.class);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return token.getToken();
	}
	
	
	public CreateBookingResponse createBooking(String body) {
		CreateBookingResponse output = null;
		String url = "https://restful-booker.herokuapp.com/booking";
		HttpPost post = new HttpPost(url);
		try {
		StringEntity entity = new StringEntity(body);
		post.setEntity(entity);
		post.setHeader("Content-Type", "application/json");
		post.setHeader("Accept", "application/json");
		CloseableHttpResponse response = client.execute(post);
		Assert.assertEquals(200,response.getStatusLine().getStatusCode());
			output = om.readValue(response.getEntity().getContent(), CreateBookingResponse.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	public UpdateBookingResponse updateBooking(int bookingId, String body)  {
		UpdateBookingResponse output = null;
		String url = "https://restful-booker.herokuapp.com/booking/"+bookingId;
		HttpPut put = new HttpPut(url);
		try {
		StringEntity entity = new StringEntity(body);
		put.setEntity(entity);
		put.setHeader("Content-Type", "application/json");
		put.setHeader("Accept", "application/json");
		put.setHeader("Cookie", "token="+token);
		CloseableHttpResponse response = client.execute(put);
		Assert.assertEquals(200,response.getStatusLine().getStatusCode());
		
			output = om.readValue(response.getEntity().getContent(), UpdateBookingResponse.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
		
	}
	public UpdateBookingResponse getBookingDetails(int bookingId) {
		UpdateBookingResponse output = null;
		String url = "https://restful-booker.herokuapp.com/booking/"+bookingId;
		HttpGet get = new HttpGet(url);
		get.setHeader("Accept", "application/json");
		try {
			CloseableHttpResponse response = client.execute(get);
			Assert.assertEquals(200, response.getStatusLine().getStatusCode());
			output = om.readValue(response.getEntity().getContent(), UpdateBookingResponse.class);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public UpdateBookingResponse updatePartialBooking(int bookingId, String body) {
		UpdateBookingResponse output = null;
		String url = "https://restful-booker.herokuapp.com/booking/"+bookingId;
		HttpPatch patch = new HttpPatch(url);
		patch.setHeader("Accept", "application/json");
		patch.setHeader("Cookie", "token="+token);
		patch.setHeader("Content-Type", "application/json");
		try {
			StringEntity entity = new StringEntity(body);
			patch.setEntity(entity);
			CloseableHttpResponse response = client.execute(patch);
			Assert.assertEquals(200, response.getStatusLine().getStatusCode());
			output = om.readValue(response.getEntity().getContent(), UpdateBookingResponse.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (UnsupportedOperationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
		
	}
	
	public String  setTestData(String type) {
		String body = null;
		if(type.equals("NewBooking")){
		cbr.setFirstname(testData.getProperty("firstname"));
		cbr.setLastname(testData.getProperty("lastname"));
		cbr.setTotalprice(Integer.parseInt(testData.getProperty("totalprice")));
		if(testData.getProperty("depositpaid").equals("true")) depositpaid = true;
		cbr.setDepositpaid(depositpaid);
		bd.setCheckin(testData.getProperty("checkin"));
		bd.setCheckout(testData.getProperty("checkout"));
		cbr.setBookingdates(bd);
		cbr.setAdditionalneeds(testData.getProperty("additionalneeds"));
		}
		else if(type.equals("UpdateFullBooking")) {
			cbr.setFirstname(testData.getProperty("ufirstname"));
			cbr.setLastname(testData.getProperty("ulastname"));
			cbr.setTotalprice(Integer.parseInt(testData.getProperty("utotalprice")));
			if(testData.getProperty("udepositpaid").equals("true")) depositpaid = true;
			cbr.setDepositpaid(depositpaid);
			bd.setCheckin(testData.getProperty("ucheckin"));
			bd.setCheckout(testData.getProperty("ucheckout"));
			cbr.setBookingdates(bd);
			cbr.setAdditionalneeds(testData.getProperty("uadditionalneeds"));
		}
		else if(type.equals("PartialUpdate")){
			cbr.setFirstname(testData.getProperty("pfirstname"));
			cbr.setLastname(testData.getProperty("plastname"));
		}
		
		try {
			body = om.writeValueAsString(cbr);
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}
}
