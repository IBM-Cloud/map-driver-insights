/**
 * Copyright 2016 IBM Corp. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the �gLicense�h);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an �gAS IS�h BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibm.iot.auto.bluemix.samples.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Connection {
	
	private String apiBaseUrl;
	private String tenantId;
	private String userName;
	private String password;
	
	private String authorizationValue;
	
	public Connection(Configuration config) {
		this.apiBaseUrl = config.apiBaseUrl();
		this.tenantId = config.tenantId();
		this.userName = config.userName();
		this.password = config.password();
		String authorizationString = userName + ":" + password;
		authorizationValue = new String(Base64.encodeBase64(authorizationString.getBytes()));
	}
	
	public JSONArray getJSONArray(String urlTail) throws IOException, JSONException {
		JSONArray jsonArray = null;
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = createHttpURLGetConnection(urlTail);
			InputStreamReader connectionInputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(connectionInputStreamReader);
			String jsonString = "";
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonString += line;
			}
			jsonArray = new JSONArray(jsonString);
		} finally {
			disconnectIfRequired(httpURLConnection);
		}
		return jsonArray;				
	}

	public JSONArray getJSONArray(String urlTail, String parameters) throws IOException, JSONException {
		JSONArray jsonArray = null;
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = createHttpURLGetConnection(urlTail, parameters);
			InputStreamReader connectionInputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(connectionInputStreamReader);
			String jsonString = "";
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonString += line;
			}
			jsonArray = new JSONArray(jsonString);
		} finally {
			disconnectIfRequired(httpURLConnection);
		}
		return jsonArray;				
	}

	public JSONArray getJSONArray(String urlTail, JSONObject parameters) throws IOException, JSONException {
		JSONArray jsonArray = null;
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = createHttpURLPostConnection(urlTail, parameters);
			InputStreamReader connectionInputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(connectionInputStreamReader);
			String jsonString = "";
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonString += line;
			}
			jsonArray = new JSONArray(jsonString);
		} finally {
			disconnectIfRequired(httpURLConnection);
		}
		return jsonArray;				
	}

	public JSONObject getJSONObject(String urlTail) throws IOException, JSONException {
		JSONObject jsonObject = null;
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = createHttpURLGetConnection(urlTail);
			InputStreamReader connectionInputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(connectionInputStreamReader);
			String jsonString = "";
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonString += line;
			}
			jsonObject = new JSONObject(jsonString);
		} finally {
			disconnectIfRequired(httpURLConnection);
		}
		return jsonObject;				
	}
	
	public JSONObject getJSONObject(String urlTail, String parameters) throws IOException, JSONException {
		JSONObject jsonObject = null;
		HttpURLConnection httpURLConnection = null;
		try {
			httpURLConnection = createHttpURLGetConnection(urlTail, parameters);
			InputStreamReader connectionInputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(connectionInputStreamReader);
			String jsonString = "";
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				jsonString += line;
			}
			jsonObject = new JSONObject(jsonString);
		} finally {
			disconnectIfRequired(httpURLConnection);
		}
		return jsonObject;				
	}

	private HttpURLConnection createHttpURLGetConnection(String urlTail) throws IOException {
		URL url = new URL(apiBaseUrl + urlTail + "?tenant_id=" + tenantId);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "Basic " + authorizationValue);
		httpURLConnection.setRequestMethod("GET");
		return httpURLConnection;
	}

	private HttpURLConnection createHttpURLGetConnection(String urlTail, String parameters) throws IOException {
		URL url = new URL(apiBaseUrl + urlTail + "?tenant_id=" + tenantId + "&" + parameters);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "Basic " + authorizationValue);
		httpURLConnection.setRequestMethod("GET");
		return httpURLConnection;
	}
	
	private HttpURLConnection createHttpURLPostConnection(String urlTail, JSONObject parameters) throws IOException, JSONException {
		URL url = new URL(apiBaseUrl + urlTail + "?tenant_id=" + tenantId);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "Basic " + authorizationValue);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setDoOutput(true);
		OutputStreamWriter connectionOutputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
		parameters.write(connectionOutputStreamWriter);			
		return httpURLConnection;
	}
	
	private void disconnectIfRequired(HttpURLConnection connection) {
		if (connection != null) {
			connection.disconnect();
		}
	}

}
