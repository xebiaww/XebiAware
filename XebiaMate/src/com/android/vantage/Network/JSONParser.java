package com.android.vantage.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.ClientProtocolException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.vantageLogManager.Logger;

import android.util.Log;

public class JSONParser {

	// constructor
	public JSONParser() {

	}

	private static InputStream OpenHttpConnection(String urlString)
			throws IOException {

		Log.d("palval", "OpenHttpConnection");
		InputStream in = null;
		int response = -1;

		URL url = new URL(urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		try {
			HttpURLConnection httpConn = (HttpURLConnection) conn;
			httpConn.setAllowUserInteraction(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setConnectTimeout(60000);
			httpConn.setRequestMethod("GET");
			httpConn.addRequestProperty("X-Parse-Application-Id",
					"ffkLGugChiz4rxWuS0lYBgHCqvAAu7h8OrUcp4ed");
			httpConn.addRequestProperty("X-Parse-REST-API-Key",
					"8bOK1EyACceLxPaR2x8zqcm250zLT30TKbqEPf4X");
			httpConn.connect();
			
			response = httpConn.getResponseCode();
			Logger.error("Connected to Stream", "YES");
			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}

		} catch (Exception ex) {
			Log.e("Internet Connecting Exception", "Unable To Connect");
		}
		return in;
	}

	public static String getJSONFromUrl(String url) {

		InputStream is = null;
		JSONObject jObj = null;
		String json = "";
		// Making HTTP request
		try {

			is = OpenHttpConnection(url);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			json = sb.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		return json;

	}
}