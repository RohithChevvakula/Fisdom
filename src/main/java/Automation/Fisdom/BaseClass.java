package Automation.Fisdom;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

public class BaseClass {
	static String authToken = null;

	public BaseClass() throws ClientProtocolException, IOException {
		Operations ops = new Operations();
		authToken = ops.auth();
	}
}
